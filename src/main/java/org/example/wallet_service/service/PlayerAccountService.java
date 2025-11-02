package org.example.wallet_service.service;

import org.example.wallet_service.domain.dto.CreatePlayerAccountDto;
import org.example.wallet_service.domain.entity.PlayerAccount;
import org.example.wallet_service.exception.PlayerAccountException;
import org.example.wallet_service.factory.PlayerAccountRepositoryFactory;
import org.example.wallet_service.repository.PlayerAccountRepository;

import java.math.BigDecimal;

public class PlayerAccountService {
    private PlayerAccountRepository playerAccountRepository;

    public PlayerAccountService(){
        playerAccountRepository = PlayerAccountRepositoryFactory.getPlayerAccountRepository();
    }

    public PlayerAccount createAccountPlayer(CreatePlayerAccountDto createPlayerAccountDto){
        return playerAccountRepository.save(createPlayerAccountDto)
                .orElseThrow(() -> new PlayerAccountException("Аккаунт не создан"));
    }

    public PlayerAccount getAccountByNumber(Long accountNumber){
        return playerAccountRepository.findAccountByNumber(accountNumber)
                .orElseThrow(() -> new PlayerAccountException("Аккаунт не найден"));
    }

    public PlayerAccount getAccountByPlayerId(int playerId){
        return playerAccountRepository.findAccountByPlayerId(playerId)
                .orElseThrow(() -> new PlayerAccountException("Аккаунт не найден"));
    }

    public void updateBalanceByAccountNumber(BigDecimal balance, long accountNumber){
        playerAccountRepository.updateBalanceByAccountNumber(balance, accountNumber);
    }
}
