package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.Search;

public interface ConsecutiveApproach {

    boolean isSameSession(final Search previous, final Search current);

}
