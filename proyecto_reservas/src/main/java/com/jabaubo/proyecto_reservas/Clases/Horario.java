package com.jabaubo.proyecto_reservas.Clases;

public class Horario {

    private String dia;
    private Boolean cerrado;
    private String hora_inicio_m;
    private String hora_fin_m;
    private String hora_inicio_t;
    private String hora_fin_t;
    private String orden;

    public Horario(String dia, Boolean cerrado, String hora_inicio_m, String hora_fin_m, String hora_inicio_t, String hora_fin_t) {
        this.dia = dia;
        this.cerrado = cerrado;
        this.hora_inicio_m = hora_inicio_m;
        this.hora_fin_m = hora_fin_m;
        this.hora_inicio_t = hora_inicio_t;
        this.hora_fin_t = hora_fin_t;
    }

    public Horario() {
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Boolean getCerrado() {
        return cerrado;
    }

    public void setCerrado(Boolean cerrado) {
        this.cerrado = cerrado;
    }

    public String getHora_inicio_m() {
        return hora_inicio_m;
    }

    public void setHora_inicio_m(String hora_inicio_m) {
        this.hora_inicio_m = hora_inicio_m;
    }

    public String getHora_fin_m() {
        return hora_fin_m;
    }

    public void setHora_fin_m(String hora_fin_m) {
        this.hora_fin_m = hora_fin_m;
    }

    public String getHora_inicio_t() {
        return hora_inicio_t;
    }

    public void setHora_inicio_t(String hora_inicio_t) {
        this.hora_inicio_t = hora_inicio_t;
    }

    public String getHora_fin_t() {
        return hora_fin_t;
    }

    public void setHora_fin_t(String hora_fin_t) {
        this.hora_fin_t = hora_fin_t;
    }

    public void setOrden(int orden) {
        this.orden = String.valueOf(orden);
    }

    @Override
    public String toString() {
        return "Horario{" + "dia=" + dia + ", cerrado=" + cerrado + ", hora_inicio_m=" + hora_inicio_m + ", hora_fin_m=" + hora_fin_m + ", hora_inicio_t=" + hora_inicio_t + ", hora_fin_t=" + hora_fin_t + ", orden=" + orden + '}';
    }

    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        jsonBuilder.append("  \"dia\": \"").append(dia).append("\",\n");
        jsonBuilder.append("  \"cerrado\": ").append(cerrado).append(",\n");
        jsonBuilder.append("  \"hora_inicio_m\": \"").append(hora_inicio_m).append("\",\n");
        jsonBuilder.append("  \"hora_fin_m\": \"").append(hora_fin_m).append("\",\n");
        jsonBuilder.append("  \"hora_inicio_t\": \"").append(hora_inicio_t).append("\",\n");
        jsonBuilder.append("  \"hora_fin_t\": \"").append(hora_fin_t).append("\",\n");
        jsonBuilder.append("  \"orden\": \"").append(orden).append("\"\n");
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}

