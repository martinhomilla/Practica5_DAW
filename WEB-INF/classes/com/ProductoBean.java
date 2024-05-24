package com;

import java.io.Serializable;

public class ProductoBean implements Serializable{
    private String titulo;
    private float precio;
    private int cantidad;
    private float total;

    public ProductoBean() {
        this.titulo = "";
        this.precio = 0;
        this.cantidad = 0;
        this.total = 0;
    }

    

    public String getTitulo() {
        return titulo;
    }
    
    public float getPrecio() {
        return precio;
    }
    public int getCantidad() {
        return cantidad;
    }

    public float getTotal() {
        return total;
    }

    //setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setPrecio(float precio) {
        this.precio = (float) (Math.round(precio * 100.0) / 100.0);
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public void setTotal(float total) {
        this.total = (float) (Math.round(total * 100.0) / 100.0);
    }

}
