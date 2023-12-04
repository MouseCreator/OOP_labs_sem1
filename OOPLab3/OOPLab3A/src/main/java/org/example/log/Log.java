package org.example.log;

public class Log {
    private final static ULogger uLogger = new ConsoleLogger();

    static {
        uLogger.onStart();
    }
    public static void write(Object message) {
        uLogger.write(message);
    }
}
