package com.BD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PedidoDAO {
    Connection conexion;
    public PedidoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void registrarPedido(String usuario, Float total) throws SQLException, IOException{

		String insert = " ";
		insert = "INSERT INTO pedidos (usuario, importe, fecha) VALUES (?, ?, ?)";         
			
        PreparedStatement preparedStatement = conexion.prepareStatement(insert);
        preparedStatement.setString(1, usuario);
        preparedStatement.setFloat(2, total);
        preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
        preparedStatement.executeUpdate();
        preparedStatement.close();     

        


	}
}
