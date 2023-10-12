package com.famisalud.famisalud.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.famisalud.famisalud.Login;
import com.famisalud.famisalud.R;

public class ProfileFragment extends Fragment {

   Button btlogin;

   private ProfileViewModel mViewModel;

   public static ProfileFragment newInstance() {
      return new ProfileFragment();
   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_profile, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
      // TODO: Use the ViewModel}

      loadUI();

      btlogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            startActivity(new Intent(requireActivity(), Login.class));
         }
      });
   }

   private void loadUI() {
      btlogin = requireView().findViewById(R.id.btlogin);
   }

}