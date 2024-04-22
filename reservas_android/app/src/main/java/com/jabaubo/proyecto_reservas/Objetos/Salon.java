package com.jabaubo.proyecto_reservas.Objetos;

public class Salon {
    private int id;
    private String nombre;
    private int aforo;

    public Salon() {
    }

    public Salon(int id, String nombre, int aforo) {
        this.id = id;
        this.nombre = nombre;
        this.aforo = aforo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    @Override
    public String toString() {
        return "Salon{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", aforo=" + aforo +
                '}';
    }
}
