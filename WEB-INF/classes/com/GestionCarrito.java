package com;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.omg.CORBA.Request;

public class GestionCarrito extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("carrito.jsp");
        rd.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(true);
        CarritoBean carrito = (CarritoBean) session.getAttribute("carrito");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "añadir":
                if (carrito == null) {

                    carrito = new CarritoBean();
                    session.setAttribute("carrito", carrito);
                }
                if (agregarAlCarrito(request, response, carrito)) {
                    response.sendRedirect("carrito.jsp");

                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("index.html");
                    rd.include(request, response);
                    out.println("<p style = \"color:red;\">Error: Cantidad de productos incorrecta.</p>");
                }

                break;
            case "vaciar":
                // Obtener el carrito de compras de la sesión
                if (carrito != null) {
                    carrito.vaciar();
                }
                response.sendRedirect("carrito.jsp");

                break;
            case "eliminar":
                if (carrito != null) {
                    carrito.eliminarProducto(request.getParameter("titulo"));
                }
                response.sendRedirect("carrito.jsp");

                break;

            case "sumar":
                if (carrito != null) {
                    carrito.actualizarProducto(request.getParameter("titulo"),
                            Integer.parseInt(request.getParameter("cantidad")) + 1);
                }
                response.sendRedirect("carrito.jsp");

                break;
            case "restar":
                if (carrito != null) {
                    carrito.actualizarProducto(request.getParameter("titulo"),
                            Integer.parseInt(request.getParameter("cantidad")) - 1);
                }
                response.sendRedirect("carrito.jsp");

                break;

            case "acabar":
                carrito.vaciar();
                request.removeAttribute("carrito");
                response.sendRedirect("index.html");
                break;

            default:
                break;
        }

    }

    private boolean agregarAlCarrito(HttpServletRequest request, HttpServletResponse response, CarritoBean carrito)
            throws ServletException, IOException {

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
        if (nombre != null && !nombre.isEmpty() && cantidad > 0) {// cantidad != null && !cantidad.isEmpty()) {
            // Agregar el producto al carrito de compras
            ProductoBean producto = new ProductoBean();
            producto.setTitulo(nombre);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);
            producto.setTotal(precio * cantidad);
            carrito.agregarProducto(producto);
            return true;

        } else {
            return false;
        }
    }

}
