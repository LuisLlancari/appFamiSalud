package com.famisalud.famisalud.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.famisalud.famisalud.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

   private HomeViewModel viewModel;
   private FragmentHomeBinding binding;

   public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
      HomeViewModel homeViewModel =
          new ViewModelProvider(this).get(HomeViewModel.class);

      binding = FragmentHomeBinding.inflate(inflater, container, false);
      View root = binding.getRoot();

      final TextView textView = binding.textHome;
      homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

      //viewModel = new HomeViewModel();
      setupUI();
      return root;
   }

   private void setupUI() {
      ImageSlider imageSlider = binding.imageSlider;

      HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
      List<SlideModel> slideModelsList = viewModel.getSlideModelsList();

      // Configura el ImageSlider
      imageSlider.setImageList(slideModelsList);
   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      binding = null;
   }
}