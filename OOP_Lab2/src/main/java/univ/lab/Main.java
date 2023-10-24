package univ.lab;

import univ.lab.demo.Demonstration;
import univ.lab.demo.SimpleDemonstration;

public class Main {
    public static void main(String[] args) {
        Demonstration demonstration = new SimpleDemonstration();
        demonstration.validateAndParse();
    }
}