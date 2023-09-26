package univ.lab;

import univ.lab.controller.DatabaseController;

public class Main {
    public static void main(String[] args) {
        DatabaseController controller = new DatabaseController();
        controller.start();
    }
}