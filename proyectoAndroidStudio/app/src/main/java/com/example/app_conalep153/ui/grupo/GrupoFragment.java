package com.example.app_conalep153.ui.grupo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_conalep153.R;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GrupoFragment extends Fragment {
    ArrayList<Grupo> grupoList1, grupoList2;
    GrupoAdapter adapter1,adapter2;
    RequestQueue requestQueue;
    MaterialCardView mensajeSinResultados;


    public GrupoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grupo, container, false);

        ListView listView1 = view.findViewById(R.id.listaGrupos1);
        ListView listView2 = view.findViewById(R.id.listaGrupos2);
        grupoList1 = new ArrayList<>();
        grupoList2 = new ArrayList<>();
        adapter1 = new GrupoAdapter(getActivity(), grupoList1);
        adapter2 = new GrupoAdapter(getActivity(), grupoList2);
        requestQueue = Volley.newRequestQueue(getContext());

        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);

        fetchDocenteData();

        mensajeSinResultados = view.findViewById(R.id.mensajeSinResultados);

        return view;
    }

    private void fetchDocenteData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String url = "http://192.168.137.1/ProyectoCONALEP153_AppMovil/codeigniter4-framework/public/api/grupo/usuario/"+sharedPreferences.getString("id_usuario",null);
//        String url = "http://192.168.137.1/ProyectoCONALEP153_AppMovil/codeigniter4-framework/public/api/grupo/usuario/101";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject actividadJson = response.getJSONObject(i);

                                String nombre = actividadJson.getString("nombre");
                                String imagenGrupo = actividadJson.getString("imagenGrupo");
                                String username = actividadJson.getString("username");
                                String id_usuario = actividadJson.getString("id_usuario");
                                String rolUsuario = actividadJson.getString("rolUsuario");
                                String imagenUsuario = actividadJson.getString("imagenUsuario");


                                Grupo grupo = new Grupo(nombre,imagenGrupo,username,id_usuario,rolUsuario,imagenUsuario);

                                if(i%2==0){
                                    grupoList1.add(grupo);
                                }else{
                                    grupoList2.add(grupo);
                                }
                            }
                            adapter1.notifyDataSetChanged();
                            adapter2.notifyDataSetChanged();
                            mensajeSinResultados.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("Error procesando la respuesta");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mensajeSinResultados.setVisibility(View.VISIBLE);
            }
        });

        requestQueue.add(request);
    }



    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}