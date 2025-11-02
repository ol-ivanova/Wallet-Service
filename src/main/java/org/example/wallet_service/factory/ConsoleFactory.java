package org.example.wallet_service.factory;

import java.util.Scanner;

public class ConsoleFactory {
    private static Scanner scanner;

    private ConsoleFactory(){}

    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}
