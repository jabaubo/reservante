/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jabaubo.proyecto_reservas.Clases;

import java.time.LocalDate;

/**
 *
 * @author pokem
 */
public class Vacaciones {
    private String nombre;
    private LocalDate Inicio;
    private LocalDate Fin;

    public Vacaciones(String nombre, LocalDate Inicio, LocalDate Fin) {
        this.nombre = nombre;
        this.Inicio = Inicio;
        this.Fin = Fin;
    }
    public Vacaciones(String nombre, String Inicio, String Fin) {
        this.nombre = nombre;
        this.Inicio = LocalDate.parse(Inicio);
        this.Fin = LocalDate.parse(Fin);
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getInicio() {
        return Inicio;
    }

    public void setInicio(LocalDate Inicio) {
        this.Inicio = Inicio;
    }

    public LocalDate getFin() {
        return Fin;
    }

    public void setFin(LocalDate Fin) {
        this.Fin = Fin;
    }

    @Override
    public String toString() {
        return "Vacaciones{" + "nombre=" + nombre + ", Inicio=" + Inicio + ", Fin=" + Fin + '}';
    }
    
}
