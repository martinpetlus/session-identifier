package sk.stuba.fiit.ms.apps;

import sk.stuba.fiit.ms.database.Database;
import sk.stuba.fiit.ms.database.FileDatabase;
import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.semantic.CosineOfClickedResults;
import sk.stuba.fiit.ms.input.SessionTrack2011;
import sk.stuba.fiit.ms.input.SessionTrack2012;
import sk.stuba.fiit.ms.input.SessionTrack2013;
import sk.stuba.fiit.ms.input.SessionTrackParser;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.semantic.lda.LDAFileFormatter;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.ArrayList;
import java.util.List;

public final class Test {

    public static void main(String[] args) {
        List<Session> sessions = new ArrayList<Session>();

        SessionTrackParser parser = new SessionTrackParser();

        parser.setSessionTrack(new SessionTrack2012());
        parser.parse("data/sessiontrack2012.xml", sessions);

        parser.setSessionTrack(new SessionTrack2013());
        parser.parse("data/sessiontrack2013.xml", sessions);

        parser.setSessionTrack(new SessionTrack2011());
        parser.parse("data/sessiontrack2011.RL4.xml", sessions);

        Database db = FileDatabase.getInstance();

        int index = 15;
        List<Session> testSessions  = sessions.subList(0, index);
        List<Session> trainSessions = sessions.subList(index, sessions.size());


//        SessionDownloader sd = new SessionDownloader(db);
//        sd.addAll(subSessions);
//        sd.downloadClicked(true);

        LDAFileFormatter formatter = new LDAFileFormatter(true, true, false);

        formatter.write(trainSessions);

        LDAModel model = LDAModel.estimate(LDAFileFormatter.FILE, formatter, 100);

        PairFeature pairFeature = new CosineOfClickedResults(model);

        for (Session session : testSessions) {
            if (session.getNumberOfSearches() > 1) {
                Search sr1 = session.getSearch(0);

                if (sr1.hasClickedResults()) {
                    for (int i = 1; i < session.getNumberOfSearches(); i++) {
                        Search sr2 = session.getSearch(i);

                        if (sr2.hasClickedResults()) {
                            System.out.println(pairFeature.extract(sr1, sr2));
                        }
                    }
                }
            }

            System.out.println("****");
        }
    }

}
