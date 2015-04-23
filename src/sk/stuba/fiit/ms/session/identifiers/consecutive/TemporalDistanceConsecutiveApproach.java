package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.Search;

public class TemporalDistanceConsecutiveApproach implements ConsecutiveApproach {

    // 25.5 minutes
    public static final int MAX_GAP_IN_SECONDS = 1530;

    @Override
    public boolean isSameSession(Search previous, Search current) {
        return (current.getTimeStamp() - previous.getTimeStamp()) <= MAX_GAP_IN_SECONDS;
    }

}
