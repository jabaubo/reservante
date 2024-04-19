package com.jabaubo.proyecto_reservas.ui.reservas;

import java.time.LocalDate;

public class Reserva {
    private int id;
    private String nombre_apellidos;
    private String telefono;
    private String email;
    private int n_personas;
    private int id_salon;
    private String fecha;
    private String hora;
    private String observaciones;

    public Reserva(int id, String nombre_apellidos, String telefono, String email, int n_personas, int id_salon, String fecha, String hora, String observaciones) {
        this.id = id;
        this.nombre_apellidos = nombre_apellidos;
        this.telefono = telefono;
        this.email = email;
        this.n_personas = n_personas;
        this.id_salon = id_salon;
        this.fecha = fecha;
        this.hora = hora;
        this.observaciones = observaciones;
    }
    public Reserva(){

    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_apellidos() {
        return nombre_apellidos;
    }

    public void setNombre_apellidos(String nombre_apellidos) {
        this.nombre_apellidos = nombre_apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getN_personas() {
        return n_personas;
    }

    public void setN_personas(int n_personas) {
        this.n_personas = n_personas;
    }

    public int getId_salon() {
        return id_salon;
    }

    public void setId_salon(int id_salon) {
        this.id_salon = id_salon;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", nombre_apellidos='" + nombre_apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", n_personas=" + n_personas +
                ", id_salon=" + id_salon +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
