package com.example.app_conalep153.ui.grupo;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class GrupoChatFragment extends Fragment {
    private RecyclerView RecyclerViewMensajes;
    private ImageView btnEnviarMensaje;
    private TextInputEditText cuadroMensaje;

    private MensajesGrupoAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    public GrupoChatFragment() {
    }

    public static GrupoChatFragment newInstance(String nombreGrupo, String imagenGrupo, String username, String id_usuario, String rolUsuario, String imagenUsuario) {
        GrupoChatFragment fragment = new GrupoChatFragment();
        Bundle args = new Bundle();
        args.putString("nombreGrupo", nombreGrupo);
        args.putString("imagenGrupo", imagenGrupo);
        args.putString("username", username);
        args.putString("id_usuario", id_usuario);
        args.putString("rolUsuario", rolUsuario);
        args.putString("imagenUsuario", imagenUsuario);
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
        View rootView = inflater.inflate(R.layout.fragment_grupo_chat, container, false);

        RecyclerViewMensajes = rootView.findViewById(R.id.RecyclerViewMensajes);
        btnEnviarMensaje = rootView.findViewById(R.id.enviarMensaje);
        cuadroMensaje = rootView.findViewById(R.id.textFieldMensaje);

        String grupo = "";
        Bundle args = getArguments();
        if (args != null) {
            grupo = args.getString("nombreGrupo");
            TextView nombreGrupo = rootView.findViewById(R.id.nombreGrupo);
            nombreGrupo.setText(grupo);
        }

        database = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        databaseReference = database.getReference("Grupo"+grupo);

        adapter = new MensajesGrupoAdapter(this.getContext());
        LinearLayoutManager l = new LinearLayoutManager(this.getContext());
        RecyclerViewMensajes.setLayoutManager(l);
        RecyclerViewMensajes.setAdapter(adapter);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEEE dd/MM (HH:mm)", Locale.getDefault());
        String fechaActual = dateFormat2.format(calendar.getTime());

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new Mensaje(cuadroMensaje.getText().toString(),fechaActual,sharedPreferences.getString("foto", null),sharedPreferences.getString("id_usuario", null),args.getString("username"),sharedPreferences.getString("rol", null)));
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