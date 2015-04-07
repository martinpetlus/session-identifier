package sk.stuba.fiit.ms.input;

import org.w3c.dom.Node;

public class SessionTrack2011 extends SessionTrackSkeleton {
	
	@Override
	public Topic parseTopic(final Node node) {
		return new Topic2011(Util.getChildValue(node, "title"));
	}
	
	private static final class Topic2011 implements Topic {
		
		private final String topicName;
		
		public Topic2011(final String topicName) {
			this.topicName = topicName;
		}

		@Override
		public boolean same(final Topic t) {
			if (t instanceof Topic2011) {
				return ((Topic2011) t).topicName.equals(this.topicName);
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return "Topic2011[" + topicName + "]";
		}
		
	}

}
