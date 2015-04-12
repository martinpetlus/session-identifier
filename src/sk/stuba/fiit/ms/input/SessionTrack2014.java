package sk.stuba.fiit.ms.input;

import org.w3c.dom.Node;

import sk.stuba.fiit.ms.session.Time;

public final class SessionTrack2014 extends SessionTrackSkeleton {

    @Override
    public Time parseTime(final String time) {
        return new TimeInSeconds(Double.parseDouble(time));
    }

    @Override
    public Topic parseTopic(final Node node) {
        return new Topic2014(Integer.parseInt(Util.getAttrValue(node, "num")));
    }

    private static final class Topic2014 implements Topic {

        private final int id;

        public Topic2014(final int id) {
            this.id = id;
        }

        @Override
        public boolean same(final Topic t) {
            if (t instanceof Topic2014) {
                return ((Topic2014) t).id == this.id;
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "Topic2014[" + id + "]";
        }

    }

}
