package com.example.app_conalep153.ui.horario;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_conalep153.R;

import java.util.List;
import java.util.Objects;

public class ModuloAdapter extends ArrayAdapter<Modulo> {
    public ModuloAdapter(Context context, List<Modulo> modulos){
        super(context, 0, modulos);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        Modulo modulo = getItem(position);
        if (converView == null) {
            converView = LayoutInflater.from(getContext()).inflate(R.layout.horario_list_item, parent, false);
        }

        ImageView tipoFormacion = converView.findViewById(R.id.tipoFormacion);
        TextView horasClase = converView.findViewById(R.id.horasClase);
        TextView nombreModulo = converView.findViewById(R.id.nombreModulo);
        TextView nombreDocente = converView.findViewById(R.id.nombreDocente);
        TextView salonClase = converView.findViewById(R.id.salonClase);

        horasClase.setText(modulo.getHorasClase());
        nombreModulo.setText(modulo.getNombreModulo());
        nombreDocente.setText(modulo.getNombreDocente());
        salonClase.setText(modulo.getSalonClase());

        String nombreImagen = modulo.getTipoFormacion();

        if (Objects.equals(nombreImagen, "PROFESIONAL")) {
            tipoFormacion.setImageResource(R.drawable.profesional);
        } else if  (Objects.equals(nombreImagen, "TRAYECTO TECNICO")) {
            tipoFormacion.setImageResource(R.drawable.trayecto_tecnico);
        }

        return converView;
    }
}

