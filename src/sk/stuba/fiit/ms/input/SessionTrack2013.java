package sk.stuba.fiit.ms.input;

import org.w3c.dom.Node;

import sk.stuba.fiit.ms.session.Time;

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
    public Topic parseTopic(final Node node) {
        return new Topic2013(Integer.parseInt(Util.getAttrValue(node, "num")));
    }

    private static final class Topic2013 implements Topic {

        private final int id;

        public Topic2013(final int id) {
            this.id = id;
        }

        @Override
        public boolean same(final Topic t) {
            if (t instanceof Topic2013) {
                return ((Topic2013) t).id == this.id;
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "Topic2013[" + id + "]";
        }

    }

}
