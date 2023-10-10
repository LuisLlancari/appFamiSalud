package com.famisalud.famisalud;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.famisalud.famisalud.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

   private ActivityMainBinding binding;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      binding = ActivityMainBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());

      getSupportActionBar().hide();
      FloatingActionButton fabDial = findViewById(R.id.fabDial);
      fabDial.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            String phoneNumber = "987654321";

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + 987654321));
            startActivity(intent);
         }
      });

      BottomNavigationView navView = findViewById(R.id.nav_view);
      // Passing each menu ID as a set of Ids because each
      // menu should be considered as top level destinations.
      AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
          R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_profile, R.id.navigation_drugstore)
          .build();
      NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
      NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
      NavigationUI.setupWithNavController(binding.navView, navController);


   }


}