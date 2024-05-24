
<%@ page import="com.ProductoBean " %>
<%@ page import="com.CarritoBean" %>

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
            <th>Producto</th>
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
                            <td>${producto.total}$</td>
                            <td class="botones" style="border:none; display:flex; flex-direction:row; gap:3px;">
                            
                                <form action="gestionarCarrito" method="post">
                                    <input type="hidden" name="titulo" value="${producto.titulo}">
                                    <input type="hidden" name="accion" value="eliminar">
                                    <input type="submit" value="Eliminar">                    
                                </form>
                                <form action="gestionarCarrito" method="post">

                                    <input type="hidden" name="titulo" value="${producto.titulo}">
                                    <input type="hidden" name="cantidad" value="${producto.cantidad}">
                                    <input type="hidden" name="accion" value="sumar">
                                    <input type="submit" value="+">                    
                                </form>
                                <form action="gestionarCarrito" method="post">
                                    <input type="hidden" name="cantidad" value="${producto.cantidad}">
                                    <input type="hidden" name="titulo" value="${producto.titulo}">
                                    <input type="hidden" name="accion" value="restar">
                                    <input type="submit" value="-">                    
                                </form>
                            </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>  
      
       
        
    </table>

    <c:choose>
        <c:when test="${not empty carrito.productos}">
                   
            <table>
            <tr>
                <th>Importe Total</th>
                <td>${carrito.total}$</td> 
            </tr>
            </table>

            <form action="gestionarCarrito" method="post">
                <input type="hidden" name="accion" value="vaciar">
                <input type="submit" value="Vaciar Carrito">
            </form>
            <hr>
            <div class = "menu">        
                <form action="index.html" method="post">
                    <input type="submit" value="Seguir Comprando">
                </form>       
                 <form action="pago.jsp" method="post">
                    
                    <input type="submit" value="Pagar Compra">
                </form>
            </div>
            </c:when>
            <c:otherwise>
                     <table>
            <tr>
                <th>Importe Total</th>
                <td>0$</td>                 
                
            </tr>
            </table>
            <form action="gestionarCarrito" method="post">
                <input type="hidden" name="accion" value="vaciar">
                <input type="submit" value="Vaciar Carrito" disabled>
            </form>
            <hr>
            <div class = "menu">        
                <form action="index.html" method="post">
                    <input type="submit" value="Seguir Comprando">
                </form>        <form action="pago.jsp" method="post">
                    <input type="submit" value="Pagar Compra" disabled>
                </form>
            </div>
                </c:otherwise>
    </c:choose>
    


</body>
</html>