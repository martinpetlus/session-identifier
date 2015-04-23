package sk.stuba.fiit.ms.document.downloading;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import sk.stuba.fiit.ms.database.Database;
import sk.stuba.fiit.ms.document.DocumentContent;
import sk.stuba.fiit.ms.utils.TextNormalizer;
import sk.stuba.fiit.ms.session.Result;

public final class Downloader {

    private final Database db;

    public Downloader(final Database db) {
        this.db = db;
    }

    public void download(final Result result) {
        String url = result.getUrl();

        try {
            Connection conn = Jsoup.connect(url);

            conn.timeout(20000);
            conn.userAgent("Mozilla");
            conn.followRedirects(false);

            Document doc = conn.get();

            String text = TextNormalizer.normalize(doc.text());

            if (text.length() > 0) {
                result.setContent(text);

                db.save(new DocumentContent(url, text));

                System.out.println("Downloaded content of doc: " + url);
            } else {
                System.out.println("Downloaded empty doc: " + url);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage() + " : " + url);
        }
    }

}
