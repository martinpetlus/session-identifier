package sk.stuba.fiit.ms.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class TextNormalizer {

    private static final Stemmer stemmer = new Stemmer();

    private static String stopWords = "";

    static {
        try {
            StringBuilder sb = new StringBuilder();

            Charset charset = Charset.forName("UTF-8");

            for (String word : Files.readAllLines(Paths.get("stopwords.txt"), charset)) {
                word = word.trim();

                if (sb.length() != 0) {
                    sb.append("|");
                }

                sb.append(word);
            }

            stopWords = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TextNormalizer() {}

    private static String stemWord(final String word) {
        for (int i = 0; i < word.length(); i++) {
            stemmer.add(word.charAt(i));
        }

        stemmer.stem();

        return stemmer.toString();
    }

    private static String stemWords(final String words) {
        StringBuilder sb = new StringBuilder();

        String[] parts = words.split("\\s+");

        for (int i = 0; i < parts.length; i++) {
            parts[i] = stemWord(parts[i]);

            sb.append(parts[i]);

            if (i + 1 < parts.length) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    public static String normalize(String s) {
        s = s.trim().toLowerCase();

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

        return stemWords(s.trim());
    }

    public static String[] split(final String s) {
        return s.split("\\s+");
    }

}
