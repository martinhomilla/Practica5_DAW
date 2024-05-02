<%@ page import="com.Carrito" %>

<%
    // Get the carrito bean from the session
    Carrito carrito = (Carrito) session.getAttribute("carrito");

    // Get the product title from the request parameters
    String titulo = request.getParameter("titulo");

    // Remove the product from the carrito
    if (carrito != null && titulo != null) {
        carrito.eliminarProducto(titulo);
    }

    // Redirect back to the carrito.jsp page
    response.sendRedirect("carrito.jsp");
%>