
package com.famisalud.famisalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class sedelist extends AppCompatActivity {

   RecyclerView recyclerView;
   DatabaseReference database;
   MyApapterSede myApapterSede;
   ArrayList<SedeClass> list;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_sedelist);

      recyclerView = findViewById(R.id.sedelist);
      database = FirebaseDatabase.getInstance().getReference("sedes");
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

      list = new ArrayList<>();
      myApapterSede = new MyApapterSede(this, list);
      recyclerView.setAdapter(myApapterSede);

      database.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            list.clear(); // Limpia la lista antes de agregar nuevos datos
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
               SedeClass sedeClass = dataSnapshot.getValue(SedeClass.class);
               list.add(sedeClass);
            }
            myApapterSede.notifyDataSetChanged(); // Notifica al adaptador de los cambios en los datos
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            // Manejo de errores en caso de fallo en la lectura
         }
      });
   }
}
