<%@ page import="com.Producto " %>
<%@ page import="com.Carrito " %>

<%@ page isELIgnored="false" %>
<%@ page session="true"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Finalizar Compra</title>
    <link rel="stylesheet"  href="estilos.css">
</head>
<body>
    <h1> PEDIDO REALIZADO CON EXITO</h1>
    <h2> Informacion de la compra del usuario ${usuario}</h2>
    <table>
         <c:forEach var="producto" items="${carrito.productos}">            
                    <tr>
                            <td>${producto.titulo}</td>
                            <td>${producto.cantidad}</td>
                            <td>${producto.precio}$</td>                            
                    </tr>
        </c:forEach>
        <tr>
            <th>Importe Total</th>
            <td>${carrito.total}$</td>            
        </tr>
        
    </table>
    
    

    <form action="index.html" method="post">
        <input type="submit" value="Seguir Comprando">
    </form>    
</body>