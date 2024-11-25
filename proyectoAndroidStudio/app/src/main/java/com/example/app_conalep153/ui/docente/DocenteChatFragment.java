package com.example.app_conalep153.ui.docente;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_conalep153.R;
import com.example.app_conalep153.ui.actividad.InfoActividadFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DocenteChatFragment extends Fragment {
    private RecyclerView RecyclerViewMensajes;
    private ImageView btnEnviarMensaje;
    private TextInputEditText cuadroMensaje;

    private MensajesDocenteAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


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

        RecyclerViewMensajes = rootView.findViewById(R.id.RecyclerViewMensajes);
        btnEnviarMensaje = rootView.findViewById(R.id.enviarMensaje);
        cuadroMensaje = rootView.findViewById(R.id.textFieldMensaje);

        String id_docente = "";
        String modulo = "";
        Bundle args = getArguments();
        if (args != null) {
            id_docente = args.getString("id_docente");
            String nombre = args.getString("nombre");
            modulo = args.getString("modulo");
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

        database = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        databaseReference = database.getReference("Modulo"+modulo);

        adapter = new MensajesDocenteAdapter(this.getContext());
        LinearLayoutManager l = new LinearLayoutManager(this.getContext());
        RecyclerViewMensajes.setLayoutManager(l);
        RecyclerViewMensajes.setAdapter(adapter);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEEE dd/MM (HH:mm)", Locale.getDefault());
        String fechaActual = dateFormat2.format(calendar.getTime());

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new Mensaje(cuadroMensaje.getText().toString(),fechaActual,sharedPreferences.getString("foto", null),sharedPreferences.getString("id_usuario", null)));
                cuadroMensaje.setText("");
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Mensaje m = snapshot.getValue(Mensaje.class);
                adapter.addMensaje(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageView regresar = rootView.findViewById(R.id.regresar);

        regresar.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(rootView);

            if (!navController.popBackStack()) {
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }


    private void setScrollBar(){
        RecyclerViewMensajes.scrollToPosition(adapter.getItemCount()-1);
    }

}