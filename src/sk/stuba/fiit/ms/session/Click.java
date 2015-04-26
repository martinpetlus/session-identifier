package sk.stuba.fiit.ms.session;

public final class Click {

    private final int num;

    private final int rank;

    private final double spentTime;

    public Click(final int num, final int rank, final Time startTime, final Time endTime) {
        this(num, rank, endTime.getDifference(startTime));
    }

    public Click(final int num, final int rank, double spentTime) {
        this.num = num;
        this.rank = rank;
        this.spentTime = spentTime;
    }

    public int getNum() {
        return num;
    }

    public int getRank() {
        return rank;
    }

    public double getSpentTime() {
        return spentTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getClass().getSimpleName()).append('[');

        sb.append("num=").append(num);

        sb.append(" rank=").append(rank);

        sb.append(" spentTime=").append(spentTime);

        return sb.append(']').toString();
    }

}
