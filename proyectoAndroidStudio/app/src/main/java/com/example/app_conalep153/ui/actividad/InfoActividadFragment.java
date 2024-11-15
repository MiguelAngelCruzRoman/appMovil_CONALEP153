package com.example.app_conalep153.ui.actividad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.app_conalep153.R;
import com.squareup.picasso.Picasso;  // Importar Picasso

import com.google.android.material.card.MaterialCardView;

public class InfoActividadFragment extends Fragment {

    public InfoActividadFragment() {
    }

    public static InfoActividadFragment newInstance(String nombre, String descripcion, String dia, String mes, String imagen, String ubicacion, String colorFecha) {
        InfoActividadFragment fragment = new InfoActividadFragment();
        Bundle args = new Bundle();
        args.putString("nombre", nombre);
        args.putString("descripcion", descripcion);
        args.putString("dia", dia);
        args.putString("mes", mes);
        args.putString("imagen", imagen);
        args.putString("ubicacion", ubicacion);
        args.putString("colorFecha", colorFecha);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_actividad, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String nombre = args.getString("nombre");
            String descripcion = args.getString("descripcion");
            String dia = args.getString("dia");
            String mes = args.getString("mes");
            String imagen = args.getString("imagen");
            String ubicacion = args.getString("ubicacion");
            String colorFecha = args.getString("colorFecha");

            TextView nombreActividad = rootView.findViewById(R.id.nombreActividad);
            TextView descripcionActividad = rootView.findViewById(R.id.descripcion);
            TextView diaActividad = rootView.findViewById(R.id.diaActividad);
            TextView mesActividad = rootView.findViewById(R.id.mesActividad);
            ImageView imagenActividad = rootView.findViewById(R.id.imagen);
            TextView ubicacionActividad = rootView.findViewById(R.id.ubicacion);
            MaterialCardView tarjetaFecha = rootView.findViewById(R.id.tarjetaFecha);

            nombreActividad.setText(nombre);
            descripcionActividad.setText(descripcion);
            diaActividad.setText(dia);
            mesActividad.setText(mes);
            ubicacionActividad.setText(ubicacion);

            Picasso.get()
                    .load(imagen)
                    .into(imagenActividad);

            tarjetaFecha.setCardBackgroundColor(android.graphics.Color.parseColor(colorFecha));
        }

        ImageView regresar = rootView.findViewById(R.id.regresar);

        regresar.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(rootView);

            if (!navController.popBackStack()) {
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }
}
