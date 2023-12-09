package com.famisalud.famisalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.famisalud.famisalud.Adapter.MyAdapterEspecialidad;
import com.famisalud.famisalud.Adapter.MyAdapterServicios;
import com.famisalud.famisalud.Model.EspecialidadClass;
import com.famisalud.famisalud.Model.ServicioClass;
import com.famisalud.famisalud.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Especialidad extends AppCompatActivity {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private DatabaseReference databaseEspecialidad;
    private MyAdapterEspecialidad myAdapter;
    private ArrayList<EspecialidadClass> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidad);
        initializeViews();
        setupRecyclerView();

        databaseEspecialidad.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    EspecialidadClass especialidadClass = dataSnapshot.getValue(EspecialidadClass.class);
                    list.add(especialidadClass);
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
        recyclerView = findViewById(R.id.especialidadList);
        databaseEspecialidad = FirebaseDatabase.getInstance().getReference("especialidades");
        list = new ArrayList<>();
        myAdapter = new MyAdapterEspecialidad(this, list);
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