package org.example.wallet_service.state;

public interface ConsoleState {
    /** Метод, запускающий логику, состояний и приложение
     */
    void process();

    /** Метод, возвращающий следующее состояние приложения
     */
    ConsoleState nextState();
}
