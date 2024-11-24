package com.example.app_conalep153.ui.docente;

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

public class DocenteFragment extends Fragment {

    ArrayList<Docente> docenteList1, docenteList2;
    DocenteAdapter adapter1,adapter2;
    RequestQueue requestQueue;
    MaterialCardView mensajeSinResultados;


    public DocenteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docente, container, false);

        ListView listView1 = view.findViewById(R.id.listaDocentes1);
        ListView listView2 = view.findViewById(R.id.listaDocentes2);
        docenteList1 = new ArrayList<>();
        docenteList2 = new ArrayList<>();
        adapter1 = new DocenteAdapter(getActivity(), docenteList1);
        adapter2 = new DocenteAdapter(getActivity(), docenteList2);
        requestQueue = Volley.newRequestQueue(getContext());

        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);

        fetchDocenteData();

        mensajeSinResultados = view.findViewById(R.id.mensajeSinResultados);

        return view;
    }

    private void fetchDocenteData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String url = "http://192.168.137.1/ProyectoCONALEP153_AppMovil/codeigniter4-framework/public/api/docente/usuario/"+sharedPreferences.getString("id_usuario",null);
//        String url = "http://192.168.137.1/ProyectoCONALEP153_AppMovil/codeigniter4-framework/public/api/docente/usuario/101";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject actividadJson = response.getJSONObject(i);


                                String id_docente = actividadJson.getString("id_docente");
                                String nombre = actividadJson.getString("nombre");
                                String foto = actividadJson.getString("foto");
                                String modulo = actividadJson.getString("modulo");

                                Docente docente = new Docente(id_docente,nombre,foto,modulo);

                                if(i%2==0){
                                    docenteList1.add(docente);
                                }else{
                                    docenteList2.add(docente);
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