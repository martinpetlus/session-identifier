package sk.stuba.fiit.ms.document.downloading;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sk.stuba.fiit.ms.database.Database;
import sk.stuba.fiit.ms.session.Result;


public final class DownloadManager {
	
	private final Object mutex = new Object();
	
	private final Set<Result> results = new HashSet<Result>();
	
	private final ExecutorService executorService;
	
	private final int DOWNLOADERS = 10;
	
	private final Database db;
	
	public DownloadManager(final List<Result> results, final Database db) {
		this.db = db;
		
		for (Result result : results) {
			this.results.add(result);
		}
		
		this.executorService = Executors.newFixedThreadPool(DOWNLOADERS);
	}
	
	public void start() {
		for (int i = 0; i < DOWNLOADERS; i++) {
			executorService.execute(new Runnable() {
			    public void run() {
			    	Downloader downloader = new Downloader(db);
			    	
			    	while (true) {
			    		Result result;
			    		
			    		synchronized (mutex) {
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
			    		
			    		downloader.download(result);
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
