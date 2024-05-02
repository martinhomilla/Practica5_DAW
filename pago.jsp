<%@ page import="com.Carrito " %>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
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

    <article>
        <h1>Inicia Sesion o Crea tu cuenta</h1>
    
        <form class="registro" method="post" action="Registro">
            <label for="usuario">Usuario</label>
            <input type="text" name="usuario" placeholder="Usuario">
            <label for="password">Contraseña</label>
            <input type="password" name="password" placeholder="Contraseña">
            <label for="tipoTarjeta">Tipo de tarjeta</label>
            <input type="text" name="tipoTarjeta">
            <label for="numeroTarjeta">Numero de tarjeta</label>
            <input type="text" name="numeroTarjeta">
            <input type="submit" value="Registrar">
        </form>
    </article>
    <form method="post" action="finalizarCompra.jsp">
        <input type="submit" value="Realizar pago">
    </form>
</body>
</html>