package com.famisalud.famisalud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyApapterSede extends RecyclerView.Adapter<MyApapterSede.MyViewHolder> {

   Context context;
   ArrayList<SedeClass> list;

   public MyApapterSede(Context context, ArrayList<SedeClass> list) {
      this.context = context;
      this.list = list;
   }

   public static class MyViewHolder extends RecyclerView.ViewHolder {
      TextView sede, direccion, correo, whatsapp;

      public MyViewHolder(@NonNull View itemView) {
         super(itemView);

         sede = itemView.findViewById(R.id.tvnombreSede);
         direccion = itemView.findViewById(R.id.tvdireccionSede);
         correo = itemView.findViewById(R.id.tvcorreoSede);
         whatsapp = itemView.findViewById(R.id.tvwhatsappSede);
      }
   }

   @NonNull
   @Override
   public MyApapterSede.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.itemsede, parent, false);
      return new MyViewHolder(v);
   }

   @Override
   public void onBindViewHolder(@NonNull MyApapterSede.MyViewHolder holder, int position) {
      SedeClass sede = list.get(position);
      holder.sede.setText(sede.getSede());
      holder.direccion.setText(sede.getDireccion());
      holder.correo.setText(sede.getCorreo());
      holder.whatsapp.setText(sede.getWhatsapp());

   }

   @Override
   public int getItemCount() {
      return list.size();
   }

}

