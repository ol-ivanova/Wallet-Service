package org.example.wallet_service.state;

import org.example.wallet_service.factory.ConsoleFactory;
import org.example.wallet_service.util.SelectionUtil;

import java.util.Scanner;

public class MainState implements ConsoleState{
    /** Объект сканнера
     */
    private final Scanner scanner;
    /** следующее состояние приложения
     */
    private ConsoleState nextState;

    public MainState(){
        scanner = ConsoleFactory.getScanner();
    }

    /** метод, запускающий логику начального состояния приложения
     * @throws Exception
     */
    @Override
    public void process() {
        System.out.println("1. Зарегистрироваться\n2. Войти\n");
        System.out.print("Введите нужный вариант: ");

        int menuSelection = SelectionUtil.getValue(() -> Integer.parseInt(scanner.nextLine()));

        switch (menuSelection) {
            case 1 -> nextState = new RegistrationState();
            case 2 -> nextState = new AuthorizationState();
        }
    }

    /** метод, возвращающий слудующее состояние приложения
     * @return - слудующее состояние приложения
     */
    @Override
    public ConsoleState nextState() {
        return nextState;
    }
}
