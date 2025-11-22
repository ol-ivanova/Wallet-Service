package org.example.wallet_service;

import org.example.wallet_service.util.JDBCStarter;

public class ApplicationRunner {
    public static void main(String[] args) {
        JDBCStarter.initDateBase();

        ConsoleService consoleService = new ConsoleService();
        consoleService.execute();

    }
}
