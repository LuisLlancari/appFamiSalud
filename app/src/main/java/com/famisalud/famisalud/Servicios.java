package com.famisalud.famisalud;

import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.famisalud.famisalud.Adapter.MyAdapterServicios;
import com.famisalud.famisalud.Model.ServicioClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.*;
import java.util.*;

public class Servicios extends AppCompatActivity {
    private Spinner spSedesDD;
    private LinearLayout linearLayout;
    private List<String> sedesList = new ArrayList<>();
    private ArrayAdapter<String> sedesAdapter;

    private RecyclerView recyclerView;
    private DatabaseReference database;
    private MyAdapterServicios myAdapter;
    private ArrayList<ServicioClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        // Inicializa las vistas
        initializeViews();
        setupRecyclerView();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){

                        String valor = childSnapshot.child("Servicios  ").getValue(String.class);

                    }
                }else {}

                /*for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ServicioClass servicio = dataSnapshot.getValue(ServicioClass.class);
                    list.add(servicio);
                }
                myAdapter.notifyDataSetChanged();*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                handleDatabaseError(error);
            }
        });
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.servicioList);
        database = FirebaseDatabase.getInstance().getReference("Servicios");

        list = new ArrayList<>();
        myAdapter = new MyAdapterServicios(this, list);



        recyclerView.setAdapter(myAdapter);
    }

    private void setupRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
    private void handleDatabaseError(DatabaseError error) {
        // Manejo de errores en caso de fallo en la lectura
        notificar("ERROR CON LA BASE DE DATOS");
    }

    private void notificar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
    /*private Spinner spSedesDD;

    private LinearLayout linearLayout;
    private List<String> sedesList = new ArrayList<>();
    private ArrayAdapter<String> sedesAdapter;

    private RecyclerView recyclerView;
    private DatabaseReference database;
    private MyAdapterServicios  myAdapter;
    private ArrayList<ServicioClass> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        //getSupportActionBar().hide();
        initializeViews();
        setupRecyclerView();




        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ServicioClass servicio = dataSnapshot.getValue(ServicioClass.class);
                    list.add(servicio);

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                handleDatabaseError(error);
            }
        });
    }

    private  void initializeViews(){
        recyclerView = findViewById(R.id.servicioList);
        database = FirebaseDatabase.getInstance().getReference("Servicios");

        list =  new ArrayList<>();
        myAdapter = new MyAdapterServicios(this,list);
        recyclerView.setAdapter(myAdapter);

    }


    private void setupRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    private void handleDatabaseError(DatabaseError error) {
        // Manejo de errores en caso de fallo en la lectura
        notificar("ERROR CON LA BASE DE DATOS");
    }

    private void notificar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }*/

    /*private void loadUI() {
        spSedesDD = findViewById(R.id.spSpinnerSedes);
        prueba = findViewById(R.id.etprueba);
        btPrueba = findViewById(R.id.btPulsar);
        linearLayout = findViewById(R.id.linearContainer);

        // Inicializar el adaptador de Spinner para mostrar sedes
        sedesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sedesList);
        sedesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSedesDD.setAdapter(sedesAdapter);
    }*/
}

