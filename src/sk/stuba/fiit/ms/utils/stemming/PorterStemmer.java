package sk.stuba.fiit.ms.utils.stemming;

/**
 * This class is wrapper class for (@see sk.stuba.fiit.ms.utils.stemming.Stemmer).
 * It stems word, or array of words using Porter stemmer algorithm.
 */
public final class PorterStemmer {

    private final Stemmer stemmer;

    public PorterStemmer() {
        stemmer = new Stemmer();
    }

    /**
     * Stems the specified word.
     * @param word word to stem
     * @return stemmed word
     */
    public String stem(final String word) {
        for (int i = 0; i < word.length(); i++) {
            stemmer.add(word.charAt(i));
        }

        stemmer.stem();

        return stemmer.toString();
    }

    /**
     * Stems the specified words in array.
     * @param words array of words to stem
     * @return array containing the stemmed words
     */
    public String[] stem(final String[] words) {
        String[] stemmedWords = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            stemmedWords[i] = stem(words[i]);
        }

        return stemmedWords;
    }

}
