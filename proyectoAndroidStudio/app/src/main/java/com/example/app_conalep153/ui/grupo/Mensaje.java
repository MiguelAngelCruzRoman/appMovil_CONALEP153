package com.example.app_conalep153.ui.grupo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Mensaje {
    private String mensaje;
    private String tiempo;
    private String fotoPerfil;
    private String idUsuario;
    private String usuario;
    private String rol;

    public Mensaje() {
    }

    public Mensaje(String mensaje, String tiempo, String fotoPerfil, String idUsuario, String usuario, String rol) {
        this.mensaje = mensaje;
        this.tiempo = tiempo;
        this.fotoPerfil = fotoPerfil;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.rol = rol;
    }

    public String colorBordeTarjeta() {
        if (Objects.equals(rol, "ADMINISTRADOR")) {
            return "#CEA767"; // Dorado (?
        } else if (Objects.equals(rol, "DOCENTE")) {
            return "#8484DF"; // Morado (?
        } else {
            return "#A6C56B"; //Verde
        }
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
