package com.famisalud.famisalud.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.famisalud.famisalud.Adapter.MyAdapterMetodoPago;
import com.famisalud.famisalud.Model.MetodoPagoClass;
import com.famisalud.famisalud.databinding.FragmentHomeBinding;
import com.famisalud.famisalud.sedelist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
   private FragmentHomeBinding binding;
   private RecyclerView recyclerView;
   private DatabaseReference database;
   private MyAdapterMetodoPago myAdapter;
   private ArrayList<MetodoPagoClass> list;

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
      binding = FragmentHomeBinding.inflate(inflater, container, false);
      View root = binding.getRoot();
      final TextView textView = binding.textHome;
      homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

      initializeViews();
      setupRecyclerView();
      setupUI();
      setupDialogButton();
      setupRecyclerViewButton();

      database.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            list.clear();
            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
               MetodoPagoClass metodoPagoClass = dataSnapshot.getValue(MetodoPagoClass.class);
               list.add(metodoPagoClass);
            }
            myAdapter.notifyDataSetChanged();

         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            handleDatabaseError(error);
         }
      });

      return root;
   }

   private void handleDatabaseError(DatabaseError error) {
      // Manejo de errores en caso de fallo en la lectura
   }

   private void initializeViews() {
      recyclerView = binding.metodosPagoList;
      database = FirebaseDatabase.getInstance().getReference("metodosPago");
      list = new ArrayList<>();
      myAdapter = new MyAdapterMetodoPago(requireActivity(),list);
      recyclerView.setAdapter(myAdapter);
   }

   private void setupRecyclerView() {
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));

   }

   private void setupUI() {
      ImageSlider imageSlider = binding.imageSlider;
      HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
      List<SlideModel> slideModelsList = viewModel.getSlideModelsList();
      imageSlider.setImageList(slideModelsList);
   }

   private void setupDialogButton() {
      binding.btnDialog.setOnClickListener(v -> {
         SweetAlertDialog dialog = new SweetAlertDialog(requireActivity(), SweetAlertDialog.SUCCESS_TYPE);
         dialog.setTitleText("Exitoso");
         dialog.show();
      });
   }

   private void setupRecyclerViewButton() {
      binding.btnIrReciclerView.setOnClickListener(v -> startActivity(new Intent(requireContext(), sedelist.class)));
   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      binding = null;
   }
}
