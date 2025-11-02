package org.example.wallet_service;

import org.example.wallet_service.state.ConsoleState;
import org.example.wallet_service.state.MainState;

public class ConsoleService {
   /* состояние консольного приложения
     */
    private ConsoleState currentState;

    public ConsoleService(){
        currentState = new MainState();
    }

    /* запуск состояний консольного приложения
     */
    public void execute() {
        while (true) {
            try {
                currentState.process();
                if (currentState.nextState() != null) {
                    currentState = currentState.nextState();
                }
            } catch (Exception e) {
                System.err.println("Problem with input: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
