package sk.stuba.fiit.ms.database;

import java.util.List;

import sk.stuba.fiit.ms.document.DocumentContent;
import sk.stuba.fiit.ms.session.Session;

public interface Database {

    boolean containsContent(final String url);

    DocumentContent getContent(final String url);

    void save(final DocumentContent doc);

    void close();

    int size();

    List<DocumentContent> getAllContents();

    void setResultsContent(final List<Session> sessions);

}
