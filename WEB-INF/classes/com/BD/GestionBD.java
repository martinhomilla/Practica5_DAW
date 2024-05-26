package com.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GestionBD extends HttpServlet {
	private String host, port, database, userBD, passwordBD;
	private Conexion conexion;
	private UsuarioDAO usuarioDAO;
	private PedidoDAO pedidoDAO;

	public void init() throws ServletException {
		// Se cargan las propiedades de la base de datos
		Properties dbProps = new Properties();
		InputStream input = null;
		try {
			input = getServletContext().getResourceAsStream("/WEB-INF/dbconfig.properties");
			dbProps.load(input);
			host = dbProps.getProperty("db.host");
			port = dbProps.getProperty("db.port");
			database = dbProps.getProperty("db.database");
			userBD = dbProps.getProperty("db.user");
			passwordBD = dbProps.getProperty("db.password");

			// Se crea la conexión con la base de datos
			conexion = new Conexion(host, port, database, userBD, passwordBD);

			// Se crean los objetos DAO
			try {
				usuarioDAO = new UsuarioDAO(conexion.getConnection());
				pedidoDAO = new PedidoDAO(conexion.getConnection());
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			throw new ServletException("Error al obtener las propiedades de la base de datos", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Método para cerrar la conexión con la base de datos al destruir el servlet
	public void destroy() {
		// Close the database connection
		try {
			if (conexion != null) {
				conexion.closeConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String accion = request.getParameter("accion");

		switch (accion) {

			case "iniciarSesion":
				try {
					iniciarSesion(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case "registrarUsuario":
				try {
					registrarUsuario(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "registrarPedido":
				try {
					registrarPedido(request, response);

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
		}

	}

	private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String usuario = request.getParameter("usuario");
		String password = request.getParameter("password");
		String tipoTarjeta = request.getParameter("tipoTarjeta");
		String numeroTarjeta = request.getParameter("numeroTarjeta");

		try {

			// Se intenta registrar el usuario en la base de datos
			usuarioDAO.registroUsuario(usuario, password, numeroTarjeta, tipoTarjeta);

			// Se guarda el usuario en la sesión y sus datos para presentarlos en la página
			// de confirmación
			request.setAttribute("usuario", usuario);
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
		} catch (Exception e) {
			// Captura de excepciones y reenvío a página de error
			request.setAttribute("error_message", "Error al registrar usuario: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			rd.forward(request, response);
		}

	}

	private void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String usuario = request.getParameter("usuario");
		String password = request.getParameter("password");

		try {
			// Se intenta iniciar sesión y se recupera el número de tarjeta
			if (usuarioDAO.inicioSesion(usuario, password)) {
				request.setAttribute("numeroTarjeta", usuarioDAO.getNumeroTarjeta(usuario));
				request.setAttribute("usuario", usuario);
				request.setAttribute("total", request.getParameter("total"));
				RequestDispatcher rd = request.getRequestDispatcher("finalizarCompra.jsp");
				rd.forward(request, response);
			} else {
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
		} catch (Exception e) {
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

	private void registrarPedido(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String usuario = request.getParameter("usuario");
			Float total = Float.parseFloat(request.getParameter("total"));
			pedidoDAO.registrarPedido(usuario, total);
			// Se guardan los datos del usuario y el total en la sesión para presentarlos en
			// la página de confirmación
			request.setAttribute("usuario", usuario);
			request.setAttribute("total", total);

			// Redirigir al usuario a la página de informacion de compra
			RequestDispatcher rd = request.getRequestDispatcher("confirmacionCompra.jsp");
			rd.forward(request, response);

		} catch (SQLException e) {
			// Captura de excepciones y reenvío a página de error
			request.setAttribute("error_message", "Error al registrar usuario: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			// Captura de excepciones y reenvío a página de error
			request.setAttribute("error_message", "Error al registrar usuario: " + e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("pago.jsp");
			rd.forward(request, response);
		}

	}

}
