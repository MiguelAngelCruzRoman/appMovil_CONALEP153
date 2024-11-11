package com.example.app_conalep153.ui.actividad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.app_conalep153.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ActividadAdapter extends ArrayAdapter<Actividad> {
    public ActividadAdapter(Context context, List<Actividad> actividades){
        super(context, 0, actividades);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        Actividad actividad = getItem(position);
        if (converView == null) {
            converView = LayoutInflater.from(getContext()).inflate(R.layout.actividad_list_item, parent, false);
        }

        TextView nombreActividad = converView.findViewById(R.id.nombreActividad);
        TextView descripcionActividad = converView.findViewById(R.id.descripcionActividad);
        TextView diaActividad = converView.findViewById(R.id.diaActividad);
        TextView mesActividad = converView.findViewById(R.id.mesActividad);
        MaterialCardView tarjetaFecha = converView.findViewById(R.id.tarjetaFecha);
        MaterialCardView tarjetaIdentificador = converView.findViewById(R.id.tarjetaIdentificador);


        nombreActividad.setText(actividad.getnombre().toUpperCase());
        descripcionActividad.setText(actividad.getdescripcion());
        diaActividad.setText(actividad.getDia());
        mesActividad.setText(actividad.getMes());

        String colorFecha = actividad.getColorFondoFecha();
        tarjetaFecha.setCardBackgroundColor(android.graphics.Color.parseColor(colorFecha));

        String colorIdentificador = actividad.getColorFondoFecha();
        tarjetaIdentificador.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(colorIdentificador)));

        if (position % 2 == 0) {
            nombreActividad.setScaleX(1);
            descripcionActividad.setScaleX(1);
            diaActividad.setScaleX(1);
            mesActividad.setScaleX(1);
            converView.setRotation(0);
            converView.setScaleX(1);
        } else {
            nombreActividad.setScaleX(-1);
            descripcionActividad.setScaleX(-1);
            diaActividad.setScaleX(-1);
            mesActividad.setScaleX(-1);
            converView.setRotation(0);
            converView.setScaleX(-1);
        }

        converView.setOnClickListener(v -> {
            if (getContext() instanceof FragmentActivity) {
                FragmentActivity activity = (FragmentActivity) getContext();

                InfoActividadFragment fragment = InfoActividadFragment.newInstance(
                        actividad.getnombre(),
                        actividad.getdescripcion(),
                        actividad.getDia(),
                        actividad.getMes(),
                        actividad.getImagen(),
                        actividad.getubicacion(),
                        colorFecha
                );

                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_main, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return converView;
    }
}
