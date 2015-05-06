package sk.stuba.fiit.ms.apps;

import java.util.List;

import sk.stuba.fiit.ms.database.Database;
import sk.stuba.fiit.ms.database.SerializedDatabase;
import sk.stuba.fiit.ms.downloading.ResultsContentDownloader;
import sk.stuba.fiit.ms.evaluation.*;
import sk.stuba.fiit.ms.parsers.Sessions;
import sk.stuba.fiit.ms.parsers.sessiontrack.*;
import sk.stuba.fiit.ms.parsers.soke.SokeParser;
import sk.stuba.fiit.ms.session.identifiers.SessionIdentifier;
import sk.stuba.fiit.ms.session.identifiers.consecutive.ConsecutiveSessionIdentifier;
import sk.stuba.fiit.ms.session.identifiers.consecutive.CosineSimilarityConsecutiveApproach;
import sk.stuba.fiit.ms.session.identifiers.consecutive.JaccardSimilarityConsecutiveApproach;
import sk.stuba.fiit.ms.session.identifiers.consecutive.TemporalDistanceConsecutiveApproach;
import sk.stuba.fiit.ms.session.identifiers.stack.MethodStackApproach;
import sk.stuba.fiit.ms.session.identifiers.stack.StackSessionIdentifier;
import sk.stuba.fiit.ms.features.FeatureNormalizer;
import sk.stuba.fiit.ms.features.extraction.SessionSearchExtractor;
import sk.stuba.fiit.ms.learning.SVMModel;
import sk.stuba.fiit.ms.learning.training.training_set.TrainingSet;
import sk.stuba.fiit.ms.learning.training.training_set.creation.TrainingSetCreator;
import sk.stuba.fiit.ms.learning.SessionsSplitter;
import sk.stuba.fiit.ms.semantic.lda.LDAFileFormatter;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.utils.Logger;

public final class App {

