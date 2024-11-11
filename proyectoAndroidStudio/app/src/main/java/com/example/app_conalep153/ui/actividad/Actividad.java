package com.example.app_conalep153.ui.actividad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Actividad {
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String imagen;
    private String fecha;

    public Actividad(String nombre, String descripcion, String ubicacion, String imagen, String fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public Actividad(String nombre, String descripcion, String fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getDia() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(fecha);
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
            return dayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMes() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(fecha);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", new Locale("es", "ES"));

            String mes = monthFormat.format(date);

            return mes.substring(0, 1).toUpperCase() + mes.substring(1).toLowerCase();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getColorFondoFecha() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date fechaActividad = sdf.parse(fecha);
            Date fechaActual = new Date();

            long diferenciaEnMillis = fechaActividad.getTime() - fechaActual.getTime();

            long diferenciaEnDias = diferenciaEnMillis / (1000 * 60 * 60 * 24);

            if (diferenciaEnDias < 0) {
                return "#DF9696"; // Rojo
            } else if (diferenciaEnDias <= 7) {
                return "#F7CC7B"; // Naranja
            } else {
                return "#A0DC94"; // Verde
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return "#808080";
        }
    }

    public String getColorFondoIdentificador() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date fechaActividad = sdf.parse(fecha);
            Date fechaActual = new Date();

            long diferenciaEnMillis = fechaActividad.getTime() - fechaActual.getTime();

            long diferenciaEnDias = diferenciaEnMillis / (1000 * 60 * 60 * 24);

            if (diferenciaEnDias < 0) {
                return "#D13F3F"; // Rojo
            } else if (diferenciaEnDias <= 7) {
                return "#FFA000"; // Naranja
            } else {
                return "#52CA39"; // Verde
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return "#808080";
        }
    }

    public String getnombre() {

        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getubicacion() {
        return ubicacion;
    }

    public void setubicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
