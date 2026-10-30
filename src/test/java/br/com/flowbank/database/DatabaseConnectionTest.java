package br.com.flowbank.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

class DatabaseConnectionTest {

    @Test
    void deveConectarAoMySQL() throws Exception {

        try (Connection conexao = DatabaseConnection.getConnection()) {

            assertNotNull(
                conexao,
                "A conexão não deveria ser nula."
            );

            assertTrue(
                conexao.isValid(5),
                "A conexão com o MySQL deveria estar válida."
            );
        }
    }
}