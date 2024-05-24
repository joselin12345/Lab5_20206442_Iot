package com.example.lab5_20206442.Entity;

import java.io.Serializable;

public class TareaData implements Serializable {

    private String titulo;
    private String Descripcion;
    private String FechaVencimiento;
    private String Codigo;
    private String Hora;

    public TareaData(String titulo, String Descripcion, String FechaVencimiento, String Hora, String Codigo) {
        this.titulo = titulo;
        this.Descripcion = Descripcion;
        this.FechaVencimiento = FechaVencimiento;
        this.Hora = Hora;
        this.Codigo = Codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getFechaVencimiento() {
        return FechaVencimiento;
    }

    public void setFechaVencimiento(String FechaVencimiento) {
        this.FechaVencimiento = FechaVencimiento;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

}
