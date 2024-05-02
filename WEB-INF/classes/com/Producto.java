package com;

import java.io.Serializable;

public class Producto implements Serializable{
    private String titulo;
    private float precio;
    private int cantidad;

    

    public String getTitulo() {
        return titulo;
    }
    
    public float getPrecio() {
        return precio;
    }
    public int getCantidad() {
        return cantidad;
    }

    //setter 
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setPrecio(float precio) {
        this.precio = (float) (Math.round(precio * 100.0) / 100.0);
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getTotal() {
        return (float) (Math.round(precio * cantidad * 100.0) / 100.0);
    }
}
