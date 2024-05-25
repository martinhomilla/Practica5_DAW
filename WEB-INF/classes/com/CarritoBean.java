package com;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * 
 * Clase que representa un carrito de compras.
 * Sus atributos son: productos y total.
 * productos: lista de productos.
 * total: precio total de los productos en el carrito.
 */

public class CarritoBean implements Serializable{
    private ArrayList<ProductoBean>  productos;
    private float total;


    public CarritoBean() {
        productos = new ArrayList<ProductoBean>();
        total = 0;
    }
    public ArrayList<ProductoBean> getProductos() {
        return productos;
    }

    public float getTotal() {
       
        return (float) (Math.round(total * 100.0) / 100.0);
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    public void setProductos(ArrayList<ProductoBean> productos) {
        this.productos = productos;
    }
    

    


    public void agregarProducto(ProductoBean producto) {
        for (ProductoBean p : productos) {
                if(p.getTitulo().equals(producto.getTitulo())){
                    p.setCantidad(p.getCantidad() + producto.getCantidad());
                    total += producto.getTotal();
                    return;
                }
            }       
        total += producto.getTotal();
        productos.add(producto);
        
    }

   

    public void vaciar() {
        total = 0;
        productos.clear();
    }

    public void eliminarProducto(String titulo) {
        for (ProductoBean producto : productos) {
            if (producto.getTitulo().equals(titulo)) {
                productos.remove(producto);
                total -= producto.getTotal();
                return;
            }
        }
    }

    public void actualizarProducto(String titulo, int cantidad) {
        for (ProductoBean producto : productos) {
            if (producto.getTitulo().equals(titulo)) {
                if(cantidad == 0){
                    productos.remove(producto);
                    total -= producto.getTotal();
                    return;
                }
                total += (cantidad - producto.getCantidad()) * (float)(Math.round(producto.getPrecio() * 100.0) / 100.0);
                producto.setCantidad(cantidad);
                producto.setTotal(producto.getPrecio() * cantidad);
                return;
            }
        }
    }
}


