
package br.com.flowbank.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private DatabaseConnection() {
        // Impede a criação de objetos desta classe.
    }

    public static Connection getConnection() throws SQLException {

        String url = System.getenv("FLOWBANK_DB_URL");
        String usuario = System.getenv("FLOWBANK_DB_USER");
        String senha = System.getenv("FLOWBANK_DB_PASSWORD");

        if (url == null || usuario == null || senha == null
                || url.isBlank() || usuario.isBlank() || senha.isBlank()) {

            throw new IllegalStateException(
                "As variáveis de ambiente do banco de dados não foram configuradas."
            );
        }

        return DriverManager.getConnection(url, usuario, senha);
    }
}
