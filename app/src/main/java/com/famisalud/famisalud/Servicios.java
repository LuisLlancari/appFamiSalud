package com.famisalud.famisalud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Servicios extends AppCompatActivity {
    Spinner SpSedesDD;
    EditText prueba;
    Button btPrueba;

    ArrayAdapter<CharSequence> adapter;
    final String URL = "http://192.168.137.217/webserviceFS/controller/servicio.controller.php";
    //URL2 =
    final String URL2 = "http://192.168.137.217/webserviceFS/controllers/sede.controller.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        List<String> sedes = new ArrayList<>();

        loadUI();
        btPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarWS();
            }
        });
    }
    private void enviarWS(){

        // PASO 1. Necesiamos un tipo de objeto para la comunicacion
        // El onResponse = cuando la comunicacion se logra conectar
        // Response.ErrorListener = error comunication
        StringRequest request = new StringRequest(Request.Method.GET, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                notificar("Datos obtenidos: " + response);
                // procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notificar("no hay comunicacion con el servidor");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Enviar los datos
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("operacion", "listar");
                return parametros   ;
            }
        };

        //Enviar la solicitud al WS
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void procesarRespuesta(String response) {
        notificar("Datos obtenidos: " + response);

        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String dato = jsonObject.getString("sedes"); // Reemplaza con el nombre del dato en tu JSON
                prueba.setText(dato);// Realiza lo que desees con el dato, como agregarlo a una lista o mostrarlo en una vista


            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }


    private void loadUI(){
      SpSedesDD = findViewById(R.id.spSpinnerSedes);
      prueba = findViewById(R.id.etprueba);
      btPrueba = findViewById(R.id.btPulsar);
    };
}