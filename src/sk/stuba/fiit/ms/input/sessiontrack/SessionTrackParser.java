package sk.stuba.fiit.ms.input.sessiontrack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class SessionTrackParser {

    private List<Session> sessions;

    private SessionTrack sessionTrack;

    public void parse(final SessionTrack sessionTrack, final String file, final List<Session> sessions) {
        if (sessionTrack == null) {
            throw new IllegalArgumentException("Illegal session track");
        }

        this.sessionTrack = sessionTrack;

        try {
            File xmlFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            process(doc, sessions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(final Document doc, final List<Session> sessions) {
        this.sessions = new ArrayList<Session>();

        NodeList childNodes = doc.getElementsByTagName("session");

        for (int i = 0; i < childNodes.getLength(); i++) {
            addSession(sessionTrack.parseSession(childNodes.item(i)));
        }

        sessions.addAll(this.sessions);
    }

    private void addSession(final Session session) {
        if (!session.isEmpty()) {
            for (Session s : this.sessions) {
                if (s.getTopic().same(session.getTopic())) {
                    for (Search search : session.getAllSearches()) {
                        s.add(search);
                    }

                    return;
                }
            }

            this.sessions.add(session);
        }
    }

}
