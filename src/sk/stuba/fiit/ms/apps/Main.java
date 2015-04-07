package sk.stuba.fiit.ms.apps;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.algorithm.SessionsIdentifier;
import sk.stuba.fiit.ms.algorithm.evaluation.Evaluator;
import sk.stuba.fiit.ms.algorithm.evaluation.Shuffler;
import sk.stuba.fiit.ms.database.Database;
import sk.stuba.fiit.ms.database.FileDatabase;
import sk.stuba.fiit.ms.features.FeatureNormalizer;
import sk.stuba.fiit.ms.features.extract.SessionExtractor;
import sk.stuba.fiit.ms.input.SessionTrack2011;
import sk.stuba.fiit.ms.input.SessionTrack2012;
import sk.stuba.fiit.ms.input.SessionTrack2013;
import sk.stuba.fiit.ms.input.SessionTrackParser;
import sk.stuba.fiit.ms.learning.SVM;
import sk.stuba.fiit.ms.learning.DataSet;
import sk.stuba.fiit.ms.learning.dataset.generation.SetGenerator;
import sk.stuba.fiit.ms.semantic.lda.LDAFileFormatter;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.SearchResult;
import sk.stuba.fiit.ms.session.Session;

public class Main {

	public static void main(String[] args) {
		System.out.println("Loading sessions from files...");

		List<Session> sessions = new ArrayList<Session>();

		SessionTrackParser parser = new SessionTrackParser();

		parser.setSessionTrack(new SessionTrack2013());
		parser.parse("data/sessiontrack2013.xml", sessions);

		parser.setSessionTrack(new SessionTrack2012());
		parser.parse("data/sessiontrack2012.xml", sessions);

		parser.setSessionTrack(new SessionTrack2011());
		parser.parse("data/sessiontrack2011.RL4.xml", sessions);

		System.out.println("Number of loaded sessions from files: " + sessions.size());

//		Load and download results contents
		Database db = FileDatabase.getInstance();
		db.setResultsContent(sessions);

//		SessionDownloader sd = new SessionDownloader(db);
//		sd.addAll(sessions);
//		sd.downloadClicked(true);

//		Separate train and test sessions
		int index = 25;
		List<Session> testSessions  = sessions.subList(0, index);
		List<Session> trainSessions = sessions.subList(index, sessions.size());

//		Print number of test queries
		int testQueries = 0;

		for (Session testSession : testSessions) {
			testQueries += testSession.getNumberOfSearchResults();
		}

		System.out.println("Number of test queries: " + testQueries);

//		Print number of train queries
		int trainQueries = 0;

		for (Session trainSession : trainSessions) {
			trainQueries += trainSession.getNumberOfSearchResults();
		}

		System.out.println("Number of train queries: " + trainQueries);

//		Write train sessions to file for LDA
		LDAFileFormatter formatter = new LDAFileFormatter(true, true, false);
		formatter.write(trainSessions);

//		Train LDA model from train sessions
		System.out.println("Traning LDA model...");

		int topics = 100;
		LDAModel lda = LDAModel.estimate(LDAFileFormatter.FILE, formatter, topics);

		System.out.println("Traning LDA model done");

//		Create session extractor with trained LDA
		SessionExtractor extractor = new SessionExtractor(lda);

//		Generate training examples
		System.out.println("Generating training examples...");

		SetGenerator generator = new SetGenerator(extractor, 2, 20);
		DataSet set = generator.generateSet(trainSessions);

		double[][] trainingExamples = set.getExamples();
		double[] trainingLabels = set.getLabels();

		FeatureNormalizer normalizer = new FeatureNormalizer(trainingExamples);
		normalizer.normalizeInPlace();

		System.out.println("Number of generated training examples: " + trainingExamples.length);

//		Train SVM from training examples
		System.out.println("Traning SVM classifier...");

		SVM svm = SVM.train(trainingExamples, trainingLabels);

		System.out.println("Traning SVM classifier done");

		svm.printModel();

//		Print original test sessions queries
		System.out.println();

		for (Session session : testSessions) {
			System.out.println(session.getTopic());
			for (SearchResult result : session.getAllSearchResults()) {
				System.out.println(result.getQuery());
			}
			System.out.println("-----------------");
		}

		System.out.println("\n***********************\n");

//		Shuffle test sessions search results for session identification
		Shuffler shuffler = new Shuffler();
		List<SearchResult> searchResults = shuffler.shuffle(testSessions);

//		Identify session from test shuffled search results
		SessionsIdentifier identifier = new SessionsIdentifier(extractor, svm, normalizer);

		identifier.addAll(searchResults);

//		Print identified sessions from test sessions
		for (Session session : identifier.getSessions()) {
			for (SearchResult result : session.getAllSearchResults()) {
				System.out.println(result.getQuery());
			}
			System.out.println("-----------------");
		}

//		Evaluate results
		Evaluator.Results evalResults = Evaluator.evaluate(testSessions, identifier.getSessions());
		System.out.println(evalResults);
	}

}
