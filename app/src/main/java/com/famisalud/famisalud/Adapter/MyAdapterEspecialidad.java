package com.famisalud.famisalud.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.famisalud.famisalud.Model.EspecialidadClass;
import com.famisalud.famisalud.Model.ServicioClass;
import com.famisalud.famisalud.R;


import java.util.ArrayList;

public class MyAdapterEspecialidad extends RecyclerView.Adapter<MyAdapterEspecialidad.MyViewHolder> {
    Context context;

    ArrayList<EspecialidadClass> list;

    public MyAdapterEspecialidad(Context context, ArrayList<EspecialidadClass> list) {
        this.context = context;
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView especialidad, precio;

        ImageView urlImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            especialidad = itemView.findViewById(R.id.tvNombreEspecialidad);
            precio = itemView.findViewById(R.id.tvprecioEspecialidad);
            urlImage = itemView.findViewById(R.id.ivEspecialidad);

        }
    }
    @NonNull
    @Override
    public MyAdapterEspecialidad.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemespecialidad,parent, false);
        return  new MyAdapterEspecialidad.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterEspecialidad.MyViewHolder holder, int position) {
        EspecialidadClass vespecialidad = list.get(position);
        holder.especialidad.setText(vespecialidad.getEspecialidad());
        holder.precio.setText(vespecialidad.getPrecio().toString());


        // Carga la imagen usando Glide
        Glide.with(context)
                .load(vespecialidad.getUrl()) // URL de la imagen
                .placeholder(com.denzcoskun.imageslider.R.drawable.default_placeholder) // Imagen de marcador de posición
                .error(R.drawable.image_error) // Imagen en caso de error
                .into(holder.urlImage); // ImageView donde se mostrará la imagen


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
