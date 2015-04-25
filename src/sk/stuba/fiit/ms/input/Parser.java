package sk.stuba.fiit.ms.input;

import sk.stuba.fiit.ms.session.Session;

import java.util.List;

public interface Parser {

    void parse(final String file, final List<Session> sessions);

}
