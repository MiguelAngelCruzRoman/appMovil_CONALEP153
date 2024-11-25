package com.example.app_conalep153.ui.actividad;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_conalep153.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MensajesActividadAdapter extends RecyclerView.Adapter<MensajeHolder> {
    private List<Mensaje> listaMensajes = new ArrayList<>();
    private Context c;

    public MensajesActividadAdapter(Context c) {
        this.c = c;
    }

    public void addMensaje(Mensaje m){
        listaMensajes.add(m);
        notifyItemInserted(listaMensajes.size());
    }

    @NonNull
    @Override
    public MensajeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.mensaje_actividad_list_item, parent, false);

        return new MensajeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeHolder holder, int position) {
        holder.getFechaHoraMensaje().setText(listaMensajes.get(position).getTiempo());
        holder.getContenidoMensaje().setText(listaMensajes.get(position).getMensaje());
        holder.getNombreUsuario().setText(listaMensajes.get(position).getUsuario());

        Picasso.get()
                .load(listaMensajes.get(position).getFotoPerfil())
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
                .into(holder.getUsuarioMensaje());

        SharedPreferences sharedPreferences = c.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String idUsuarioActual = sharedPreferences.getString("id_usuario", null);

        Mensaje mensaje = listaMensajes.get(position);

        if (!mensaje.getIdUsuario().equals(idUsuarioActual)) {
            int color = android.graphics.Color.parseColor("#A6C56B");
            holder.getTarjetaMensaje().setStrokeColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }
}
