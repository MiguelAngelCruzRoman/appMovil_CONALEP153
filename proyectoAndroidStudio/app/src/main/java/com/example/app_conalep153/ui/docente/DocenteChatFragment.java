package com.example.app_conalep153.ui.docente;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_conalep153.R;
import com.example.app_conalep153.ui.actividad.InfoActividadFragment;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

public class DocenteChatFragment extends Fragment {

    public DocenteChatFragment() {
    }

    public static DocenteChatFragment newInstance(String id_docente, String nombre, String foto, String modulo) {
        DocenteChatFragment fragment = new DocenteChatFragment();
        Bundle args = new Bundle();
        args.putString("id_docente", id_docente);
        args.putString("nombre", nombre);
        args.putString("foto", foto);
        args.putString("modulo", modulo);
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
        View rootView = inflater.inflate(R.layout.fragment_docente_chat, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String id_docente = args.getString("id_docente");
            String nombre = args.getString("nombre");
            String modulo = args.getString("modulo");
            String foto = args.getString("foto");

            TextView nombreDocente = rootView.findViewById(R.id.nombreDocente);
            TextView nombreModulo = rootView.findViewById(R.id.nombreModulo);
            ImageView fotoDocente = rootView.findViewById(R.id.fotoDocente);

            nombreDocente.setText(nombre);
            nombreModulo.setText("- "+modulo+" -");
            Picasso.get()
                    .load(foto)
                    .transform(new com.squareup.picasso.Transformation() {
                        @Override
                        public Bitmap transform(Bitmap source) {
                            int size = Math.min(source.getWidth(), source.getHeight());
                            Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(result);

                            Paint paint = new Paint();
                            paint.setAntiAlias(true);
                            paint.setFilterBitmap(true);
                            paint.setDither(true);

                            canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);

                            Rect srcRect = new Rect(0, 0, source.getWidth(), source.getHeight());
                            Rect dstRect = new Rect(0, 0, size, size);
                            paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
                            canvas.drawBitmap(source, srcRect, dstRect, paint);

                            source.recycle();

                            return result;
                        }

                        @Override
                        public String key() {
                            return "circle";
                        }
                    })
                    .into(fotoDocente);
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