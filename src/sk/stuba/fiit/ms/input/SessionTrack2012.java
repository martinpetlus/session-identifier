package sk.stuba.fiit.ms.input;

import org.w3c.dom.Node;

public class SessionTrack2012 extends SessionTrackSkeleton {
	
	@Override
	public Topic parseTopic(final Node node) {
		return new Topic2012(Integer.parseInt(Util.getAttrValue(node, "num")));
	}
	
	private static final class Topic2012 implements Topic {
		
		private final int id;
		
		public Topic2012(final int id) {
			this.id = id;
		}

		@Override
		public boolean same(final Topic t) {
			if (t instanceof Topic2012) {
				return ((Topic2012) t).id == this.id;
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return "Topic2012[" + id + "]";
		}
		
	}

}
