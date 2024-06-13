/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jabaubo.proyecto_reservas.Clases;

import java.time.LocalDate;

public class Vacaciones {
    private int id;
    private int idRestaurante;
    private String nombre;
    private LocalDate Inicio;
    private LocalDate Fin;

    public Vacaciones(String nombre, LocalDate Inicio, LocalDate Fin, int idRestaurante,int id) {
        this.nombre = nombre;
        this.Inicio = Inicio;
        this.Fin = Fin;
        this.idRestaurante = idRestaurante;
        this.id  = id;
    }
    public Vacaciones(String nombre, String Inicio, String Fin, int idRestaurante,int id) {
        this.nombre = nombre;
        this.Inicio = LocalDate.parse(Inicio);
        this.Fin = LocalDate.parse(Fin);
        this.idRestaurante = idRestaurante;
        this.id  = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
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
    public String toJson(){
        String json = String.format("{\"id_restaurante\":\"%d\",\"inicio\":\"%s\",\"fin\":\"%s\",\"id_vacacion\":\"%d\",\"nombre\":\"%s\"}\n", this.idRestaurante,this.Inicio.toString(),this.Fin.toString(),this.id,this.nombre);
        System.out.println(json);
        return  json;
    };
}
