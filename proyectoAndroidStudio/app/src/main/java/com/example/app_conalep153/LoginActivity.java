package com.example.app_conalep153;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextInputLayout textFieldUsuarname, textFieldPassword;
    TextInputEditText inputUsername, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textFieldUsuarname = findViewById(R.id.textFieldUsuarname);
        textFieldPassword = findViewById(R.id.textFieldPassword);

        inputUsername = (TextInputEditText) textFieldUsuarname.getEditText();
        inputPassword = (TextInputEditText) textFieldPassword.getEditText();


        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
//            loginUser();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("nombre", "YoMero");
            intent.putExtra("rol", "ADMINISTRADOR");
            intent.putExtra("foto", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDHbanfC3zg8tSrZEA8_373O_8Qm5jujM2Mw&s");
            inputUsername.setText("");
            inputPassword.setText("");
            startActivity(intent);
            finish();
        });
    }

    private void loginUser() {
        String url = "http://192.168.137.1/ProyectoCONALEP153_AppMovil/codeigniter4-framework/public/api/usuario/login";

        String username = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("username", username);
            loginData.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, loginData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nombre = response.getString("nombre");
                            String rol = response.getString("rol");
                            String foto = response.getString("foto");

                            Toast.makeText(LoginActivity.this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("nombre", nombre);
                            intent.putExtra("rol", rol);
                            intent.putExtra("foto", foto);
                            inputUsername.setText("");
                            inputPassword.setText("");

                            startActivity(intent);
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            String errorMessage = new String(error.networkResponse.data);

                            Log.e("LoginError", "Código de estado: " + statusCode);
                            Log.e("LoginError", "Mensaje de error: " + errorMessage);

                            if (statusCode == 401) {
                                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Error de autenticación: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Error de autenticación: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        inputUsername.setText("");
                        inputPassword.setText("");
                    }
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
