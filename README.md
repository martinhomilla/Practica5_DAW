# PROTECTO II. Minitienda

## Descripción
Este proyecto es una minitienda en línea desarrollada utilizando tecnologías web. Permite a los usuarios realizar compras de productos y administrar su carrito de compras.

## Tecnologías utilizadas
- Lenguaje de programación: Java
- Gestor de base de datos: PostgreSQL
- Frontend: HTML, CSS, JS
- Servidor de aplicaciones: Apache Tomcat

## Configuración de la base de datos
La aplicación utiliza el gestor de base de datos PostgreSQL. Asegúrese de tenerlo instalado y configurado correctamente antes de ejecutar la aplicación.

1. Cree una base de datos llamada `minitienda` en su servidor PostgreSQL.
2. La BD debe tener las siguientes tablas:

    2.1 `Usuarios`, con propiedades nombre, password, tipo_tarjeta y numero_tarjeta.

    2.2 `Pedidos`, con propiedades usuario, importe, fecha.

## Patrón Modelo-Vista-Controlador (MVC)

El patrón Modelo-Vista-Controlador (MVC) se utiliza en este proyecto para separar las responsabilidades de cada componente:

- **Modelo**: Se encarga de la lógica de negocio y el acceso a los datos. En este caso serían los JavaBeans CarritoBean y ProductoBean, además de la parte de los servlets que acceden a la Base de Datos. 

- **Vista**: Muestra la información al usuario de manera visualmente atractiva. De esto se ocupan los JSP.
- **Controlador**: Actúa como intermediario entre el modelo y la vista, procesando las solicitudes del usuario y actualizando la vista en consecuencia. Este papel lo juegan los servlets, que controlan las peticiones y respuestas del cliente.

