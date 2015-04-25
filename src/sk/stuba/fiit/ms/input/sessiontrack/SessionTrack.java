package sk.stuba.fiit.ms.input.sessiontrack;

import org.w3c.dom.Node;

import sk.stuba.fiit.ms.session.*;

public interface SessionTrack {

    Time parseTime(final String time);

    Search parseInteraction(final Node node, final Session session);

    Session parseSession(final Node node);

    Result parseResult(final Node node);

    Click parseClick(final Node node);

    Topic parseTopic(final Node node);

}
