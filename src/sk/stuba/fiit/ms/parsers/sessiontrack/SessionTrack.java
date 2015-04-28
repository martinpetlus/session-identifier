package sk.stuba.fiit.ms.parsers.sessiontrack;

import org.w3c.dom.Node;

import sk.stuba.fiit.ms.session.*;

interface SessionTrack {

    Time parseTime(final String time);

    Search parseInteraction(final Node node, final Session session);

    Session parseSession(final Node node);

    Result parseResult(final Node node);

    Click parseClick(final Node node);

    Intent parseTopic(final Node node);

}
