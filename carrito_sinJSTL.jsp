
<%@ page import="com.Producto " %>
<%@ page import="com.Carrito " %>

<%@ page isELIgnored="false" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html>   

<head>
    <meta charset="utf-8">
    <title>Carrito de compra</title>
    <link rel="stylesheet"  href="estilos.css">
</head>
<body>

    <h1>Carrito de compra</h1>
    <h2><a href="index.html">Volver a la tienda</a></h2>
   
    <table>
        <tr>
            <th>Título</th>
            <th>Cantidad</th>
            <th>Importe</th>
        </tr>
        <jsp:useBean id="carrito" class="com.Carrito" scope="session" />
        

        <%
           // Producto producto = (Producto) request.getAttribute("objProducto");
          // Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito != null ) {
            for(Producto producto : carrito.getProductos()){
             
        %>
        <tr>
            <td><%= producto.getTitulo()%></td>
            <td><%= producto.getCantidad()%></td>
            <td><%= producto.getPrecio()+"$"%></td>
            <td class="botones" style="border:none; display:flex; flex-direction:row; gap:3px;">
            
                <form action="eliminarProductoCarrito.jsp" method="post">
                    <input type="hidden" name="titulo" value="<%= producto.getTitulo()%>">
                    <input type="submit" value="Eliminar">                    
                </form>
                <form action="aumentarProducto.jsp" method="post">

                    <input type="hidden" name="titulo" value="<%= producto.getTitulo()%>">
                    <input type="hidden" name="cantidad" value="<%= producto.getCantidad()%>">
                    <input type="submit" value="+">                    
                </form>
                 <form action="disminuirProducto.jsp" method="post">
                    <input type="hidden" name="cantidad" value="<%= producto.getCantidad()%>">

                    <input type="hidden" name="titulo" value="<%= producto.getTitulo()%>">
                    <input type="submit" value="-">                    
                </form>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="3">No se encontró ningún producto en el carrito</td>
        </tr>
        <%
            }
        %>
        
    </table>

    <table>
        <tr>
            <th>Importe Total</th>
            <td><%=carrito.getTotal()+"$"%></td>
        </tr>
    </table>
     <form action="eliminarCarrito.jsp" method="post">
        <input type="submit" value="Vaciar Carrito">
    </form>

</body>
</html>