package com.famisalud.famisalud.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.famisalud.famisalud.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
   private static final int IMAGE_PICK = 1;
   private ProfileViewModel viewModel;
   private FragmentProfileBinding binding;
   private StorageReference profileImagesRef;
   private String uid;

   private String downloadUrl;


   @Override
   public View onCreateView(@NonNull LayoutInflater inflater,
                            @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
      binding = FragmentProfileBinding.inflate(inflater, container, false);
      View rootView = binding.getRoot();


      viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

      binding.btCerrarSession.setOnClickListener(v -> {
         viewModel.signOutAndNavigateToLogin(requireActivity());
      });

      FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

      if (currentUser != null) {
         uid = currentUser.getUid();

         // Inicialización de Firebase Storage
         FirebaseStorage storage = FirebaseStorage.getInstance();
         StorageReference storageRef = storage.getReference();

         // Obtener una referencia a la ubicación donde se guardará la imagen
         profileImagesRef = storageRef.child("profile_images/" + uid + ".jpg");

         // Cargar la imagen del usuario desde Firebase Storage
         loadProfileImageFromFirebaseStorage();

         binding.btnUploadImage.setOnClickListener(v -> {
            // Abre un selector de imágenes para el usuario
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK);
         });

         // Cargar la imagen del usuario desde el ViewModel
         viewModel.getImageUrl().observe(getViewLifecycleOwner(), imageUrl -> {
            if (imageUrl != null) {
               // Cargar la imagen en el ImageView
               Picasso.get().load(imageUrl).into(binding.ivProfileImage);
            }
         });


         DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
         String userNodePath = "users/" + uid + "/nombre";
         String telefonoPath = "users/" + uid + "/telefono";

         databaseRef.child(userNodePath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()) {
                  String nombreUsuario = dataSnapshot.getValue(String.class);
                  if (nombreUsuario != null) {
                     binding.tvSaludo.setText("Hola, " + nombreUsuario);
                  }

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // Manejar errores si es necesario
               Toast.makeText(requireContext(), "Error al obtener el nombre: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
         });
         databaseRef.child(telefonoPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()){
                  String telefonoUsuario = snapshot.getValue(String.class);
                  if (telefonoUsuario != null){
                     binding.ivProfileCelular.setText(telefonoUsuario);
                  }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // Handle database error (if any).
            }
         });
      }

      return rootView;
   }

   private void loadProfileImageFromFirebaseStorage() {
      ProgressBar progressBar = binding.progressBar; // Obtén la referencia al ProgressBar desde el layout

      progressBar.setVisibility(View.VISIBLE); // Muestra el ProgressBar mientras se carga la imagen

      profileImagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
         String downloadUrl = uri.toString();

         // Cargar la imagen en el ImageView
         Picasso.get().load(downloadUrl).into(binding.ivProfileImage);

         // Actualizar la URL de la imagen en el ViewModel si es necesario
         viewModel.setImageUrl(downloadUrl);

         progressBar.setVisibility(View.GONE); // Oculta el ProgressBar una vez que se haya cargado la imagen
      }).addOnFailureListener(e -> {
         // Manejar errores al obtener la URL de la imagen desde Firebase Storage
         Log.e("ProfileFragment", "Error al obtener la URL de la imagen: " + e.getMessage());
         progressBar.setVisibility(View.GONE); // Asegúrate de que el ProgressBar se oculte en caso de error
      });
   }


   @Override
   public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == IMAGE_PICK && resultCode == -1) {
         if (data != null) {
            // Aquí puedes manejar la imagen seleccionada por el usuario
            Uri imageUri = data.getData();

            if (imageUri != null && profileImagesRef != null) {
               // Sube la imagen a Firebase Storage usando la referencia existente
               profileImagesRef.putFile(imageUri)
                   .addOnSuccessListener(taskSnapshot -> {
                      // Imagen subida con éxito, ahora obtén la URL de descarga
                      profileImagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
                         String downloadUrl = uri.toString();

                         // Guarda la URL en Realtime Database
                         DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                         String userImageURLPath = "users/" + uid + "/imagenURL";

                         databaseRef.child(userImageURLPath).setValue(downloadUrl)
                             .addOnSuccessListener(aVoid -> {
                                // URL de la imagen guardada con éxito
                                // Puedes mostrar un mensaje al usuario aquí
                                Toast.makeText(requireContext(), "Imagen de perfil actualizada", Toast.LENGTH_SHORT).show();

                                // Carga la imagen en el ImageView
                                Picasso.get().load(downloadUrl).into(binding.ivProfileImage);

                                // Actualizar la URL de la imagen en el ViewModel
                                viewModel.setImageUrl(downloadUrl);
                             })
                             .addOnFailureListener(e -> {
                                // Manejar errores al guardar la URL en Realtime Database
                                Toast.makeText(requireContext(), "Error al guardar la URL de la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                             });
                      });
                   })
                   .addOnFailureListener(e -> {
                      // Manejar errores al subir la imagen
                      Toast.makeText(requireContext(), "Error al subir la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                   });
            }
         }
      }
   }

}
