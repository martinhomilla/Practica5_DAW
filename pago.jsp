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
    <section class=inicio-sesion>
        <article>
            <h1>Inicia Sesion</h1>
        
            <form class="registro" method="post" action="gestionarBD">
                <label for="usuario">Usuario</label>
                <input type="text" name="usuario" placeholder="Usuario">
                <label for="password">Contraseña</label>
                <input type="password" name="password" placeholder="Contraseña">   
                <input type="hidden" name="total" value="${carrito.total}">
                <input type="hidden" name="accion" value="iniciarSesion">
                <input type="submit" value="Iniciar Sesion">
            </form>
        </article>
        <article>
            <h1>Crea tu cuenta</h1>
        
            <form class="registro" method="post" action="gestionarBD">
                <label for="usuario">Usuario</label>
                <input type="text" name="usuario" placeholder="Usuario">
                <label for="password">Contraseña</label>
                <input type="password" name="password" placeholder="Contraseña">
                <label for="tipoTarjeta">Tipo de tarjeta</label>
                <input type="text" name="tipoTarjeta">
                <label for="numeroTarjeta">Numero de tarjeta</label>
                <input type="text" name="numeroTarjeta">
                <input type="hidden" name="total" value="${carrito.total}">
                <input type="hidden" name="accion" value="registrarUsuario">
                <input type="submit" value="Registrar">
            </form>
        </article>
    </section>
    <div class=error>
        <c:choose>
            <c:when test="${not empty error_message}">
                <p class=error>${error_message}</p>
            </c:when>
            <c:otherwise>
                <p></p>
            </c:otherwise>
        </c:choose>    
    </div>    
    
</body>
</html>