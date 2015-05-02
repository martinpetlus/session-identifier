package sk.stuba.fiit.ms.utils.stemming;

public final class PorterStemmer {

    private final Stemmer stemmer;

    public PorterStemmer() {
        stemmer = new Stemmer();
    }

    public String stem(final String word) {
        for (int i = 0; i < word.length(); i++) {
            stemmer.add(word.charAt(i));
        }

        stemmer.stem();

        return stemmer.toString();
    }

    public String[] stem(final String[] words) {
        String[] stemmedWords = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            stemmedWords[i] = stem(words[i]);
        }

        return stemmedWords;
    }

}
