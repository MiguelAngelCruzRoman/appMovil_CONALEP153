package com.example.app_conalep153.ui.horario;

public class Modulo {
    public String tipoFormacion;
    public String horasClase;
    public String nombreModulo;
    public String nombreDocente;
    public String salonClase;

    public Modulo(String tipoFormacion, String horasClase, String nombreModulo, String nombreDocente, String salonClase) {
        this.tipoFormacion = tipoFormacion;
        this.horasClase = horasClase;
        this.nombreModulo = nombreModulo;
        this.nombreDocente = nombreDocente;
        this.salonClase = salonClase;
    }

    public String getTipoFormacion() {
        return tipoFormacion;
    }

    public void setTipoFormacion(String tipoFormacion) {
        this.tipoFormacion = tipoFormacion;
    }

    public String getHorasClase() {
        return horasClase;
    }

    public void setHorasClase(String horasClase) {
        this.horasClase = horasClase;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getSalonClase() {
        return salonClase;
    }

    public void setSalonClase(String salonClase) {
        this.salonClase = salonClase;
    }
}
