package sk.stuba.fiit.ms.downloading;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sk.stuba.fiit.ms.database.Database;
import sk.stuba.fiit.ms.session.Result;

final class ResultsContentDownloadManager {

    private final Object lock = new Object();

    private final Set<Result> results;

    private final ExecutorService executorService;

    private final int THREADS = 10;

    private final Database db;

    public ResultsContentDownloadManager(final List<Result> results, final Database db) {
        this.results = new HashSet<Result>();

        this.db = db;

        for (Result result : results) {
            this.results.add(result);
        }

        this.executorService = Executors.newFixedThreadPool(THREADS);
    }

    public void start() {
        for (int i = 0; i < THREADS; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    ResultContentDownloader downloader = new ResultContentDownloader(db);

                    while (true) {
                        Result result;

                        synchronized (lock) {
                            if (!results.isEmpty()) {
                                result = results.iterator().next();

                                results.remove(result);

                                String url = result.getUrl();

                                if (db.containsContent(url)) {
                                    continue;
                                }
                            } else {
                                break;
                            }
                        }

                        downloader.downloadContent(result);
                    }
                }
            });
        }

        executorService.shutdown();
    }

    public boolean isDone() {
        return executorService.isTerminated();
    }

}
