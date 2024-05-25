package com.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private String host;
    private String port;
    private String database;
    private String user;
    private String password;
    private Connection connection;

    public Conexion(String host, String port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
            } catch (SQLException e) {
                throw new SQLException("Error al conectar con la base de datos: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new SQLException("Error al cargar el driver de la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new SQLException("Error al cerrar la conexión con la base de datos: " + e.getMessage());
            }
        }
    }
}
