package com.famisalud.famisalud;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

   private TextView registraseActivity, registrarse;
   private EditText etUsuario, etContrasena;
   private Button btIniciarSesion;
//   private Button btRegistrarse;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      FirebaseApp.initializeApp(this); // Inicializar Firebase
      setContentView(R.layout.activity_login);
      getSupportActionBar().hide();

      loadUI();
      checkUserAuthentication();

      final FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
      final Bundle bundle = new Bundle();
      bundle.putString("message", "Integración de Firebase completa");
      analytics.logEvent("InitScreen", bundle);

      //registrarse.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Register.class)));
      registrarse.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), Register.class));
         }
      });
      setup();
   }

   private void setup() {
//      btRegistrarse.setOnClickListener(v -> {
//         String email = etUsuario.getText().toString();
//         String password = etContrasena.getText().toString();
//
//         if (!email.isEmpty() && !password.isEmpty()) {
//            if (isValidEmail(email)) {
//               FirebaseAuth.getInstance()
//                   .createUserWithEmailAndPassword(email, password)
//                   .addOnCompleteListener(task -> {
//                      if (task.isSuccessful()) {
//                         // Éxito en la creación del usuario
//                         showModel(email, ProviderType.BASIC);
//                         clearFields();
//                      } else {
//                         showAlert("Se ha producido un error de autenticación al usuario");
//                      }
//                   });
//            } else {
//               // Muestra una alerta indicando que el correo electrónico no es válido
//               showToast("Por favor, ingresa un correo electrónico válido.");
//            }
//         } else {
//            // Muestra una alerta indicando que los campos están vacíos
//            showToast("Por favor, completa ambos campos.");
//         }
//      });

      btIniciarSesion.setOnClickListener(v -> {
         String email = etUsuario.getText().toString();
         String password = etContrasena.getText().toString();

         if (!email.isEmpty() && !password.isEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                  // Éxito en el inicio de sesión
                  showModel(email, ProviderType.BASIC);
                  saveLoginStatus(true);
                  clearFields();
               } else {
                  showAlert("Se ha producido un error de autenticación al usuario");
               }
            });
         } else {
            // Muestra una alerta indicando que los campos están vacíos
            showToast("Por favor, completa ambos campos.");
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

   private void checkUserAuthentication() {
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      if (user != null) {
         showModel(user.getEmail(), ProviderType.BASIC);
      }
   }

   private void showAlert(String message) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Error");
      builder.setMessage(message);
      builder.setPositiveButton("Aceptar", null);
      Dialog dialog = builder.create();
      dialog.show();
   }

   private void showModel(String usuario, ProviderType provider) {
      Intent intent = new Intent(this, MainActivity.class);
      intent.putExtra("usuario", usuario);
      intent.putExtra("provider", provider.name());
      startActivity(intent);
   }

   private void loadUI() {
      etContrasena = findViewById(R.id.passwordEditText);
      etUsuario = findViewById(R.id.usuarioEditText);
      //registrarse = findViewById(R.id.tvRegistrarse);
      btIniciarSesion = findViewById(R.id.iniciarSessionButtom);
//     btRegistrarse = findViewById(R.id.registrarseButtom);
      registraseActivity = findViewById(R.id.tvRegistrarseActivity);
   }

   private void showToast(String message) {
      Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
   }

   private void clearFields() {
      etUsuario.getText().clear();
      etContrasena.getText().clear();
   }
}