    public static void main(String[] args) throws Exception {
        AppConfig config = AppConfig.load("config.json");

        Sessions sessions = new Sessions();

        if (config.loadSessionTrack2011()) {
            log("Loading SessionTrack2011...");

            new SessionTrackParser(new SessionTrack2011()).parse(config.getSessionTrack2011File(), sessions);
        }

        if (config.loadSessionTrack2012()) {
            log("Loading SessionTrack2012...");

            new SessionTrackParser(new SessionTrack2012()).parse(config.getSessionTrack2012File(), sessions);
        }

        if (config.loadSessionTrack2013()) {
            log("Loading SessionTrack2013...");

            new SessionTrackParser(new SessionTrack2013()).parse(config.getSessionTrack2013File(), sessions);
        }

        if (config.loadSessionTrack2014()) {
            log("Loading SessionTrack2014...");

            new SessionTrackParser(new SessionTrack2014()).parse(config.getSessionTrack2014File(), sessions);
        }

        // Load Soke sessions only if we are not loading Session track sessions
        if (!config.loadingSessionTrackSessions() && config.loadSokeSessions()) {
            log("Loading Soke sessions...");

            new SokeParser().parse(config.getSokeSessionsFile(), sessions);
        }

        log("Number of loaded sessions: " + sessions.size());

        // Load and download results contents
        if (config.downloadContentsOfClickedResults()) {
            Database db = SerializedDatabase.getInstance();
            db.setResultsContent(sessions.getSessions());

            ResultsContentDownloader d = new ResultsContentDownloader(db);
            d.addAll(sessions.getSessions());
            d.downloadClicked(true);
        }

        // Separate training and testing sessions
        SessionsSplitter sessionsSplitter = new SessionsSplitter(
            config.getRatioBetweenTrainingAndTestingSessions());

        sessionsSplitter.splitSessions(sessions.getSessions());

        List<Session> testingSessions = sessionsSplitter.getTestingSessions();
        List<Session> trainingSessions = sessionsSplitter.getTrainingSessions();

        log("Number of training sessions: " + trainingSessions.size());
        log("Number of testing sessions: " + testingSessions.size());

        // Print number of testing queries
        int testingQueries = 0;

        for (Session testSession : testingSessions) {
            testingQueries += testSession.getNumberOfSearches();
        }

        log("Number of testing queries: " + testingQueries );

        // Print number of training queries
        int trainingQueries = 0;

        for (Session trainSession : trainingSessions) {
            trainingQueries += trainSession.getNumberOfSearches();
        }

        log("Number of training queries: " + trainingQueries);

        // Write training sessions to file for LDA
        LDAFileFormatter formatter = new LDAFileFormatter();
        formatter.write(trainingSessions);

        // Train LDA model from training sessions
        log("Training LDA model...");

        LDAModel lda = LDAModel.estimate(LDAFileFormatter.FILE, formatter,
            config.getLDATopics(), config.getLDAIterations());

        log("Training LDA model done");

        // Create extractor with trained LDA
        SessionSearchExtractor extractor = SessionSearchExtractor.buildDefault(lda);

        log(extractor);

        // Create training examples
        log("Creating training examples...");

        TrainingSetCreator creator = new TrainingSetCreator(extractor);
        TrainingSet set = creator.generateTrainingSet(trainingSessions);

        double[][] trainingExamples = set.getExamples();
        double[] trainingLabels = set.getLabels();

        FeatureNormalizer normalizer = new FeatureNormalizer(trainingExamples);
        normalizer.normalizeInPlace();

        log("Number of positive training examples: " + set.getPositiveExamples());
        log("Number of negative training examples: " + set.getNegativeExamples());

        // Train SVM from training examples
        log("Training SVM classifier...");

        SVMModel svmModel = SVMModel.train(trainingExamples, trainingLabels, config.getSVM_C());

        log("Training SVM classifier done");

        log(svmModel.toString());

        // Print original testing sessions queries
        printSessions("Original testing sessions", testingSessions, true);

        // Shuffle test sessions search results for session identification
        SessionTrackShuffler sessionTrackShuffler = new SessionTrackShuffler();

        List<Search> searches;

        if (config.loadingSessionTrackSessions() && config.shuffleSessionTrackTestingQueries()) {
            searches = sessionTrackShuffler.shuffle(testingSessions);
        } else {
            searches = Session.collectSearches(testingSessions);
        }

        // Evaluate baseline using Cosine similarity of queries
        eval("Baseline using Cosine similarity of queries", testingSessions, searches,
            new ConsecutiveSessionIdentifier(new CosineSimilarityConsecutiveApproach()));

        // Evaluate baseline using Jaccard similarity of queries
        eval("Baseline using Jaccard similarity of queries", testingSessions, searches,
            new ConsecutiveSessionIdentifier(new JaccardSimilarityConsecutiveApproach()));

        // Evaluate baseline using Temporal distance of queries
        eval("Baseline using Temporal distance of queries", testingSessions, searches,
            new ConsecutiveSessionIdentifier(new TemporalDistanceConsecutiveApproach()));

        // Evaluate Our method
        List<Session> identifiedSessions = eval("Our method", testingSessions, searches,
            new StackSessionIdentifier(new MethodStackApproach(extractor, svmModel, normalizer)));

        // Print identified sessions from test sessions
        printSessions("Identified sessions with our method", identifiedSessions, false);
    }

    private static void log(final Object o) {
        Logger.log(o);
    }

    private static void printSessions(final String header, final List<Session> sessions, final boolean printIntent) {
        log("\n***************************************");
        log(header);
        log("***************************************\n");

        for (Session session : sessions) {
            if (printIntent) {
                log(session.getIntent());
            }

            for (Search search : session.getAllSearches()) {
                log(search.getQuery());
            }

            log("-----------------");
        }
    }

    private static List<Session> eval(final String resultsType,
                             final List<Session> originalSessions,
                             final List<Search> searches,
                             final SessionIdentifier identifier) {
        identifier.identify(searches);

        List<Session> identifiedSessions = identifier.getIdentifiedSessions();

        Evaluator evaluator = new ShiftEvaluator();

        EvaluatorResult result = evaluator.evaluate(originalSessions, identifiedSessions);

        log("Results for: " + resultsType);
        log("\t" + result);

        return identifiedSessions;
    }

}
