

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Finalizar Compra</title>
    <link rel="stylesheet"  href="estilos.css">
</head>
<body>
    <h1>Usuario: ${usuario}</h1>
    <h2> A pagar con tarjeta: ${numeroTarjeta}</h2>
    <table>
        <tr>
            <th>Importe Total</th>
            <td>${total}$</td>            
        </tr>
    </table>

    <div class=menu>
        <form action="index.html" method="post">
            <input type="hidden" name="accion" value="cancelar">
            <input type="submit" value="Cancelar">
        </form>
        <form action="gestionarBD" method="post">    
            <input type="hidden" name="usuario" value="${usuario}">
            <input type="hidden" name="numeroTarjeta" value="${numeroTarjeta}">
            <input type="hidden" name="total" value="${total}">
            <input type="hidden" name="accion" value="registrarPedido">    
            <input type="submit" value="Pagar">
        </form>
    </div>

     
</body>