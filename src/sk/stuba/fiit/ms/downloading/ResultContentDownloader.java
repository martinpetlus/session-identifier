package sk.stuba.fiit.ms.downloading;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import sk.stuba.fiit.ms.database.Database;
import sk.stuba.fiit.ms.utils.Logger;
import sk.stuba.fiit.ms.utils.TextNormalizer;
import sk.stuba.fiit.ms.session.Result;

public final class ResultContentDownloader {

    private final Database db;

    public ResultContentDownloader(final Database db) {
        this.db = db;
    }

    public void downloadContent(final Result result) {
        String url = result.getUrl();

        try {
            Connection conn = Jsoup.connect(url);

            conn.timeout(20_000);
            conn.userAgent("Mozilla");
            conn.followRedirects(false);

            Document doc = conn.get();

            String text = TextNormalizer.normalize(doc.text());

            if (text.length() > 0) {
                // Set content of result
                result.setContent(text);

                // Save content of result to the database to load content next time from database
                db.save(new ResultContent(url, text));

                Logger.log("Downloaded content of result: " + url);
            }
        } catch (IOException e) {
            Logger.err(e.getMessage() + ": " + url);
        }
    }

}
