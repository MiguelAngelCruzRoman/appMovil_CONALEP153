package com.example.app_conalep153.ui.actividad;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_conalep153.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

public class MensajeHolder extends RecyclerView.ViewHolder {

    private ImageView usuarioMensaje;
    private MaterialTextView contenidoMensaje, fechaHoraMensaje,nombreUsuario;
    private MaterialCardView tarjetaMensaje;

    public MensajeHolder(@NonNull View itemView) {
        super(itemView);
        usuarioMensaje = (ImageView) itemView.findViewById(R.id.usuarioMensaje);
        contenidoMensaje = (MaterialTextView) itemView.findViewById(R.id.contenidoMensaje);
        fechaHoraMensaje = (MaterialTextView) itemView.findViewById(R.id.fechaHoraMensaje);
        nombreUsuario = (MaterialTextView) itemView.findViewById(R.id.nombreUsuario);
        tarjetaMensaje = (MaterialCardView) itemView.findViewById(R.id.tarjetaMensaje);
    }

    public MaterialCardView getTarjetaMensaje() {
        return tarjetaMensaje;
    }

    public void setTarjetaMensaje(MaterialCardView tarjetaMensaje) {
        this.tarjetaMensaje = tarjetaMensaje;
    }

    public MaterialTextView getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(MaterialTextView nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public ImageView getUsuarioMensaje() {
        return usuarioMensaje;
    }

    public void setUsuarioMensaje(ImageView usuarioMensaje) {
        this.usuarioMensaje = usuarioMensaje;
    }

    public MaterialTextView getContenidoMensaje() {
        return contenidoMensaje;
    }

    public void setContenidoMensaje(MaterialTextView contenidoMensaje) {
        this.contenidoMensaje = contenidoMensaje;
    }

    public MaterialTextView getFechaHoraMensaje() {
        return fechaHoraMensaje;
    }

    public void setFechaHoraMensaje(MaterialTextView fechaHoraMensaje) {
        this.fechaHoraMensaje = fechaHoraMensaje;
    }
}
