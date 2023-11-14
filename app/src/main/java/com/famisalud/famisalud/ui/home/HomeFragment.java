package com.famisalud.famisalud.ui.home;

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
import com.famisalud.famisalud.Adapter.MyApapterSede;
import com.famisalud.famisalud.Model.MetodoPagoClass;
import com.famisalud.famisalud.Model.SedeClass;
import com.famisalud.famisalud.databinding.FragmentHomeBinding;
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
   private RecyclerView recyclerView, recyclerViewSede;
   private DatabaseReference databaseMetodoPago, databaseSede;
   private MyAdapterMetodoPago myAdapter;
   private MyApapterSede myAdapterSede;
   private ArrayList<MetodoPagoClass> list;
   private ArrayList<SedeClass> listSede;

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

//      val bottomsheet = BottomSheetFragment();
//      BottomSheetFragment bottomSheet = new BottomSheetFragment();


//      binding.btnShow.setOnClickListener(v -> bottomSheet.show(requireActivity().getSupportFragmentManager(), "BottomSheetDialog"));

      databaseMetodoPago.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            list.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
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
      databaseSede.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            listSede.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
               SedeClass sedeClass = dataSnapshot.getValue(SedeClass.class);
               listSede.add(sedeClass);
            }
            myAdapterSede.notifyDataSetChanged();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
      });

      return root;
   }

   private void handleDatabaseError(DatabaseError error) {
      // Manejo de errores en caso de fallo en la lectura
   }

   private void initializeViews() {
      recyclerView = binding.metodosPagoList;
      databaseMetodoPago = FirebaseDatabase.getInstance().getReference("metodosPago");
      list = new ArrayList<>();
      myAdapter = new MyAdapterMetodoPago(requireActivity(), list);
      recyclerView.setAdapter(myAdapter);

      recyclerViewSede = binding.sedelist;
      databaseSede = FirebaseDatabase.getInstance().getReference("sedes");
      listSede = new ArrayList<>();
      myAdapterSede = new MyApapterSede(this, listSede);
      recyclerViewSede.setAdapter(myAdapterSede);
   }

   private void setupRecyclerView() {
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

      recyclerViewSede.setHasFixedSize(true);
      recyclerViewSede.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

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

   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      binding = null;
   }
}
