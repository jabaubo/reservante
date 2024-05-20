package com.jabaubo.proyecto_reservas.Clases;

public class Salon {
    private int id;
    private String nombre;
    private int aforo;
    private int idRestaurante;
    
    public Salon() {
    }

    public Salon(int id, String nombre, int aforo , int idRestaurante) {
        this.id = id;
        this.nombre = nombre;
        this.aforo = aforo;
        this.idRestaurante = idRestaurante;
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

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }


    @Override
    public String toString() {
        return "Salon{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", aforo=" + aforo +
                '}';
    }
    public String toJson(){
        String json = String.format("{\"id_salon\":\"%d\",\"id_restaurante\":\"%d\",\"nombre\":\"%s\",\"aforo\":\"%d\"}", id,idRestaurante,nombre,aforo);
        return json;
    }
}
