package com.example.app_conalep153.ui.horario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.app_conalep153.R;


public class HorarioFragment extends Fragment {


    public HorarioFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horario, container, false);

        ImageView regresar = view.findViewById(R.id.regresar);

        regresar.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);

            if (!navController.popBackStack()) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}