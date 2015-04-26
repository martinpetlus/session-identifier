package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.Search;

public final class TemporalDistanceConsecutiveApproach implements ConsecutiveApproach {

    // 25.5 minutes
    public static final long MAX_GAP_IN_MILLIS = 1530_000L;

    @Override
    public boolean isSameSession(final Search previous, final Search current) {
        return (current.getTimeStamp() - previous.getTimeStamp()) <= MAX_GAP_IN_MILLIS;
    }

}
