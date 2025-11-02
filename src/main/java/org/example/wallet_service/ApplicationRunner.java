package org.example.wallet_service;

import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.util.JDBCStarter;

import java.util.Scanner;

public class ApplicationRunner {
    public static void main(String[] args) {
        JDBCStarter.initDateBase();

        ConsoleService consoleService = new ConsoleService();
        consoleService.execute();

    }
}
