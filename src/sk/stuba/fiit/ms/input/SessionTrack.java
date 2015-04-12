package sk.stuba.fiit.ms.input;

import org.w3c.dom.Node;

import sk.stuba.fiit.ms.session.*;
import sk.stuba.fiit.ms.session.Search;

public interface SessionTrack {

    public Time parseTime(final String time);

    public Search.Builder parseInteraction(final Node node, final Session session);

    public Session parseSession(final Node node);

    public Result parseResult(final Node node);

    public Click parseClick(final Node node);

    public Topic parseTopic(final Node node);

}
