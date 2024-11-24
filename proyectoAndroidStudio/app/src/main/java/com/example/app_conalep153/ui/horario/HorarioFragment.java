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
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class HorarioFragment extends Fragment {

    ArrayList<Modulo> moduloList;
    ModuloAdapter adapter;
    RequestQueue requestQueue;
    TextView mesAnioActual;
    ImageView regresar;
    TextView diaMesAnioActual;
    TextView diaLunesCalendario, diaMartesCalendario, diaMiercolesCalendario, diaJuevesCalendario, diaViernesCalendario;
    MaterialCardView LUN, MAR, MIE, JUE, VIE, mensajeSinResultado;

    public HorarioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horario, container, false);

        mesAnioActual = view.findViewById(R.id.mesAnioActual);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        String mesAnio = dateFormat.format(calendar.getTime());
        mesAnio = mesAnio.substring(0, 1).toUpperCase() + mesAnio.substring(1);
        mesAnioActual.setText(mesAnio);

        diaMesAnioActual = view.findViewById(R.id.diaMesAnioActual);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEEE dd, MMMM yyyy", Locale.getDefault());
        String fechaActual = dateFormat2.format(calendar.getTime());
        String[] partesFecha = fechaActual.split(" ");
        String dia = partesFecha[0];
        String mes = partesFecha[2];
        dia = dia.substring(0, 1).toUpperCase() + dia.substring(1).toLowerCase();
        mes = mes.substring(0, 1).toUpperCase() + mes.substring(1).toLowerCase();
        fechaActual = dia + " " + partesFecha[1] + " " + mes + " " + partesFecha[3];
        diaMesAnioActual.setText(fechaActual);

        diaLunesCalendario = view.findViewById(R.id.diaLunesCalendario);
        diaMartesCalendario = view.findViewById(R.id.diaMartesCalendario);
        diaMiercolesCalendario = view.findViewById(R.id.diaMiercolesCalendario);
        diaJuevesCalendario = view.findViewById(R.id.diaJuevesCalendario);
        diaViernesCalendario = view.findViewById(R.id.diaViernesCalendario);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int diaLunes = calendar.get(Calendar.DAY_OF_MONTH);
        diaLunesCalendario.setText(String.valueOf(diaLunes));

        for (int i = 1; i <= 4; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            int diaSiguiente = calendar.get(Calendar.DAY_OF_MONTH);
            switch (i) {
                case 1:
                    diaMartesCalendario.setText(String.valueOf(diaSiguiente));
                    break;
                case 2:
                    diaMiercolesCalendario.setText(String.valueOf(diaSiguiente));
                    break;
                case 3:
                    diaJuevesCalendario.setText(String.valueOf(diaSiguiente));
                    break;
                case 4:
                    diaViernesCalendario.setText(String.valueOf(diaSiguiente));
                    break;
            }
        }

        LUN = view.findViewById(R.id.LUN);
        LUN.setOnClickListener(v -> onDiaCardClicked("LUN"));
        MAR = view.findViewById(R.id.MAR);
        MAR.setOnClickListener(v -> onDiaCardClicked("MAR"));
        MIE = view.findViewById(R.id.MIE);
        MIE.setOnClickListener(v -> onDiaCardClicked("MIE"));
        JUE = view.findViewById(R.id.JUE);
        JUE.setOnClickListener(v -> onDiaCardClicked("JUE"));
        VIE = view.findViewById(R.id.VIE);
        VIE.setOnClickListener(v -> onDiaCardClicked("VIE"));

        regresar = view.findViewById(R.id.regresar);
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

        fetchActividadData("diaActual");

        mensajeSinResultado = view.findViewById(R.id.mensajeSinResultados);

        return view;
    }

    private void onDiaCardClicked(String diaSeleccionado) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd, MMMM yyyy", Locale.getDefault());
        switch (diaSeleccionado) {
            case "LUN":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                LUN.setCardBackgroundColor(android.graphics.Color.parseColor("#D0E6E2"));
                MAR.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                MIE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                JUE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                VIE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                break;
            case "MAR":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                LUN.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                MAR.setCardBackgroundColor(android.graphics.Color.parseColor("#D0E6E2"));
                MIE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                JUE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                VIE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                break;
            case "MIE":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                LUN.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                MAR.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                MIE.setCardBackgroundColor(android.graphics.Color.parseColor("#D0E6E2"));
                JUE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                VIE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                break;
            case "JUE":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                LUN.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                MAR.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                MIE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                JUE.setCardBackgroundColor(android.graphics.Color.parseColor("#D0E6E2"));
                VIE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                break;
            case "VIE":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                LUN.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                MAR.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                MIE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                JUE.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
                VIE.setCardBackgroundColor(android.graphics.Color.parseColor("#D0E6E2"));
                break;
        }


        String fechaSeleccionada = dateFormat.format(calendar.getTime());
        String[] partesFecha = fechaSeleccionada.split(" ");
        String dia = partesFecha[0];
        String mes = partesFecha[2];
        dia = dia.substring(0, 1).toUpperCase() + dia.substring(1).toLowerCase();
        mes = mes.substring(0, 1).toUpperCase() + mes.substring(1).toLowerCase();
        fechaSeleccionada = dia + " " + partesFecha[1] + " " + mes + " " + partesFecha[3];

        diaMesAnioActual.setText(fechaSeleccionada);

        moduloList.clear();
        adapter.notifyDataSetChanged();

        fetchActividadData(diaSeleccionado);
    }

    private void fetchActividadData(String dia) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String url = "http://192.168.137.1/ProyectoCONALEP153_AppMovil/codeigniter4-framework/public/api/modulo/usuario/"
                + sharedPreferences.getString("id_usuario", null) + "?diaSemana=" + dia;

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
                            mensajeSinResultado.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("Error procesando la respuesta");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error al realizar la solicitud");
                mensajeSinResultado.setVisibility(View.VISIBLE);
            }
        });

        requestQueue.add(request);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
