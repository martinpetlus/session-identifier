package sk.stuba.fiit.ms.document.downloading;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.database.Database;
import sk.stuba.fiit.ms.session.SearchResult;
import sk.stuba.fiit.ms.session.Result;
import sk.stuba.fiit.ms.session.Session;

public final class SessionDownloader {

    private final List<Session> sessions;

    private final Database db;

    private DownloadManager dw;

    public SessionDownloader(final Database db) {
        this.db = db;
        this.sessions = new ArrayList<Session>();
    }

    public void add(final Session session) {
        this.sessions.add(session);
    }

    public void addAll(final List<Session> sessions) {
        this.sessions.addAll(sessions);
    }

    public void downloadClicked (final boolean wait) {
        final List<Result> results = new ArrayList<Result>();

        for (Session session : this.sessions) {
            for (SearchResult searchResult : session.getAllSearchResults()) {
                results.addAll(searchResult.getClickedResults());
            }
        }

        this.dw = new DownloadManager(results, db);
        this.dw.start();

        if (wait) {
            while (!this.isDone()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

            db.close();
        }
    }

    public boolean isDone() {
        return this.dw.isDone();
    }

}
