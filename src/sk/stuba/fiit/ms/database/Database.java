package sk.stuba.fiit.ms.database;

import java.util.List;

import sk.stuba.fiit.ms.ResultContent;
import sk.stuba.fiit.ms.session.Session;

public interface Database {

    boolean containsContent(final String url);

    ResultContent getContent(final String url);

    void save(final ResultContent doc);

    void close();

    int size();

    List<ResultContent> getAllContents();

    void setResultsContent(final List<Session> sessions);

}
