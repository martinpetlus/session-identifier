package sk.stuba.fiit.ms.utils;

public final class Logger {

    private Logger() {}

    public static void warn(final Object o) {
        System.err.println(o);
    }

    public static void log(final Object o) {
        System.out.println(o);
    }

}
