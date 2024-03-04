package org.example.log;

public class ConsoleLogger implements ULogger {
    @Override
    public void write(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void write(String str) {
        System.out.println(str);
    }

    @Override
    public void onStart() {

    }
}
