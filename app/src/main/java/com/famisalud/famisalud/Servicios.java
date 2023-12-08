package com.famisalud.famisalud;

import android.os.Bundle;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.famisalud.famisalud.Adapter.MyAdapterServicios;

import com.famisalud.famisalud.Model.ServicioClass;
import com.famisalud.famisalud.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

public class Servicios extends AppCompatActivity {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private DatabaseReference databaseServicio;
    private MyAdapterServicios myAdapter;
    private ArrayList<ServicioClass> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        // Inicializa las vistas
        initializeViews();
        setupRecyclerView();

        databaseServicio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ServicioClass servicioClass = dataSnapshot.getValue(ServicioClass.class);
                    list.add(servicioClass);
                }
                myAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                handleDatabaseError(error);
            }
        });

    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.servicioList);
        databaseServicio = FirebaseDatabase.getInstance().getReference("Servicios");
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

}

