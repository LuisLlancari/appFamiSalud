package com.famisalud.famisalud;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.*;
import java.util.*;

public class Servicios extends AppCompatActivity {
    private Spinner spSedesDD;
    private EditText prueba;
    private Button btPrueba;
    private LinearLayout linearLayout;
    private final String URL = "http://192.168.137.217/webserviceFS/controller/servicio.controller.php";
    private final String URL2 = "http://192.168.137.217/webserviceFS/controllers/sede.controller.php";
    private List<String> sedesList = new ArrayList<>();
    private ArrayAdapter<String> sedesAdapter;
    private RequestQueue requestQueue;
    private SedeDatabase sedeDatabase;

//    private Sede sede;
//    private SedeDao sedeDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        getSupportActionBar().hide();

        loadUI();
        // Inicializa la base de datos y el DAO
        sedeDatabase = Room.databaseBuilder(getApplicationContext(), SedeDatabase.class, "sede_database").build();
        setupListeners();
        requestQueue = Volley.newRequestQueue(this);
//        sedeDatabase = Sede.getInstance(this);

        // Cargar datos de la base de datos local (si existen)
        loadSedesFromDatabase();
        inflateCL1Layouts(3);

        // Realizar la solicitud web
        enviarWS();


//        sedeDao = sedeDatabase.sedeDao();
    }

    private void setupListeners() {
        btPrueba.setOnClickListener(v -> enviarWS());
    }

    private void inflateCL1Layouts(int count) {
        for (int i = 0; i < count; i++) {
            View cl1 = LayoutInflater.from(this).inflate(R.layout.cl, null);
            linearLayout.addView(cl1);
        }
    }

    private void enviarWS() {
        StringRequest request = new StringRequest(Request.Method.GET, URL2, response -> {
            notificar("Datos obtenidos: " + response);
            procesarRespuesta(response);
        }, error -> {
            notificar("No hay comunicación con el servidor");
            // Intenta cargar datos desde la base de datos local en caso de error de red
            loadSedesFromDatabase();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("operacion", "listar");
                return parametros;
            }
        };

        requestQueue.add(request);
    }

    private void procesarRespuesta(String response) {
        notificar("Datos obtenidos: " + response);

        try {
            JSONArray jsonArray = new JSONArray(response);

            // Limpiar la lista de sedes antes de agregar nuevos datos
            sedesList.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String sede = jsonObject.getString("sedes");
                sedesList.add(sede);
            }

            // Actualizar la interfaz de usuario con las sedes
            sedesAdapter.notifyDataSetChanged();

            // Guardar los datos en la base de datos local
            saveSedesToDatabase(sedesList);

        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void loadSedesFromDatabase() {
        // Cargar datos desde la base de datos local
        new Thread(() -> {
            List<Sede> sedes = sedeDatabase.sedeDao().getAllSedes();
            sedesList.clear();
            for (Sede sede : sedes) {
                sedesList.add(sede.getName());
            }

            runOnUiThread(() -> sedesAdapter.notifyDataSetChanged());
        }).start();
    }

    private void saveSedesToDatabase(List<String> sedes) {
        // Guardar los datos en la base de datos local
        new Thread(() -> {
            for (String sedeName : sedes) {
                Sede sede = new Sede(sedeName);
                sedeDatabase.sedeDao().insert(sede);
            }
        }).start();
    }

    private void notificar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void loadUI() {
        spSedesDD = findViewById(R.id.spSpinnerSedes);
        prueba = findViewById(R.id.etprueba);
        btPrueba = findViewById(R.id.btPulsar);
        linearLayout = findViewById(R.id.linearContainer);

        // Inicializar el adaptador de Spinner para mostrar sedes
        sedesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sedesList);
        sedesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSedesDD.setAdapter(sedesAdapter);
    }
}


//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.widget.ConstraintSet;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Servicios extends AppCompatActivity {
//    Spinner SpSedesDD;
//    EditText prueba;
//    Button btPrueba;
//
//    LinearLayout linearLayout;
//
//    ArrayAdapter<CharSequence> adapter;
//    final String URL = "http://192.168.137.217/webserviceFS/controller/servicio.controller.php";
//    //URL2 =
//    final String URL2 = "http://192.168.137.217/webserviceFS/controllers/sede.controller.php";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_servicios);
//        getSupportActionBar().hide();
//        List<String> sedes = new ArrayList<>();
//
//        loadUI();
//        btPrueba.setOnClickListener(v -> enviarWS());
//
//        for (int i = 0; i < 3; i++){
//            View cl1 = LayoutInflater.from(this).inflate(R.layout.cl,null);
//            linearLayout.addView(cl1);
//        }
//    }
//    private void enviarWS(){
//
//        // PASO 1. Necesiamos un tipo de objeto para la comunicacion
//        // El onResponse = cuando la comunicacion se logra conectar
//        // Response.ErrorListener = error comunication
//        StringRequest request = new StringRequest(Request.Method.GET, URL2, response -> {
//            notificar("Datos obtenidos: " + response);
//            // procesarRespuesta(response);
//        }, error -> notificar("no hay comunicacion con el servidor")){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() {
//                //Enviar los datos
//                Map<String,String> parametros = new HashMap<>();
//                parametros.put("operacion", "listar");
//                return parametros   ;
//            }
//        };
//
//        //Enviar la solicitud al WS
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
//
//    private void procesarRespuesta(String response) {
//        notificar("Datos obtenidos: " + response);
//
//        try {
//            JSONArray jsonArray = new JSONArray(response);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String dato = jsonObject.getString("sedes"); // Reemplaza con el nombre del dato en tu JSON
//                prueba.setText(dato);// Realiza lo que desees con el dato, como agregarlo a una lista o mostrarlo en una vista
//
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    private void notificar(String mensaje){
//        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
//    }
//
//
//    private void loadUI(){
//      SpSedesDD = findViewById(R.id.spSpinnerSedes);
//      prueba = findViewById(R.id.etprueba);
//      btPrueba = findViewById(R.id.btPulsar);
//
//      linearLayout = findViewById(R.id.linearContainer);
//    };
//}