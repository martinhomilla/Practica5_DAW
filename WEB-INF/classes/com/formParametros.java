package com;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.Producto;
import com.Carrito;

import org.omg.CORBA.Request;



public class formParametros extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(true);
        

        // Obtener los parámetros del producto y la cantidad
        String nombre = request.getParameter("nombre");
        int cantidad = 0;
        try {
            cantidad = Integer.parseInt(request.getParameter("cantidad"));
        } catch (NumberFormatException e) {
            cantidad = 0;          
        }
        float precio = Float.parseFloat(nombre.split("\\|")[3].replace("$", "").trim());         
       

        // Validar si los parámetros no son nulos y no están vacíos
       if (nombre != null && !nombre.isEmpty() && cantidad > 0 ){// cantidad != null && !cantidad.isEmpty()) {
            // Agregar el producto al carrito de compras
            Producto producto = new Producto();
            producto.setTitulo(nombre);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);

            agregarAlCarrito(request, producto);
            

            RequestDispatcher rd = request.getRequestDispatcher("carrito.jsp");                        
            // Redirigir al usuario de vuelta a la página del carrito de compras
            rd.forward(request, response);
        }        
        else {
            // Enviar una respuesta de error si faltan parámetros
            
            RequestDispatcher rd = request.getRequestDispatcher("index.html");
            rd.include(request, response);
            out.println("<p class = error>Error: Cantidad de productos incorrecta.</p>");
        }
    }

    private void agregarAlCarrito(HttpServletRequest request, Producto producto) {

        // Obtener la sesión actual
        HttpSession session = request.getSession(true);

        // Obtener el carrito de compras de la sesión
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        // Si el carrito no existe, crear uno nuevo
        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }

        // Agregar el producto al carrito
        carrito.agregarProducto(producto);
    }

    
}