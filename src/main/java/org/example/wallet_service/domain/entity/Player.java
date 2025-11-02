package org.example.wallet_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Player {
    private Integer id;
    private String name;
    private String username;
    private String password;
}
