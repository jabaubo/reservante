/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jabaubo.proyecto_reservas.Objetos;

import java.time.LocalDate;
import java.time.LocalTime;


public class Ocupacion {
    private LocalTime hora;
    private LocalDate fecha;
    private int nReservas;
    private String ocupacion;

    public Ocupacion(LocalTime hora, int nReservas, String ocupacion,LocalDate fecha) {
        this.hora = hora;
        this.nReservas = nReservas;
        this.ocupacion = ocupacion;
        this.fecha = fecha;
    }
    public Ocupacion(){
        
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getnReservas() {
        return nReservas;
    }

    public void setnReservas(int nReservas) {
        this.nReservas = nReservas;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    @Override
    public String toString() {
        return "Ocupacion{" + "hora=" + hora + ",\n nReservas=" + nReservas + ", ocupacion=" + ocupacion + '}';
    }
    
    
}
