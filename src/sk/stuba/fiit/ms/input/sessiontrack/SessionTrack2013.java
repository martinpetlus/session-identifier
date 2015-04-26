package sk.stuba.fiit.ms.input.sessiontrack;

import org.w3c.dom.Node;

import sk.stuba.fiit.ms.session.Time;
import sk.stuba.fiit.ms.session.Intent;

public final class SessionTrack2013 extends SessionTrackSkeleton {

    @Override
    public Time parseTime(final String time) {
        if (time.equals("NA")) {
            return TimeInSeconds.ZERO;
        } else {
            return new TimeInSeconds(Double.parseDouble(time));
        }
    }

    @Override
    public Intent parseTopic(final Node node) {
        return new Topic2013(Integer.parseInt(Util.getAttrValue(node, "num")));
    }

    private static final class Topic2013 implements Intent {

        private final int id;

        public Topic2013(final int id) {
            this.id = id;
        }

        @Override
        public boolean same(final Intent intent) {
            if (intent instanceof Topic2013) {
                return ((Topic2013) intent).id == this.id;
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
