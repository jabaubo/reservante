package com.jabaubo.proyecto_reservas.Objetos;

public class ReservaFechas {
    private String hora;
    private int nReservas;
    private String fecha;
    private String ocupacion;

    public ReservaFechas() {
    }

    public ReservaFechas(String hora, String fecha, String ocupacion, int nReservas) {
        this.hora = hora;
        this.fecha = fecha;
        this.ocupacion = ocupacion;
        this.nReservas = nReservas;
    }

    public int getnReservas() {
        return nReservas;
    }

    public void setnReservas(int nReservas) {
        this.nReservas = nReservas;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
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
                ", nReservas=" + nReservas +
                ", fecha='" + fecha + '\'' +
                ", ocupacion='" + ocupacion + '\'' +
                '}';
    }
}
