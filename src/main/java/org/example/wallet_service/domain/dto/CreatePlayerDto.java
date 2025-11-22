package org.example.wallet_service.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

/**
 * dto объект для регистрации
 */
public class CreatePlayerDto {
    private String name;
    private String username;
    private String password;
}
