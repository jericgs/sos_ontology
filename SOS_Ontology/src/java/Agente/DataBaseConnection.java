package Agente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {

    public Connection connection;
    private String serverName;
    private String dataBaseName;
    private String userName;
    private String password;
    private boolean isConnected;

    public DataBaseConnection(String serverName, String dataBaseName, String userName, String password) {
        this.serverName = serverName;
        this.dataBaseName = dataBaseName;
        this.userName = userName;
        this.password = password;
    }

    public DataBaseConnection() {
        setDataBase();
        this.isConnected = false;
    }

    //TODO atualizar os dados da sua base
	public void setDataBase(){
		this.serverName = "localhost";
		this.dataBaseName = "sos_dashboard";
		this.userName = "root";
		this.password = "";
	}
    public void connectDataBase() {

        String status = null;

        try {
            System.out.println("Connecting to database...");
            // Carregando o JDBC Driver padrão
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);

            // Configurando a nossa conexão com um banco de dados//
            String url = "jdbc:mysql://" + serverName + "/" + dataBaseName;

            connection = DriverManager.getConnection(url, userName, password);

            //Testa sua conexão//  
            if (connection != null) {

                status = ("STATUS--->Successfully connected...");
                System.out.println(status);
                this.isConnected = true;
            } else {
                status = ("STATUS--->Unable to connect...");
                System.out.println(status);
            }
        } catch (ClassNotFoundException e) {  //Driver não encontrado
            System.out.println("Specified driver not found...");
        } catch (SQLException e) {
            //Não conseguindo se conectar ao banco
            System.out.println("Could not connect to Database...");

        }
    }

    public boolean closeConnection() {

        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void refreshConnection() {
        closeConnection();
        connectDataBase();
    }

    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("STATUS--->Error: SQL executeQuery");
            e.printStackTrace();
        }
        return resultSet;
    }

    public void executeUpdateQuery(String updateQuery) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            System.out.println("STATUS--->Error: SQL executeUpdateQuery");
        }
    }

    public void executeUpdate(String update) {
        try {
            Statement statment = connection.createStatement();
            statment.executeUpdate(update);
        } catch (SQLException e) {
            System.out.println("STATUS--->Error: SQL executeUpdate");
        }
    }

    public boolean isConnected() {
        return this.isConnected;
    }
}
