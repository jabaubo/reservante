package com.jabaubo.proyecto_reservas.Objetos;

public class ReservaFechas {
    private String hora;
    private int n_reservas;
    private int n_personas;
    private int aforo;
    private String fecha;

    public ReservaFechas(String hora, int n_reservas, int n_personas, int aforo,String fecha) {
        this.hora = hora;
        this.n_reservas = n_reservas;
        this.n_personas = n_personas;
        this.aforo = aforo;
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getN_reservas() {
        return n_reservas;
    }

    public void setN_reservas(int n_reservas) {
        this.n_reservas = n_reservas;
    }

    public int getN_personas() {
        return n_personas;
    }

    public void setN_personas(int n_personas) {
        this.n_personas = n_personas;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "ReservaFechas{" +
                "hora='" + hora + '\'' +
                ", n_reservas=" + n_reservas +
                ", n_personas=" + n_personas +
                ", aforo=" + aforo +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
