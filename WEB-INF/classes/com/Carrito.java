package com;

import java.util.ArrayList;
public class Carrito {
    ArrayList<Producto> productos = new ArrayList<Producto>();
    float total = 0;

    public void agregarProducto(Producto producto) {
        for (Producto p : productos) {
                if(p.getTitulo().equals(producto.getTitulo())){
                    p.setCantidad(p.getCantidad() + producto.getCantidad());
                    total += producto.getTotal();
                    return;
                }
            }       
        total += producto.getTotal();
        productos.add(producto);
        
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public float getTotal() {
       
        return (float) (Math.round(total * 100.0) / 100.0);
    }

   

    public void vaciar() {
        total = 0;
        productos.clear();
    }

    public void eliminarProducto(String titulo) {
        for (Producto producto : productos) {
            if (producto.getTitulo().equals(titulo)) {
                productos.remove(producto);
                total -= producto.getTotal();
                return;
            }
        }
    }

    public void actualizarProducto(String titulo, int cantidad) {
        for (Producto producto : productos) {
            if (producto.getTitulo().equals(titulo)) {
                if(cantidad == 0){
                    productos.remove(producto);
                    total -= producto.getTotal();
                    return;
                }
                total += (cantidad - producto.getCantidad()) * (float)(Math.round(producto.getPrecio() * 100.0) / 100.0);
                producto.setCantidad(cantidad);
                return;
            }
        }
    }
}


