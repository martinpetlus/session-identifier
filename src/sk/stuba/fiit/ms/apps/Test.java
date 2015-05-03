package sk.stuba.fiit.ms.apps;

import sk.stuba.fiit.ms.evaluation.Evaluator;
import sk.stuba.fiit.ms.parsers.Sessions;
import sk.stuba.fiit.ms.parsers.soke.SokeParser;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.session.identifiers.SessionIdentifier;
import sk.stuba.fiit.ms.session.identifiers.consecutive.ConsecutiveSessionIdentifier;
import sk.stuba.fiit.ms.session.identifiers.consecutive.TemporalDistanceConsecutiveApproach;

import java.util.ArrayList;
import java.util.List;

public final class Test {

    public static Session findSession(final List<Session> sessions, final Search search) {
        for (Session session : sessions) {
            int index = session.getAllSearches().indexOf(search);

            if (index >= 0) {
                return session;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Sessions sessions = new Sessions();

        new SokeParser().parse("data/soke-20150424.json", sessions);

//        new SessionTrackParser(new SessionTrack2014()).parse("data/sessiontrack2014.xml", sessions);

        System.out.println("Original sessions:");

        SessionIdentifier sessionIdentifier =
            new ConsecutiveSessionIdentifier(new TemporalDistanceConsecutiveApproach());

        List<Search> searches = new ArrayList<Search>();

        for (Session session : sessions.getSessions()) {
            System.out.println("Intent=" + session.getIntent());

            for (Search search : session.getAllSearches()) {
                System.out.println(search.getQuery());

                searches.add(search);
            }

            System.out.println("-----------------");
        }

        sessionIdentifier.identify(searches);

//        for (int i = 0; i < searches.size(); i++) {
//            if (i > 0) {
//                System.out.println(searches.get(i-1).getTimeStamp() < searches.get(i).getTimeStamp());
//            }
//        }

        System.out.println("\nIdentified sessions:");

        for (Session session : sessionIdentifier.getIdentifiedSessions()) {
            for (Search search : session.getAllSearches()) {
                System.out.println(search.getQuery() + ": " +
                    findSession(sessions.getSessions(), search).getIntent());
            }

            System.out.println("-----------------");
        }

        Evaluator.Results evalResults = Evaluator.evaluate(sessions.getSessions(), sessionIdentifier.getIdentifiedSessions());
        System.out.println(evalResults);
    }

}
