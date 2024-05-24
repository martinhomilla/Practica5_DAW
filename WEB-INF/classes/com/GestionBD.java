package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


import org.omg.CORBA.Request;

public class GestionBD extends HttpServlet {
    String host = "localhost", port = "5432", database = "minitienda", userBD = "postgres", passwordBD="1234";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(true);

		String accion = request.getParameter("accion");

        Connection conexion = null;
		try{
			conexion = getConnection(host, port, database, userBD, passwordBD);
		}
		catch (SQLException e) {
			request.setAttribute("error_message", "Error al conectar con la base de datos");
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			rd.forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute("error_message", "Error al conectar con la base de datos");
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			rd.forward(request, response);
		}
		switch (accion) {

			case "iniciarSesion":
				try {
					iniciarSesion(request, response, conexion);
				} catch (Exception e) {
					e.printStackTrace();
				}	
				break;

			case "registrarUsuario":
				try {
					registrarUsuario(request, response, conexion);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;	
			case "registrarPedido":
				try {
					registrarPedido(request, response, conexion);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;	
			default:
				break;
		}
       
    }

    private Connection getConnection(String host, String port, String database, String user, String password) throws SQLException, Exception {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
        } catch (SQLException e) {
			throw new SQLException("Error al conectar con la base de datos: " + e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error al conectar con la base de datos: " + e.getMessage());
		} 		
        return connection;
    }


	private void registrarUsuario(HttpServletRequest request, HttpServletResponse response, Connection conexion) throws ServletException, IOException{

		String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        String tipoTarjeta = request.getParameter("tipoTarjeta");
        String numeroTarjeta = request.getParameter("numeroTarjeta");

        String insert = " ";
        try {
			if(!validarContrasenha(password)){
				request.setAttribute("error_message", "Error al registrar usuario. La contraseña debe tener al menos 8 caracteres, una letra mayúscula, un número y un carácter especial");
				RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
				rd.forward(request, response);
				return;
			}
			if(usuario == null || usuario.isEmpty() || password == null || password.isEmpty()){
				request.setAttribute("error_message", "Error al registrar usuario. Debe especificar usuario y password");
				RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
				rd.forward(request, response);				
				return;
			}
			if(tipoTarjeta == null || tipoTarjeta.isEmpty() || numeroTarjeta == null || numeroTarjeta.isEmpty()){
				request.setAttribute("error_message", "Error al registrar usuario. Debe especificar tipo de tarjeta y número de tarjeta");
				RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
				rd.forward(request, response);				
				return;			
			}
			if(existeUsuario(conexion, usuario)){
				request.setAttribute("error_message", "Error al registrar usuario. El usuario ya existe");
				RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
				rd.forward(request, response);
				return;
			}			
            insert = "INSERT INTO usuarios (nombre, password, tipo_tarjeta, numero_tarjeta) VALUES (?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = conexion.prepareStatement(insert);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, tipoTarjeta);
            preparedStatement.setString(4, numeroTarjeta);
            preparedStatement.executeUpdate();
            
            

            preparedStatement.close();
            conexion.close();

           

			request.setAttribute("usuario", usuario);
			request.setAttribute("tipoTarjeta", tipoTarjeta);
			request.setAttribute("numeroTarjeta", numeroTarjeta);
			request.setAttribute("total", request.getParameter("total"));

			// Redirigir al usuario a la página de confirmación
			RequestDispatcher rd = request.getRequestDispatcher("finalizarCompra.jsp");
			rd.forward(request, response);

        } catch (SQLException e) {
            // Captura de excepciones y reenvío a página de error
            request.setAttribute("error_message", "Error al registrar usuario: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
            rd.forward(request, response);
        }
		catch (Exception e) {
			// Captura de excepciones y reenvío a página de error
			request.setAttribute("error_message", "Error al registrar usuario: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			rd.forward(request, response);
		}

	}

	private void iniciarSesion(HttpServletRequest request, HttpServletResponse response, Connection conexion) throws ServletException, IOException{
		String usuario = request.getParameter("usuario");
		String password = request.getParameter("password");

		String consulta = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
		try {
			PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
			preparedStatement.setString(1, usuario);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				String consulta2 = "SELECT numero_tarjeta FROM usuarios WHERE nombre = ?";
				PreparedStatement preparedStatement2 = conexion.prepareStatement(consulta2);
				preparedStatement2.setString(1, usuario);
				ResultSet resultSet2 = preparedStatement2.executeQuery();
				resultSet2.next();
				String numeroTarjeta = resultSet2.getString(1);
				request.setAttribute("numeroTarjeta", numeroTarjeta);
				request.setAttribute("usuario", usuario);				
				request.setAttribute("total", request.getParameter("total"));
				RequestDispatcher rd = request.getRequestDispatcher("finalizarCompra.jsp");
				rd.forward(request, response);
			}
			else{
				request.setAttribute("error_message", "Error al iniciar sesión. Usuario o password incorrectos");
				RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			// Captura de excepciones y reenvío a página de error
			request.setAttribute("error_message", "Error al iniciar sesión: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		}
		catch (Exception e) {
			// Captura de excepciones y reenvío a página de error
			request.setAttribute("error_message", "Error al iniciar sesión: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}


	private void registrarPedido(HttpServletRequest request, HttpServletResponse response, Connection conexion) throws ServletException, IOException{

		String insert = " ";
		insert = "INSERT INTO pedidos (usuario, importe, fecha) VALUES (?, ?, ?)";
        try {    
			String usuario = request.getParameter("usuario");
			Float total = Float.parseFloat(request.getParameter("total"));
			PreparedStatement preparedStatement = conexion.prepareStatement(insert);
			preparedStatement.setString(1, usuario);
			preparedStatement.setFloat(2, total);
			preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
			preparedStatement.executeUpdate();

			preparedStatement.close();
			conexion.close();

			request.setAttribute("usuario", usuario);

			// Redirigir al usuario a la página de informacion de compra
			RequestDispatcher rd = request.getRequestDispatcher("confirmacionCompra.jsp");
			rd.forward(request, response);

		} catch (SQLException e) {
			// Captura de excepciones y reenvío a página de error
			request.setAttribute("error_message", "Error al registrar usuario: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			rd.forward(request, response);
		}
		catch (Exception e) {
			// Captura de excepciones y reenvío a página de error
			request.setAttribute("error_message", "Error al registrar usuario: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			rd.forward(request, response);
		}


	}

	public static boolean validarContrasenha(String contrasenha) {
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

	private boolean existeUsuario(Connection conexion, String usuario) throws SQLException {
		String consulta = "SELECT COUNT(*) FROM usuarios WHERE nombre = ?";
		PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
		preparedStatement.setString(1, usuario);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		int count = resultSet.getInt(1);
		return count > 0;
	}
}
