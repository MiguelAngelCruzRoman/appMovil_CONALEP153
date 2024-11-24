package com.example.app_conalep153.ui.docente;

public class Docente {
    private String id_docente;
    private String nombre;
    private String foto;
    private String modulo;

    public Docente(String id_docente, String nombre, String foto, String modulo) {
        this.id_docente = id_docente;
        this.nombre = nombre;
        this.foto = foto;
        this.modulo = modulo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getId_docente() {
        return id_docente;
    }

    public void setId_docente(String id_docente) {
        this.id_docente = id_docente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
