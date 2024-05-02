<%@ page import="com.Carrito" %>

<%
    // Get the carrito bean from the session
    Carrito carrito = (Carrito) session.getAttribute("carrito");

    String titulo = request.getParameter("titulo");
    int cantidad = Integer.parseInt(request.getParameter("cantidad"));
    

    // Remove the product from the carrito
    if (carrito != null && titulo != null) {
        carrito.actualizarProducto(titulo, cantidad + 1);
    }
    // Redirect back to the carrito.jsp page
    response.sendRedirect("carrito.jsp");
%>