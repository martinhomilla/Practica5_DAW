package com.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UsuarioDAO {
    
    private Connection conexion;

    // Constructor
    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para iniciar sesión en la base de datos
    public boolean inicioSesion (String usuario, String password) throws SQLException{
        String query = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
        PreparedStatement preparedStatement = conexion.prepareStatement(query);
        preparedStatement.setString(1, usuario);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return true;

        } else {
            return false;
        }
    }
    // Método para registrar un usuario en la base de datos
    public boolean registroUsuario(String usuario, String password, String numeroTarjeta, String tipoTarjeta) throws SQLException {
        if (!validarContrasenha(password)) {
            throw new IllegalArgumentException("La contraseña no cumple con los requisitos.");
        }

        if (usuario == null || usuario.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Usuario y password son obligatorios.");
        }

        if (tipoTarjeta == null || tipoTarjeta.isEmpty() || numeroTarjeta == null || numeroTarjeta.isEmpty()) {
            throw new IllegalArgumentException("Tipo y número de tarjeta son obligatorios.");
        }

        if (existeUsuario(usuario)) {
            throw new IllegalArgumentException("El usuario ya existe.");
        }

        String insert = "INSERT INTO usuarios (nombre, password, tipo_tarjeta, numero_tarjeta) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = conexion.prepareStatement(insert);
        preparedStatement.setString(1, usuario);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, tipoTarjeta);
        preparedStatement.setString(4, numeroTarjeta);
        int filasAfectadas = preparedStatement.executeUpdate();
        preparedStatement.close();
        return filasAfectadas == 1;
    }

    // Método para verificar si un usuario ya existe en la base de datos
    public boolean existeUsuario(String usuario) throws SQLException{
        String consulta = "SELECT * FROM usuarios WHERE nombre = ?";
        PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
        preparedStatement.setString(1, usuario);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            resultSet.close();
            return true;
        } else {
            resultSet.close();
            return false;
        }
    }

    // Método para obtener el número de tarjeta de un usuario
    public String getNumeroTarjeta(String usuario) throws SQLException{
        String consulta = "SELECT numero_tarjeta FROM usuarios WHERE nombre = ?";
        PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
        preparedStatement.setString(1, usuario);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String numeroTarjeta = resultSet.getString(1);
        preparedStatement.close();
        resultSet.close();
        return numeroTarjeta;
    }

    
	private static boolean validarContrasenha(String contrasenha) {
        // Verificar si la contraseña tiene al menos 8 caracteres
        if (contrasenha.length() < 8) {
            return false;
        }

        // Verificar si la contraseña contiene al menos un carácter especial
        if (!contrasenha.matches(".*[!@#$%^&*()-+=].*")) {
            return false;
        }

        // Verificar si la contraseña contiene al menos una letra mayúscula
        if (!contrasenha.matches(".*[A-Z].*")) {
            return false;
        }

        // Verificar si la contraseña contiene al menos un número
        if (!contrasenha.matches(".*\\d.*")) {
            return false;
        }

        // La contraseña cumple con todos los criterios de validación
        return true;
    }

}
