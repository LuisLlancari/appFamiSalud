package com.famisalud.famisalud;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.famisalud.famisalud.Adapter.MyApapterSede;
import com.famisalud.famisalud.Model.SedeClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class sedelist extends AppCompatActivity {

   private RecyclerView recyclerView;
   private DatabaseReference database;
   private MyApapterSede myAdapter;
   private ArrayList<SedeClass> list;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_sedelist);

      initializeViews();
      setupRecyclerView();

      database.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            list.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
               SedeClass sedeClass = dataSnapshot.getValue(SedeClass.class);
               list.add(sedeClass);
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
      recyclerView = findViewById(R.id.sedelist);
      database = FirebaseDatabase.getInstance().getReference("sedes");
      list = new ArrayList<>();
      myAdapter = new MyApapterSede(this, list);
      recyclerView.setAdapter(myAdapter);
   }

   private void setupRecyclerView() {
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
   }

   private void handleDatabaseError(DatabaseError error) {
      // Manejo de errores en caso de fallo en la lectura
   }
}
