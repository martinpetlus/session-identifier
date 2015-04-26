package sk.stuba.fiit.ms.input.sessiontrack;

import org.w3c.dom.Node;
import sk.stuba.fiit.ms.session.Intent;

public class SessionTrack2012 extends SessionTrackSkeleton {

    @Override
    public Intent parseTopic(final Node node) {
        return new Topic2012(Integer.parseInt(Util.getAttrValue(node, "num")));
    }

    private static final class Topic2012 implements Intent {

        private final int id;

        public Topic2012(final int id) {
            this.id = id;
        }

        @Override
        public boolean same(final Intent intent) {
            if (intent instanceof Topic2012) {
                return ((Topic2012) intent).id == this.id;
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
