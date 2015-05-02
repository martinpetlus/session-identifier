package sk.stuba.fiit.ms.utils;

import sk.stuba.fiit.ms.utils.stemming.PorterStemmer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class TextNormalizer {

    private static final PorterStemmer porterStemmer = new PorterStemmer();

    private static String stopWords = "";

    static {
        try {
            StringBuilder sb = new StringBuilder();

            Charset charset = Charset.forName("UTF-8");

            for (String word : Files.readAllLines(Paths.get("stopwords.txt"), charset)) {
                word = word.trim();

                if (sb.length() != 0) {
                    sb.append('|');
                }

                sb.append(word);
            }

            stopWords = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TextNormalizer() {}

    public static synchronized String normalize(final String str) {
        String s = str.trim().toLowerCase();

        // Remove unicode characters
        s = s.replaceAll("\\\\u[0-9a-f]{4}", "");

        // Remove escape sequences
        s = s.replaceAll("\\\\.", "");

        // Remove html encodes
        s = s.replaceAll("&[a-z]+;", "");

        // Remove non-word characters
        s = s.replaceAll("\\s+\\W\\s+", " ");

        // Remove stop words
        if (stopWords.length() > 0) {
            s = s.replaceAll("\\b(" + stopWords + ")\\b", "");
        }

        s = s.replaceAll("[\\s\\W_]+", " ");

        // Remove numbers
        s = s.replaceAll("\\s+\\d+\\s+", " ");

        // Remove short words
        s = s.replaceAll("^[\\w]{1,1}\\s+|\\s+[\\w]{1,1}\\s+|\\s+[\\w]{1,1}$", " ");

        return join(porterStemmer.stem(split(s.trim())));
    }

    public static String join(final String[] s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length; i++) {
            sb.append(s[i]);

            if (i + 1 < s.length) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    public static String[] split(final String s) {
        return s.split("\\s+");
    }

}
