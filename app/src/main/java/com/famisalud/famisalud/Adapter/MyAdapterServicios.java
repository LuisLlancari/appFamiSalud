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
import com.famisalud.famisalud.Model.ServicioClass;
import com.famisalud.famisalud.R;
import com.famisalud.famisalud.Servicios;

import java.util.ArrayList;

public class MyAdapterServicios extends RecyclerView.Adapter<MyAdapterServicios.MyViewHolder> {
    Context context;

    ArrayList<ServicioClass> list;

    public MyAdapterServicios(Context context, ArrayList<ServicioClass> list) {
        this.context = context;
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView servicio, fecha_creacion, precio;

        ImageView urlImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            servicio = itemView.findViewById(R.id.tvNombreServicio);
            fecha_creacion = itemView.findViewById(R.id.tvFechaServicio);
            precio = itemView.findViewById(R.id.tvprecioServicio);
            urlImage = itemView.findViewById(R.id.ivServicios);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cl,parent, false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ServicioClass vservicio = list.get(position);
        holder.servicio.setText(vservicio.getServicio());
        holder.fecha_creacion.setText(vservicio.getFecha_creacion());
        holder.precio.setText(vservicio.getPrecio().toString());


        // Carga la imagen usando Glide
        Glide.with(context)
                .load(vservicio.getUrl()) // URL de la imagen
                .placeholder(com.denzcoskun.imageslider.R.drawable.default_placeholder) // Imagen de marcador de posición
                .error(R.drawable.image_error) // Imagen en caso de error
                .into(holder.urlImage); // ImageView donde se mostrará la imagen


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
