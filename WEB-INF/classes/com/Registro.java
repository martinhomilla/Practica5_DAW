package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.omg.CORBA.Request;



public class Registro extends HttpServlet{

        String host = "localhost", port = "5432", database = "minitienda", userBD = "postgres", passwordBD="1234";

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            HttpSession session = request.getSession(true);

            String usuario = request.getParameter("usuario");
            String password = request.getParameter("password");
			String tipoTarjeta = request.getParameter("tipoTarjeta");
			String numeroTarjeta = request.getParameter("numeroTarjeta");
            

            Connection conexion = null;
			String insert = " ";
			try{
				conexion = getConnection(host, port, database, userBD, passwordBD);
				insert = "INSERT INTO usuarios (nombre, password, tipo_tarjeta, numero_tarjeta) VALUES (?, ?, ?, ?)";
			}catch (Exception e) {
					out.println("Error al obtener conexion: " + e.getMessage());
				}	
			if(conexion != null){
				try{
					PreparedStatement preparedStatement = conexion.prepareStatement(insert);
					preparedStatement.setString(1, usuario);
					preparedStatement.setString(2, password);
					preparedStatement.setString(3, tipoTarjeta);
					preparedStatement.setString(4, numeroTarjeta);
					preparedStatement.executeUpdate();
					
					RequestDispatcher rd = request.getRequestDispatcher("index.html");
					rd.include(request, response);

					out.println("<h1>Usuario registrado correctamente</h1>");
					preparedStatement.close();
					conexion.close();

					//vaciamos el carrito
					request.getSession().removeAttribute("carrito");
					
				}
				catch (Exception e) {
					out.println("Error al registrar usuario: " + e.getMessage());
				}
			}        
        
        
        
        }

        private Connection getConnection(String host, String port, String database, String user, String password) throws Exception{
            Connection connection = null;
            try {
               	Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return connection;


        }
}
