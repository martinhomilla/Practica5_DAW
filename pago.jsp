<%@ page import="com.Carrito " %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pago</title>
    <link rel="stylesheet"  href="estilos.css">

</head>
<body>
    <h1>Pago</h1>
     <table>
        <tr>
            <th>Importe Total</th>
            <td>${carrito.total}$</td>            
        </tr>
        
    </table>
    <form method="post" action="finalizarCompra.jsp">
        <input type="submit" value="Realizar pago">
    </form>
</body>
</html>