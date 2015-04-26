package sk.stuba.fiit.ms.input.sessiontrack;

import org.w3c.dom.Node;
import sk.stuba.fiit.ms.session.Intent;

public class SessionTrack2011 extends SessionTrackSkeleton {

    @Override
    public Intent parseTopic(final Node node) {
        return new Topic2011(Util.getChildValue(node, "title"));
    }

    private static final class Topic2011 implements Intent {

        private final String topicName;

        public Topic2011(final String topicName) {
            this.topicName = topicName;
        }

        @Override
        public boolean same(final Intent intent) {
            if (intent instanceof Topic2011) {
                return ((Topic2011) intent).topicName.equals(this.topicName);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "[" + topicName + "]";
        }

    }

}
