package sk.stuba.fiit.ms.input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import sk.stuba.fiit.ms.session.SearchResult;
import sk.stuba.fiit.ms.session.Session;

public final class SessionTrackParser {

    private List<Session> sessions;

    private SessionTrack sessionTrack;

    public SessionTrackParser() {
        this(null);
    }

    public SessionTrackParser(final SessionTrack sessionTrack) {
        this.sessionTrack = sessionTrack;
    }

    public void parse(final String file, final List<Session> sessions) {
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

    public void setSessionTrack(final SessionTrack sessionTrack) {
        this.sessionTrack = sessionTrack;
    }

    private void process(final Document doc, final List<Session> sessions) {
        this.sessions = new ArrayList<Session>();

        NodeList childs = doc.getElementsByTagName("session");

        for (int i = 0; i < childs.getLength(); i++) {
            addSession(sessionTrack.parseSession(childs.item(i)));
        }

        sessions.addAll(this.sessions);
    }

    private void addSession(final Session session) {
        if (!session.isEmpty()) {
            for (Session s : this.sessions) {
                if (s.getTopic().same(session.getTopic())) {
                    for (SearchResult sr : session.getAllSearchResults()) {
                        s.add(sr);
                    }

                    return;
                }
            }

            this.sessions.add(session);
        }
    }

}
