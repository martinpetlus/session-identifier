package sk.stuba.fiit.ms.apps;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.input.sessiontrack.*;
import sk.stuba.fiit.ms.session.identifiers.stack.StackSessionIdentifier;
import sk.stuba.fiit.ms.evaluation.Evaluator;
import sk.stuba.fiit.ms.evaluation.Shuffler;
import sk.stuba.fiit.ms.database.Database;
import sk.stuba.fiit.ms.database.FileDatabase;
import sk.stuba.fiit.ms.features.FeatureNormalizer;
import sk.stuba.fiit.ms.features.extract.SessionExtractor;
import sk.stuba.fiit.ms.learning.SVM;
import sk.stuba.fiit.ms.learning.DataSet;
import sk.stuba.fiit.ms.learning.dataset.generation.SetGenerator;
import sk.stuba.fiit.ms.learning.test.SessionsSeparator;
import sk.stuba.fiit.ms.semantic.lda.LDAFileFormatter;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class Main {

    public static void main(String[] args) {
        System.out.println("Loading sessions from files...");

        List<Session> sessions = new ArrayList<Session>();

//        new SessionTrackParser(new SessionTrack2013()).parse("data/sessiontrack2013.xml", sessions);
//
//        new SessionTrackParser(new SessionTrack2012()).parse("data/sessiontrack2012.xml", sessions);
//
//        new SessionTrackParser(new SessionTrack2011()).parse("data/sessiontrack2011.RL4.xml", sessions);

        new SessionTrackParser(new SessionTrack2014()).parse("data/sessiontrack2014.xml", sessions);

        System.out.println("Number of loaded sessions from files: " + sessions.size());

        // Load and download results contents
        Database db = FileDatabase.getInstance();
        db.setResultsContent(sessions);

//        SessionDownloader sd = new SessionDownloader(db);
//        sd.addAll(sessions);
//        sd.downloadClicked(true);

        // Separate train and test sessions
        SessionsSeparator sessionsSeparator = new SessionsSeparator(sessions, 0.94);
        sessionsSeparator.splitSessions();
        sessionsSeparator.splitQueriesInTestingSessions();
        List<Session> testSessions = sessionsSeparator.getTestingSessions();
        List<Session> trainSessions = sessionsSeparator.getTrainingSessions();

        // Print number of test queries
        int testQueries = 0;

        for (Session testSession : testSessions) {
            testQueries += testSession.getNumberOfSearches();
        }

        System.out.println("Number of test queries: " + testQueries);

        // Print number of train queries
        int trainQueries = 0;

        for (Session trainSession : trainSessions) {
            trainQueries += trainSession.getNumberOfSearches();
        }

        System.out.println("Number of train queries: " + trainQueries);

        // Write train sessions to file for LDA
        LDAFileFormatter formatter = new LDAFileFormatter();
        formatter.write(trainSessions);

        // Train LDA model from train sessions
        System.out.println("Traning LDA model...");

        int topics = 100;
        LDAModel lda = LDAModel.estimate(LDAFileFormatter.FILE, formatter, topics);

        System.out.println("Traning LDA model done");

        // Create session extractor with trained LDA
        SessionExtractor extractor = new SessionExtractor(lda);

        System.out.println(extractor.getFeaturesExtractor());

        // Generate training examples
        System.out.println("Generating training examples...");

        SetGenerator generator = new SetGenerator(extractor, 2, 20);
        DataSet set = generator.generateSet(trainSessions);

        double[][] trainingExamples = set.getExamples();
        double[] trainingLabels = set.getLabels();

        FeatureNormalizer normalizer = new FeatureNormalizer(trainingExamples);
        normalizer.normalizeInPlace();

        System.out.println("Number of generated training examples: " + trainingExamples.length);

        // Train SVM from training examples
        System.out.println("Traning SVM classifier...");

        SVM svm = SVM.train(trainingExamples, trainingLabels);

        System.out.println("Traning SVM classifier done");

        svm.printModel();

        // Print original test sessions queries
        System.out.println("\nOriginal test sessions:\n");

        for (Session session : testSessions) {
            System.out.println(session.getIntent());

            for (Search search : session.getAllSearches()) {
                System.out.println(search.getQuery());
            }

            System.out.println("-----------------");
        }

        // Shuffle test sessions search results for session identification
        Shuffler shuffler = new Shuffler();
        List<Search> searches = shuffler.shuffle(testSessions);

        // Identify session from test shuffled search results
        StackSessionIdentifier identifier = new StackSessionIdentifier(extractor, svm, normalizer);

        identifier.identifyAll(searches);

        // Print identified sessions from test sessions
        System.out.println("\n***************************************");
        System.out.println("Identified sessions from test sessions:");
        System.out.println("***************************************\n");

        for (Session session : identifier.getIdentifiedSessions()) {
            for (Search search : session.getAllSearches()) {
                System.out.println(search.getQuery());
            }

            System.out.println("-----------------");
        }

        // Evaluate results
        Evaluator.Results evalResults = Evaluator.evaluate(testSessions, identifier.getIdentifiedSessions());
        System.out.println(evalResults);
    }

}
