package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.Util;
import sk.stuba.fiit.ms.session.Search;

public final class QueryCommonWords implements PairFeature {

    private final Direction dir;

    public QueryCommonWords(final Direction dir) {
        this.dir = dir;
    }

    public enum Direction {

        LEFT {

            @Override
            int compute(final String[] one, final String[] two) {
                int count = 0;

                for (int i = 0; i < one.length && i < two.length; i++) {
                    if (one[i].equals(two[i])) {
                        count++;
                    } else {
                        break;
                    }
                }

                return count;
            }

        },

        RIGHT {

            @Override
            int compute(final String[] one, final String[] two) {
                int count = 0;

                for (int i = one.length - 1, j = two.length - 1; i >= 0 && j >= 0; i--, j--) {
                    if (one[i].equals(two[j])) {
                        count++;
                    } else {
                        break;
                    }
                }

                return count;
            }

        },

        NO {

            @Override
            int compute(final String[] one, final String[] two) {
                return Util.intersection(one, two).length;
            }

        };

        abstract int compute(final String[] one, final String[] two);

    }

    @Override
    public double extract(Search search, Search compareTo) {
        String[] query1 = TextNormalizer.split(search.getQuery());
        String[] query2 = TextNormalizer.split(compareTo.getQuery());

        return dir.compute(query1, query2);
    }

}
