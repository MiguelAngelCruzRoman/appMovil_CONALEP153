package com.example.app_conalep153.ui.horario;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_conalep153.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HorarioFragment extends Fragment {

    ArrayList<Modulo> moduloList;
    ModuloAdapter adapter;
    RequestQueue requestQueue;

    public HorarioFragment() {
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

        ListView listView = view.findViewById(R.id.listaHorario);
        moduloList = new ArrayList<>();
        adapter = new ModuloAdapter(getActivity(), moduloList);
        requestQueue = Volley.newRequestQueue(getContext());

        listView.setAdapter(adapter);

        fetchActividadData();

        return view;
    }

    private void fetchActividadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String url = "http://192.168.137.1/ProyectoCONALEP153_AppMovil/codeigniter4-framework/public/api/modulo/usuario/"+sharedPreferences.getString("id_usuario",null);
//        String url = "http://192.168.137.1/ProyectoCONALEP153_AppMovil/codeigniter4-framework/public/api/modulo/usuario/8";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject actividadJson = response.getJSONObject(i);


                                String tipoFormacion = actividadJson.getString("tipoFormacion");
                                String horasClase = actividadJson.getString("horasClase");
                                String nombreModulo = actividadJson.getString("nombreModulo");
                                String nombreDocente = actividadJson.getString("nombreDocente");
                                String salonClase = actividadJson.getString("salonClase");

                                Modulo modulo = new Modulo(tipoFormacion, horasClase, nombreModulo, nombreDocente, salonClase);

                                moduloList.add(modulo);
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("Error procesando la respuesta");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error al realizar la solicitud");

            }
        });

        requestQueue.add(request);
    }



    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}