//package com.famisalud.famisalud.ui.home;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.denzcoskun.imageslider.ImageSlider;
//import com.denzcoskun.imageslider.models.SlideModel;
//import com.famisalud.famisalud.R;
//import com.famisalud.famisalud.databinding.FragmentHomeBinding;
//
//import java.util.List;
//
//public class HomeFragment extends Fragment {
//
//   private HomeViewModel viewModel;
//   private FragmentHomeBinding binding;
//
//   public View onCreateView(@NonNull LayoutInflater inflater,
//                            ViewGroup container, Bundle savedInstanceState) {
//      HomeViewModel homeViewModel =
//          new ViewModelProvider(this).get(HomeViewModel.class);
//
//      binding = FragmentHomeBinding.inflate(inflater, container, false);
//      View root = binding.getRoot();
//
//      final TextView textView = binding.textHome;
//      homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//
//      //viewModel = new HomeViewModel();
//      setupCardViews();
//      setupUI();
//      return root;
//   }
//
//   private void setupCardViews() {
//      // Obtén una referencia al LinearLayout que contiene los CardViews
//      LinearLayout cardContainer = root.findViewById(R.id.cardContainer);
//
//      // Crea y agrega tus CardViews
//      for (int i = 0; i < 5; i++) { // Ejemplo: crea 5 CardViews
//         CardView cardView = new CardView(requireContext());
//         cardView.setLayoutParams(new LinearLayout.LayoutParams(
//             getResources().getDimensionPixelSize(R.dimen.card_width),
//             getResources().getDimensionPixelSize(R.dimen.card_height)
//         ));
//
//         // Configura el contenido de tu CardView (puedes personalizarlo según tus necesidades)
//         LayoutInflater inflater = LayoutInflater.from(requireContext());
//         View cardContent = inflater.inflate(R.layout.card_content, null);
//         cardView.addView(cardContent);
//
//         // Agrega el CardView al LinearLayout
//         cardContainer.addView(cardView);
//      }
//   }
//
//   private void setupUI() {
//      ImageSlider imageSlider = binding.imageSlider;
//
//      HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//      List<SlideModel> slideModelsList = viewModel.getSlideModelsList();
//
//      // Configura el ImageSlider
//      imageSlider.setImageList(slideModelsList);
//   }
//
//   @Override
//   public void onDestroyView() {
//      super.onDestroyView();
//      binding = null;
//   }
//}


package com.famisalud.famisalud.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.famisalud.famisalud.R;
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
      setupCardViews(root); // Pasa la vista 'root' como argumento
      setupUI();
      return root;
   }

   private void setupCardViews(View root) {
      // Obtén una referencia al LinearLayout que contiene los CardViews
      LinearLayout cardContainer = root.findViewById(R.id.cardContainer);

      // Crea y agrega tus CardViews
      for (int i = 0; i < 7; i++) { // Ejemplo: crea 7 CardViews
         CardView cardView = new CardView(requireContext());

         LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
             getResources().getDimensionPixelSize(R.dimen.card_width),
             getResources().getDimensionPixelSize(R.dimen.card_height)
         );

         // Agrega márgenes izquierdo y derecho al CardView para separarlos
         cardLayoutParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.card_margin));
         cardLayoutParams.setMarginEnd(getResources().getDimensionPixelSize(R.dimen.card_margin));

         cardView.setLayoutParams(cardLayoutParams);

         // Configura el contenido de tu CardView (puedes personalizarlo según tus necesidades)
         LayoutInflater inflater = LayoutInflater.from(requireContext());
         View cardContent = inflater.inflate(R.layout.cardview, null);
         cardView.addView(cardContent);

         // Agrega el CardView al LinearLayout
         cardContainer.addView(cardView);
      }
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
