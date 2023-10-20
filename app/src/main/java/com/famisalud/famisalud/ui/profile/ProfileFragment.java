package com.famisalud.famisalud.ui.profile;//package com.famisalud.famisalud.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.famisalud.famisalud.databinding.FragmentProfileBinding; // Importa la clase de vinculación generada
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

   private ProfileViewModel viewModel;
   private FragmentProfileBinding binding; // Declara una variable de vinculación

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater,
                            @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {

      binding = FragmentProfileBinding.inflate(inflater, container, false); // Infla la vista usando View Binding
      View rootView = binding.getRoot(); // Accede a la vista raíz a través del objeto de vinculación

      viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

      // Configurar el evento de cierre de sesión
      binding.btCerrarSession.setOnClickListener(v -> {
         viewModel.signOutAndNavigateToLogin(requireActivity());
      });

      // Recuperar el usuario actualmente autenticado
      FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
      String uid = currentUser.getUid();

      if (currentUser != null) {
         // Obtener la referencia a la base de datos de Firebase
         DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

         // Construir la ruta para obtener el nombre del usuario
         String userNodePath = "users/" + uid + "/nombre";

         // Agregar un oyente de datos para recuperar el nombre
         databaseRef.child(userNodePath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()) {
                  String nombreUsuario = dataSnapshot.getValue(String.class);
                  if (nombreUsuario != null) {
                     // Mostrar el saludo personalizado
                     binding.tvSaludo.setText("Hola, " + nombreUsuario);
                  }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // Manejar errores si es necesario
            }
         });
      }
      return rootView;
   }

}
