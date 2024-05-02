<%@ page import="com.Carrito" %>

<%
    // Get the carrito bean from the session
    Carrito carrito = (Carrito) session.getAttribute("carrito");

    // Clear the carrito
    if (carrito != null) {
        carrito.vaciar();
    }
    response.sendRedirect("index.html");
%>