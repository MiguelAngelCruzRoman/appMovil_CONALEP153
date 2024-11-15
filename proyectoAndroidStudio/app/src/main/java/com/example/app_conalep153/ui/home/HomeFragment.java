package com.example.app_conalep153.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_conalep153.R;
import com.example.app_conalep153.ui.actividad.Actividad;
import com.example.app_conalep153.ui.actividad.ActividadAdapter;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Actividad> actividadList;
    ActividadAdapter adapter;
    RequestQueue requestQueue;
    LinearLayout tablaHorario;
    MaterialCardView fechaCalendario;
    MaterialCardView mensajeSinResultados;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ListView listView = view.findViewById(R.id.listaActividadesHome);
        actividadList = new ArrayList<>();
        adapter = new ActividadAdapter(getActivity(), actividadList);
        requestQueue = Volley.newRequestQueue(getContext());

        listView.setAdapter(adapter);

        fetchActividadData();

        tablaHorario = view.findViewById(R.id.tablaHorario);
        tablaHorario.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_horario);
        });

        fechaCalendario = view.findViewById(R.id.fechaCalendario);
        fechaCalendario.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_calendario);
        });

        mensajeSinResultados = view.findViewById(R.id.mensajeSinResultados);
        mensajeSinResultados.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_actividades);
        });

        return view;
    }

    private void fetchActividadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String url = "http://192.168.137.1/ProyectoCONALEP153_AppMovil/codeigniter4-framework/public/api/actividad/usuario/"+sharedPreferences.getString("id_usuario",null)+"?limit=2";
//        String url = "http://192.168.137.1/ProyectoCONALEP153_AppMovil/codeigniter4-framework/public/api/actividad/usuario/8?limit=2";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject actividadJson = response.getJSONObject(i);


                                String nombre = actividadJson.getString("nombre");
                                String descripcion = actividadJson.getString("descripcion");
                                String fecha = actividadJson.getString("fecha");
                                String imagen = actividadJson.getString("imagen");
                                String ubicacion = actividadJson.getString("ubicacion");

                                Actividad actividad = new Actividad(nombre, descripcion, ubicacion, imagen, fecha);

                                actividadList.add(actividad);
                            }

                            adapter.notifyDataSetChanged();
                            mensajeSinResultados.setVisibility(View.INVISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("Error procesando la respuesta");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error al realizar la solicitud");
                mensajeSinResultados.setVisibility(View.VISIBLE);
            }
        });

        requestQueue.add(request);
    }



    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}