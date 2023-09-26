package univ.lab.controller;

public interface UserInputProcessor {
    String requestString(String prompt);
    Long requestLong(String prompt);
    Integer requestInteger(String prompt);
}
