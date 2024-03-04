package org.example.log;

public class Log {
    private final static ULogger uLogger = new ConsoleLogger();

    static {
        uLogger.onStart();
    }
    public static void write(Object... messages) {
        StringBuilder builder = new StringBuilder();
        for (Object message : messages) {
            builder.append(message);
        }
        uLogger.write(builder);
    }
}
