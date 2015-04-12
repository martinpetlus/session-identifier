package sk.stuba.fiit.ms.input;

import sk.stuba.fiit.ms.session.Time;

final class TimeInSeconds implements Time {

    public static final TimeInSeconds ZERO = new TimeInSeconds(0.0);

    private final double value;

    public TimeInSeconds(final double value) {
        this.value = value;
    }

    @Override
    public double getDifference(final Time time) {
        if (this == ZERO || time == ZERO) {
            return 0.0;
        } else {
            return Math.abs(this.value - ((TimeInSeconds) time).value);
        }
    }

    @Override
    public String toString() {
        return "TimeInSeconds[" + value + "]";
    }

}
