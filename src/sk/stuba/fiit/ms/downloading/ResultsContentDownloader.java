package sk.stuba.fiit.ms.downloading;

import java.util.ArrayList;
import java.util.List;

import sk.stuba.fiit.ms.database.Database;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Result;
import sk.stuba.fiit.ms.session.Session;

public final class ResultsContentDownloader {

    private final List<Session> sessions;

    private final Database db;

    private ResultsContentDownloadManager dm;

    public ResultsContentDownloader(final Database db) {
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
        List<Result> results = new ArrayList<Result>();

        for (Session session : this.sessions) {
            for (Search search : session.getAllSearches()) {
                results.addAll(search.getClickedResults());
            }
        }

        this.dm = new ResultsContentDownloadManager(results, db);
        this.dm.start();

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
        return this.dm.isDone();
    }

}
