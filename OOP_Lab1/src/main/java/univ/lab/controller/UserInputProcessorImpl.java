package univ.lab.controller;

import java.util.Scanner;

public class UserInputProcessorImpl implements UserInputProcessor {
    public String requestString(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.print(prompt);
            input = scanner.nextLine();

        } while (input == null || input.isEmpty());

        return input;
    }

    public Long requestLong(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input;
        long res;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
            if ((input == null || input.isEmpty()))
                continue;
            try {
                res = Long.parseLong(input);
            } catch (Exception e) {
                continue;
            }
            return res;
        } while (true);

    }

    public Integer requestInteger(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input;
        int res;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
            if ((input == null || input.isEmpty()))
                continue;
            try {
                res = Integer.parseInt(input);
            } catch (Exception e) {
                continue;
            }
            return res;
        } while (true);
    }
}
