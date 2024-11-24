package com.example.app_conalep153.ui.docente;

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

public class DocenteAdapter  extends ArrayAdapter<Docente> {
    public DocenteAdapter(Context context, List<Docente> docentes){
        super(context, 0, docentes);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        Docente docente = getItem(position);
        if (converView == null) {
            converView = LayoutInflater.from(getContext()).inflate(R.layout.docente_list_item, parent, false);
        }

        TextView nombreDocente = converView.findViewById(R.id.nombreDocente);
        ImageView fotoDocente = converView.findViewById(R.id.fotoDocente);


        nombreDocente.setText(docente.getNombre().toUpperCase());

        Picasso.get()
                .load(docente.getFoto())
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


        converView.setOnClickListener(v -> {
            if (getContext() instanceof FragmentActivity) {
                FragmentActivity activity = (FragmentActivity) getContext();

                DocenteChatFragment fragment = DocenteChatFragment.newInstance(
                        docente.getId_docente(),
                        docente.getNombre(),
                        docente.getFoto(),
                        docente.getModulo()
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

