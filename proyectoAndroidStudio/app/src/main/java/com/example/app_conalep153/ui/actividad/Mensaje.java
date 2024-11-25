package com.example.app_conalep153.ui.actividad;

import java.util.Objects;

public class Mensaje {
    private String mensaje;
    private String tiempo;
    private String fotoPerfil;
    private String idUsuario;
    private String usuario;

    public Mensaje() {
    }

    public Mensaje(String mensaje, String tiempo, String fotoPerfil, String idUsuario, String usuario) {
        this.mensaje = mensaje;
        this.tiempo = tiempo;
        this.fotoPerfil = fotoPerfil;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
