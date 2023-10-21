package com.famisalud.famisalud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

   TextView iniciarSessionActivity;
   TextInputEditText tietCorreo, tietContraseña, tietNombre, tietTelefono;
   Button btRegistrarse;

   private DatabaseReference databaseReference;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      FirebaseApp.initializeApp(this); // Inicializar Firebase
      setContentView(R.layout.activity_register);
      loadUI();
      checkUserAuthentication();
      getSupportActionBar().hide();

      // Evento para volver a la actividad de inicio de sesión
      iniciarSessionActivity.setOnClickListener(v -> {
         Intent intent = new Intent(getApplicationContext(), Login.class);
         startActivity(intent);
      });

      //  Inicializar la referencia a la base de datos
      databaseReference = FirebaseDatabase.getInstance().getReference();

      setup();
   }

   private void showToast(String message) {
      Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
   }

   private void checkUserAuthentication() {
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      if (user != null) {
         showModel(user.getEmail(), ProviderType.BASIC);
      }
   }

   private void showModel(String usuario, ProviderType provider) {
      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
      intent.putExtra("usuario", usuario);
      intent.putExtra("provider", provider.name());
      startActivity(intent);
   }

   private boolean isValidEmail(String email) {
      String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
      return email.matches(emailPattern);
   }

   private void setup() {
      btRegistrarse.setOnClickListener(v -> {
         validateRegistrationFields();
      });
   }

   private void validateRegistrationFields() {
      String email = tietCorreo.getText().toString();
      String password = tietContraseña.getText().toString();
      String nombre = tietNombre.getText().toString(); // Agregar la obtención del nombre
      String telefono = tietTelefono.getText().toString();

      if (!email.isEmpty() && !password.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty()) {
         if (isValidEmail(email)) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                  // Registro exitoso
                  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                  if (user != null) {
                     String uid = user.getUid();

                     showSuccessMessage();

                     saveUserNameInRealtimeDatabase(uid, nombre, telefono); // Pasamos el UID en lugar del email
                     showModel(email, ProviderType.BASIC);
                     clearFields();
                  }
               } else {
                  showAlert("Se ha producido un error de autenticación al usuario");
               }
            });
         } else {
            // Muestra una alerta indicando que el correo electrónico no es válido
            showToast("Por favor, ingresa un correo electrónico válido.");
         }
      } else {
         // Muestra una alerta indicando que los campos están vacíos
         showToast("Por favor, completa ambos campos.");
      }
   }

   private void saveUserNameInRealtimeDatabase(String uid, String nombre, String telefono) {
      // Definimos la ruta en la base de datos donde se guardarán los datos, utilizando el UID como nodo
      String databasePath = "users/" + uid;

      // Agregar el nombre del usuario a la base de datos en la ruta específica
      databaseReference.child(databasePath).child("nombre").setValue(nombre);
      databaseReference.child(databasePath).child("telefono").setValue(telefono);

   }

   private void showAlert(String msg) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Error");
      builder.setMessage(msg);
      builder.setPositiveButton("Aceptar", null);
      Dialog dialog = builder.create();
      dialog.show();
   }

   private void loadUI() {
      iniciarSessionActivity = findViewById(R.id.tvIniciarSessionActivity);

      tietCorreo = findViewById(R.id.tietCorreoRegistrar);
      tietContraseña = findViewById(R.id.tietRegistrarContraseña);
      tietNombre = findViewById(R.id.tietNombreRegistrar);
      tietTelefono = findViewById(R.id.tietTelfonoRegistrar);

      btRegistrarse = findViewById(R.id.btRegistrarCuenta);
   }

   private void clearFields() {
      tietCorreo.getText().clear();
      tietContraseña.getText().clear();
      tietNombre.getText().clear();
   }

   private void showSuccessMessage() {
      showToast("¡Registro exitoso!");
   }
}
