package com.example.app_conalep153.ui.grupo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.app_conalep153.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GrupoAdapter extends ArrayAdapter<Grupo> {
    public GrupoAdapter(Context context, List<Grupo> docentes){
        super(context, 0, docentes);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        Grupo grupo = getItem(position);
        if (converView == null) {
            converView = LayoutInflater.from(getContext()).inflate(R.layout.grupo_list_item, parent, false);
        }

        TextView nombreGrupo = converView.findViewById(R.id.nombreGrupo);
        ImageView fotoGrupo = converView.findViewById(R.id.fotoGrupo);


        nombreGrupo.setText(grupo.getNombre().toUpperCase());

        Picasso.get()
                .load(grupo.getImagenGrupo())
                .fit()
                .into(fotoGrupo);


        converView.setOnClickListener(v -> {
            if (getContext() instanceof FragmentActivity) {
                FragmentActivity activity = (FragmentActivity) getContext();

                GrupoChatFragment fragment = GrupoChatFragment.newInstance(
                        grupo.getNombre(),
                        grupo.getImagenGrupo(),
                        grupo.getUsername(),
                        grupo.getId_usuario(),
                        grupo.getRolUsuario(),
                        grupo.getImagenUsuario()
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

