package com.famisalud.famisalud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
      ImageView urlImage;

      public MyViewHolder(@NonNull View itemView) {
         super(itemView);

         sede = itemView.findViewById(R.id.tvnombreSede);
         direccion = itemView.findViewById(R.id.tvdireccionSede);
         correo = itemView.findViewById(R.id.tvcorreoSede);
         whatsapp = itemView.findViewById(R.id.tvwhatsappSede);
         urlImage = itemView.findViewById(R.id.ivSedeImage);
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
//      holder.urlImage.setText(sede.getUrl());

      // Carga la imagen usando Glide
      Glide.with(context)
          .load(sede.getUrl()) // URL de la imagen
          .placeholder(com.denzcoskun.imageslider.R.drawable.default_placeholder) // Imagen de marcador de posición
          .error(R.drawable.image_error) // Imagen en caso de error
          .into(holder.urlImage); // ImageView donde se mostrará la imagen


   }

   @Override
   public int getItemCount() {
      return list.size();
   }

}

