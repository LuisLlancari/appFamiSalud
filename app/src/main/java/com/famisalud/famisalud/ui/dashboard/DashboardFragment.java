package com.famisalud.famisalud.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.famisalud.famisalud.Especialidad;
import com.famisalud.famisalud.Famicard;

import com.famisalud.famisalud.Servicios;
import com.famisalud.famisalud.databinding.FragmentDashboardBinding;

import com.famisalud.famisalud.ui.drugstore.DrugstoreFragment;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        binding.catservice.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), Servicios.class);
//            startActivity(intent);
//        });
//
//        binding.catespecialidad.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), Especialidad.class);
//            startActivity(intent);
//        });
//
//        binding.catfarmacia.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), DrugstoreFragment.class);
//            startActivity(intent);
//        });
//
//        binding.catfamicard.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), Famicard.class);
//            startActivity(intent);
//        });

//        final TextView textView = binding.titulocat;
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}