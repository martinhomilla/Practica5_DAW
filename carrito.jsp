
<%@ page import="com.Producto " %>
<%@ page import="com.Carrito " %>

<%@ page isELIgnored="false" %>
<%@ page session="true"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html> 
<html>   

<head>
    <meta charset="utf-8">
    <title>Carrito de compra</title>
    <link rel="stylesheet"  href="estilos.css">
</head>
<body>

    <h1>Carrito de compra</h1>
   
    <table>
        <tr>
            <th>Título</th>
            <th>Cantidad</th>
            <th>Importe</th>
        </tr>
        
       


        <c:choose>
            <c:when test="${empty carrito.productos}">
                <tr>
                    <td colspan="3">No se encontró ningún producto en el carrito</td>
                </tr>
            </c:when>
            <c:otherwise>               
                <c:forEach var="producto" items="${carrito.productos}">            
                    <tr>
                            <td>${producto.titulo}</td>
                            <td>${producto.cantidad}</td>
                            <td>${producto.precio}$</td>
                            <td class="botones" style="border:none; display:flex; flex-direction:row; gap:3px;">
                            
                                <form action="eliminarProductoCarrito.jsp" method="post">
                                    <input type="hidden" name="titulo" value="${producto.titulo}">
                                    <input type="submit" value="Eliminar">                    
                                </form>
                                <form action="aumentarProducto.jsp" method="post">

                                    <input type="hidden" name="titulo" value="${producto.titulo}">
                                    <input type="hidden" name="cantidad" value="${producto.cantidad}">
                                    <input type="submit" value="+">                    
                                </form>
                                <form action="disminuirProducto.jsp" method="post">
                                    <input type="hidden" name="cantidad" value="${producto.cantidad}">

                                    <input type="hidden" name="titulo" value="${producto.titulo}">
                                    <input type="submit" value="-">                    
                                </form>
                            </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>  
      
       
        
    </table>

    <table>
    <tr>
        <th>Importe Total</th>
        <td>
            <c:choose>
                <c:when test="${not empty carrito.productos}">
                    ${carrito.total}$
                </c:when>
                <c:otherwise>
                    0$
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>
    <form action="eliminarCarrito.jsp" method="post">
        <input type="submit" value="Vaciar Carrito">
    </form>
    <hr>
    <div class = "menu">        
        <form action="index.html" method="post">
            <input type="submit" value="Seguir Comprando">
        </form>        <form action="pago.jsp" method="post">
            <input type="submit" value="Pagar Compra">
        </form>
    </div>


</body>
</html>