package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.Search;

/**
 * Class used as baseline method for comparing our method with temporal distance approach.
 */
public final class TemporalDistanceConsecutiveApproach implements ConsecutiveApproach {

    // 25.5 minutes in milliseconds (25.5 minutes * 60 seconds * 1000 milliseconds)
    public static final long MAX_GAP = 1530_000L;

    /**
     * Returns true if time gap between two consecutive searches is smaller or equal than 25.5 minutes.
     * @param previous previous search
     * @param current search after previous search
     * @return true if time gap is smaller or equal
     */
    @Override
    public boolean isSameSession(final Search previous, final Search current) {
        return (current.getTimeStamp() - previous.getTimeStamp()) <= MAX_GAP;
    }

}
