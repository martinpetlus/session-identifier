package sk.stuba.fiit.ms.session;

public final class Click {
	
	private final int num;

	private final int rank;
	
	private final Time startTime;
	
	private final Time endTime;

	public Click(final int num, final int rank, final Time startTime, final Time endTime) {
		this.num = num;
		this.rank = rank;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getNum() {
		return num;
	}

	public int getRank() {
		return rank;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}
	
	public double getSpentTime() {
		return startTime.getDifference(endTime);
	}
	
}
