package org.example.wallet_service.service;

import org.example.wallet_service.domain.dto.CreatePlayerAccountDto;
import org.example.wallet_service.domain.entity.Player;
import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.domain.mapper.PlayerAccountMapper;
import org.example.wallet_service.exception.PlayerAccountException;
import org.example.wallet_service.exception.TransferException;
import org.example.wallet_service.factory.PlayerAccountRepositoryFactory;
import org.example.wallet_service.repository.PlayerAccountRepository;

import java.math.BigDecimal;

public class PlayerAccountService {
    private PlayerAccountRepository playerAccountRepository;

    private final PlayerAccountMapper playerAccountMapper;

    public PlayerAccountService(){
        playerAccountRepository = PlayerAccountRepositoryFactory.getPlayerAccountRepository();
        playerAccountMapper = PlayerAccountMapper.getInstance();
    }

    /**
     * Метод, создающий объект PlayerAccount
     * @param createPlayerAccountDto - dto объект
     * @return - объект PlayerAccount (счет пользователя) со сгенерированным номером
     */
    public PlayerAccount createAccountPlayer(CreatePlayerAccountDto createPlayerAccountDto){
        PlayerAccount playerAccount = playerAccountMapper.map(createPlayerAccountDto);
        return playerAccountRepository.save(playerAccount)
                .orElseThrow(() -> new PlayerAccountException("Аккаунт не создан"));
    }

    /**
     * Метод для получения счета по номеру
     * @param accountNumber - номер счета
     * @return - объект PlayerAccount (счет пользователя)
     */
    public PlayerAccount getAccountByNumber(Long accountNumber){
        return playerAccountRepository.findAccountByNumber(accountNumber)
                .orElseThrow(() -> new PlayerAccountException("Аккаунт не найден"));
    }

    /**
     * Метод для получения счета по id
     * @param playerId - id пользователя
     * @return - объект PlayerAccount (счет пользователя)
     */

    public PlayerAccount getAccountByPlayerId(int playerId){
        return playerAccountRepository.findAccountByPlayerId(playerId)
                .orElseThrow(() -> new PlayerAccountException("Аккаунт не найден"));
    }

    public void doCreditOperation(BigDecimal sum, long accountNumberTo, PlayerAccount currentPlayerAccount){
        PlayerAccount playerAccount = getAccountByNumber(accountNumberTo);
        if (sum.compareTo(currentPlayerAccount.getBalance()) >= 0) {
            throw new TransferException("Сумма не может превышать баланс");
        }

        playerAccount.setBalance(playerAccount.getBalance().add(sum));

        currentPlayerAccount.setBalance(currentPlayerAccount.getBalance().subtract(sum));

        playerAccountRepository.updateBalanceByAccountNumber(playerAccount.getBalance() , accountNumberTo);
        playerAccountRepository.updateBalanceByAccountNumber(currentPlayerAccount.getBalance() , currentPlayerAccount.getAccountNumber());
    }

    public void doDebitOperation(BigDecimal sum, long accountNumberFrom, PlayerAccount currentPlayerAccount){
        PlayerAccount playerAccount = getAccountByNumber(accountNumberFrom);
        if (sum.compareTo(playerAccount.getBalance()) >= 0) {
            throw new TransferException("Сумма не может превышать баланс");
        }

        playerAccount.setBalance(playerAccount.getBalance().subtract(sum));

        currentPlayerAccount.setBalance(currentPlayerAccount.getBalance().add(sum));

        playerAccountRepository.updateBalanceByAccountNumber(playerAccount.getBalance(), accountNumberFrom);
        playerAccountRepository.updateBalanceByAccountNumber(currentPlayerAccount.getBalance(), currentPlayerAccount.getAccountNumber());
    }
}
