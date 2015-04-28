package sk.stuba.fiit.ms.parsers.sessiontrack;

import org.w3c.dom.Node;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;
import sk.stuba.fiit.ms.session.Time;
import sk.stuba.fiit.ms.session.Intent;

public final class SessionTrack2014 extends SessionTrackSkeleton {

    @Override
    public Time parseTime(final String time) {
        return new TimeInSeconds(Double.parseDouble(time));
    }

    @Override
    public Intent parseTopic(final Node node) {
        return new Topic2014(Integer.parseInt(Util.getAttrValue(node, "num")));
    }

    @Override
    public Session parseSession(final Node node) {
        Session session = super.parseSession(node);

        String userId = Util.getAttrValue(node, "userid");

        if (!userId.equals("NA")) {
            session.setUserId(Integer.parseInt(userId));
        }

        return session;
    }

    @Override
    public Search parseInteraction(final Node node, final Session session) {
        Search search = super.parseInteraction(node, session);

        if (Util.getAttrValue(node, "type").equals("page")) {
            session.getNewestSearch().mergeIn(search);
            return null;
        } else {
            return search;
        }
    }

    private static final class Topic2014 implements Intent {

        private final int id;

        public Topic2014(final int id) {
            this.id = id;
        }

        @Override
        public boolean same(final Intent intent) {
            if (intent instanceof Topic2014) {
                return ((Topic2014) intent).id == this.id;
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "[" + id + "]";
        }

    }

}
