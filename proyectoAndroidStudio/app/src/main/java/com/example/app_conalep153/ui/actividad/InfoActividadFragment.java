package com.example.app_conalep153.ui.actividad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_conalep153.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;  // Importar Picasso

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InfoActividadFragment extends Fragment {
    private RecyclerView RecyclerViewMensajes;
    private ImageView btnEnviarMensaje;
    private TextInputEditText cuadroMensaje;

    private MensajesActividadAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

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

        RecyclerViewMensajes = rootView.findViewById(R.id.RecyclerViewMensajes);
        btnEnviarMensaje = rootView.findViewById(R.id.enviarMensaje);
        cuadroMensaje = rootView.findViewById(R.id.textFieldMensaje);


        Bundle args = getArguments();
        String nombre ="";
        if (args != null) {
            nombre = args.getString("nombre");
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

        database = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        databaseReference = database.getReference("Actividad"+nombre);

        adapter = new MensajesActividadAdapter(this.getContext());
        LinearLayoutManager l = new LinearLayoutManager(this.getContext());
        RecyclerViewMensajes.setLayoutManager(l);
        RecyclerViewMensajes.setAdapter(adapter);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEEE dd/MM (HH:mm)", Locale.getDefault());
        String fechaActual = dateFormat2.format(calendar.getTime());

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new Mensaje(cuadroMensaje.getText().toString(),fechaActual,sharedPreferences.getString("foto", null),sharedPreferences.getString("id_usuario", null),sharedPreferences.getString("nombre", null)));
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


        return rootView;
    }

    private void setScrollBar(){
        RecyclerViewMensajes.scrollToPosition(adapter.getItemCount()-1);
    }

}
