package com.famisalud.famisalud.ui.drugstore;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.famisalud.famisalud.R;

public class DrugstoreFragment extends Fragment {

   private DrugstoreViewModel mViewModel;

   public static DrugstoreFragment newInstance() {
      return new DrugstoreFragment();
   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_drugstore, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      mViewModel = new ViewModelProvider(this).get(DrugstoreViewModel.class);
      // TODO: Use the ViewModel
   }

}