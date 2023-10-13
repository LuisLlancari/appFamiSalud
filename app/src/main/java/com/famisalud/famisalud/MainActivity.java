package com.famisalud.famisalud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.famisalud.famisalud.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.Provider;

enum ProviderType{
   BASIC
}

public class MainActivity extends AppCompatActivity {

Button btcerrarSession;

   private ActivityMainBinding binding;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      FirebaseApp.initializeApp(this); // Inicializar Firebase
      getSupportActionBar().hide();
      binding = ActivityMainBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());

      loadUI();
      checkUserAuthentication(); // Verificar el estado de autenticación del usuario

      final Bundle bundle = getIntent().getExtras();
      final String email = bundle.getString("usuario");
      final String provider = bundle.getString("provider");
      setup(email, provider);


      BottomNavigationView navView = findViewById(R.id.nav_view);
      AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
          R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_profile, R.id.navigation_drugstore)
          .build();
      NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
      NavigationUI.setupWithNavController(binding.navView, navController);
   }

   private void setup(String email, String provider) {
//      setTitle("Inicio");
      btcerrarSession.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
            onBackPressed();
         }
      });
   }

   private void loadUI() {
      btcerrarSession = findViewById(R.id.btCerarSession);
   }

   // ... (Métodos de ayuda y otros)

   // Método para verificar si el usuario está autenticado
   private void checkUserAuthentication() {
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      if (user == null) {
         // El usuario no ha iniciado sesión, dirige al usuario a la pantalla de inicio de sesión
         goToLoginActivity();
      }
   }

   private void goToLoginActivity() {
      Intent intent = new Intent(this, Login.class);
      startActivity(intent);
      finish(); // Cierra la actividad actual
   }

}

//esto es un comentario