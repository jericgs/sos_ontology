package br.com.uern.les.erick.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author jerick.gs
 */
public class ConexaoBD {

    public Connection getConnection() throws ClassNotFoundException {        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost/sos_dashboard", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
