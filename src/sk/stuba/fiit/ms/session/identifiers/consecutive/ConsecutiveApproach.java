package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.Search;

/**
 * Interface to be implemented by various consecutive approaches.
 */
public interface ConsecutiveApproach {

    /**
     * Returns true if two consecutive searches belong to the same session.
     * @param previous previous search
     * @param current search following previous search
     * @return true if searches belongs to the same session
     */
    boolean isSameSession(final Search previous, final Search current);

}
