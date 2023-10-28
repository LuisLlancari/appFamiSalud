
package com.famisalud.famisalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

   RecyclerView recyclerView;
   DatabaseReference database;
   MyApapterSede myApapterSede;
   ArrayList<SedeClass> list;

   Button btdialog;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_sedelist);

//      btdialog = findViewById(R.id.btndialogS);
//      btdialog.setOnApplyWindowInsetsListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            SweetAlertDialog pDialog = new SweetAlertDialog(con, SweetAlertDialog.PROGRESS_TYPE);
//            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pDialog.setTitleText("Loading");
//            pDialog.setCancelable(false);
//            pDialog.show();
//         }
//      });

      recyclerView = findViewById(R.id.sedelist);
      database = FirebaseDatabase.getInstance().getReference("sedes");
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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
