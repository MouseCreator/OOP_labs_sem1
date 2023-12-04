package org.example.log;

public interface ULogger {
    void write(Object obj);
    void write(String str);
    void onStart();
}
