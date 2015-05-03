package sk.stuba.fiit.ms.utils;

import java.util.HashSet;
import java.util.Set;

public final class SetUtils {

    private SetUtils() {}

    /**
     * Returns union of two string arrays.
     * @param one first string array
     * @param two second string array
     * @return union of two string arrays
     */
    public static String[] union(final String[] one, final String[] two) {
        Set<String> set = new HashSet<String>();

        for (String el : one) {
            set.add(el);
        }

        for (String el : two) {
            set.add(el);
        }

        return set.toArray(new String[set.size()]);
    }

    /**
     * Returns intersection of two string arrays.
     * @param one first string array
     * @param two second string array
     * @return intersection of two string arrays
     */
    public static String[] intersection(final String[] one, final String[] two) {
        Set<String> set1 = new HashSet<String>();
        Set<String> set2 = new HashSet<String>();

        for (String el : one) {
            set1.add(el);
        }

        for (String el : two) {
            if (set1.contains(el)) {
                set2.add(el);
            }
        }

        return set2.toArray(new String[set2.size()]);
    }

}
