package com.famisalud.famisalud;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


//import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

   EditText etusuario, etcontrasena;
   Button btiniciarsession, btregistrarse;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      FirebaseApp.initializeApp(this); // Inicializar Firebase
      setContentView(R.layout.activity_login);

      loadUI();
      checkUserAuthentication();

      final FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
      final Bundle bundle = new Bundle();
      bundle.putString("message", "Integración de Firebase completa");
      analytics.logEvent("InitScreen", bundle);

      // Verifica si el usuario ya ha iniciado sesión utilizando SharedPreferences
      //  OJO esto es almacenamiento local: SharedPreferences
//      if (isUserLoggedIn()) {
//         showModel(etusuario.getText().toString(), ProviderType.BASIC);
//      } else {
//         setup();
//      }

      setup();
   }

//   private boolean isUserLoggedIn() {
//      SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//      return sharedPreferences.getBoolean("isLoggedIn", false);
//   }

   private void setup() {

      btregistrarse.setOnClickListener(v -> {
         String email = etusuario.getText().toString();
         String password = etcontrasena.getText().toString();

         if (!email.isEmpty() && !password.isEmpty()) {
            if (isValidEmail(email)) {
               FirebaseAuth.getInstance()
                   .createUserWithEmailAndPassword(email, password)
                   .addOnCompleteListener(task -> {
                      if (task.isSuccessful()) {
                         // Éxito en la creación del usuario
                         showModel(email, ProviderType.BASIC);
                      } else {
                         showAlert();
                      }
                   });
            } else {
               // Muestra una alerta indicando que el correo electrónico no es válido
               Toast.makeText(this, "Por favor, ingresa un correo electrónico válido.", Toast.LENGTH_SHORT).show();
            }
         } else {
            // Muestra una alerta indicando que los campos están vacíos
            Toast.makeText(this, "Por favor, completa ambos campos.", Toast.LENGTH_SHORT).show();
         }
      });

      btiniciarsession.setOnClickListener(v -> {
         if (!etusuario.getText().toString().isEmpty() && !etcontrasena.getText().toString().isEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(etusuario.getText().toString(), etcontrasena.getText().toString()).addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                  // Éxito en la creación del usuario
                  showModel(etusuario.getText().toString(), ProviderType.BASIC);

                  // Guarda el estado de inicio de sesión en SharedPreferences
                  saveLoginStatus(true);
               } else {
                  showAlert();
               }
            });
         } else {
            // Muestra una alerta indicando que los campos están vacíos
            Toast.makeText(this, "Por favor, completa ambos campos.", Toast.LENGTH_SHORT).show();
         }
      });


   }

   private void saveLoginStatus(boolean isLoggedIn) {
      SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putBoolean("isLoggedIn", isLoggedIn);
      editor.apply();
   }


   private boolean isValidEmail(String email) {
      String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
      return email.matches(emailPattern);
   }

   // Método para verificar si el usuario está autenticado
   private void checkUserAuthentication() {
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      if (user != null) {
         // El usuario ya ha iniciado sesión, dirige al usuario a la actividad principal
         showModel(user.getEmail(), ProviderType.BASIC);
      }
   }

   private void showAlert() {

      final AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Error");
      builder.setMessage("Se ha producido un error de autenticacion al usuario");
      builder.setPositiveButton("Aceptar", null);
      final Dialog dialog = builder.create();
      dialog.show();

   }


   private void showModel(String usuario, ProviderType provider) {
      final Intent intent = new Intent(this, MainActivity.class);
      intent.putExtra("usuario", usuario);
      intent.putExtra("provider", provider.name());
      startActivity(intent);
   }

   private void loadUI() {

      etcontrasena = findViewById(R.id.passwordEditText);
      etusuario = findViewById(R.id.usuarioEditText);

      btiniciarsession = findViewById(R.id.iniciarSessionButtom);
      btregistrarse = findViewById(R.id.registrarseButtom);
   }
}