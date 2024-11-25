package com.example.app_conalep153.ui.grupo;

public class Grupo {
    private String nombre;
    private String imagenGrupo;
    private String username;
    private String id_usuario;
    private String rolUsuario;
    private String imagenUsuario;

    public Grupo(String nombre, String imagenGrupo, String username, String id_usuario, String rolUsuario, String imagenUsuario) {
        this.nombre = nombre;
        this.imagenGrupo = imagenGrupo;
        this.username = username;
        this.id_usuario = id_usuario;
        this.rolUsuario = rolUsuario;
        this.imagenUsuario = imagenUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenGrupo() {
        return imagenGrupo;
    }

    public void setImagenGrupo(String imagenGrupo) {
        this.imagenGrupo = imagenGrupo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public String getImagenUsuario() {
        return imagenUsuario;
    }

    public void setImagenUsuario(String imagenUsuario) {
        this.imagenUsuario = imagenUsuario;
    }
}
