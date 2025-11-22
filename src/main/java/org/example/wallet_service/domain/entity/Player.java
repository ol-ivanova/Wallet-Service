package org.example.wallet_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

/**
 * Сущность - пользователь
 */
public class Player {
    /**
     * id, генерируемый при вставке экземпляра класса в БД
     */
    private Integer id;
    /**
     * Имя пользователя
     */
    private String name;
    /**
     * Логин пользователя
     */
    private String username;
    /**
     * Пароль игрока
     */
    private String password;
}
