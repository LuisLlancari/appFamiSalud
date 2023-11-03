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
import com.famisalud.famisalud.Model.MetodoPagoClass;
import com.famisalud.famisalud.R;

import java.util.ArrayList;

public class MyAdapterMetodoPago extends RecyclerView.Adapter<MyAdapterMetodoPago.MyViewHolder> {

   Context context;
   ArrayList<MetodoPagoClass> list;

   public  MyAdapterMetodoPago(Context context, ArrayList<MetodoPagoClass>list){
      this.context = context;
      this.list = list;
   }
   public class MyViewHolder extends RecyclerView.ViewHolder{
      TextView metodo,cci, numeroCuenta;
      ImageView URLImage;
      public MyViewHolder(@NonNull View itemView) {
         super(itemView);
         metodo = itemView.findViewById(R.id.tvbancoMetodoPago);
         cci = itemView.findViewById(R.id.tvcciMetodoPago);
         numeroCuenta = itemView.findViewById(R.id.tvnumeroMetodoPago);
         URLImage = itemView.findViewById(R.id.ivMetodoPagoImage);
      }
   }

   @NonNull
   @Override
   public MyAdapterMetodoPago.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(context).inflate(R.layout.activity_item_metodo_pago,parent,false);
      return new MyViewHolder(v);
   }

   @Override
   public void onBindViewHolder(@NonNull MyAdapterMetodoPago.MyViewHolder holder, int position) {
      MetodoPagoClass metodoPago = list.get(position);
      holder.metodo.setText(metodoPago.getMetodo());
      holder.cci.setText(metodoPago.getCci());
      holder.numeroCuenta.setText(metodoPago.getNumeroCuenta());

      Glide.with(context)
          .load(metodoPago.getURLImage())
          .placeholder(com.denzcoskun.imageslider.R.drawable.default_error)
          .error(R.drawable.image_error)
          .into(holder.URLImage);
   }

   @Override
   public int getItemCount() {
      return list.size();
   }

}
