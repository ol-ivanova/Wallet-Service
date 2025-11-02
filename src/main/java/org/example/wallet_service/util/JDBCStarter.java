package org.example.wallet_service.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class JDBCStarter {
    private static final String CREATE_SQL = """
            CREATE SCHEMA IF NOT EXISTS wallet_schema;
            CREATE TABLE IF NOT EXISTS wallet_schema.player (
                                       id SERIAL PRIMARY KEY,
                                       name VARCHAR(50) NOT NULL,
                                       username VARCHAR(50) NOT NULL UNIQUE,
                                       password VARCHAR(30) NOT NULL/** ? */
            
            );
            
            CREATE TABLE IF NOT EXISTS wallet_schema.player_audit (
                                         id SERIAL PRIMARY KEY,
                                         date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         action VARCHAR(50) NOT NULL, /** ? */
                                         player_id INT REFERENCES wallet_schema.player(id)
            
            );
            
            CREATE TABLE IF NOT EXISTS wallet_schema.player_account (
                                            account_number BIGINT DEFAULT FLOOR(random()*(10000000000 - 1000000000)) + 10000000000 PRIMARY KEY, /** ? */
                                            balance NUMERIC(18,2),
                                            player_id INT NOT NULL REFERENCES wallet_schema.player(id)
            );
           
            
            CREATE TABLE IF NOT EXISTS wallet_schema.transaction (
                                               id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                                               created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                               type VARCHAR(10) NOT NULL,
                                               sum NUMERIC(18,2) NOT NULL,
                                               player_account_from BIGINT NOT NULL REFERENCES wallet_schema.player_account(account_number),
                                               player_account_to BIGINT NOT NULL REFERENCES wallet_schema.player_account(account_number)
            );
            
            
            """;

    private JDBCStarter() {
    }

    public static void prepareDateBase(){
        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
