package org.example.wallet_service.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizationDto {
    private String username;
    private String password;
}
