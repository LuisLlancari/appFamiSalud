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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileFragment extends Fragment {
   private static final int IMAGE_PICK = 1;
   private ProfileViewModel viewModel;
   private FragmentProfileBinding binding;
   private StorageReference profileImagesRef;
   private String uid;

   private String downloadUrl;

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      binding = FragmentProfileBinding.inflate(inflater, container, false);
      View rootView = binding.getRoot();

      viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
      setupSignOutButton();
      setupFirebase();
      //setupProfileImageUpload();
      setupProfileInfo();
      deleteAccount();

      return rootView;
   }

   private void deleteAccount() {
      binding.liconfiguracion.setOnClickListener(v -> {
         SweetAlertDialog dialog = new SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE);
         dialog.setTitleText("Estas Seguro?");
         dialog.setConfirmText("continuar");
         dialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.setTitleText("Borrar");
            sweetAlertDialog.setContentText("Ella no te ama");
            sweetAlertDialog.setConfirmClickListener(null);
            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
         });
         dialog.show();
      });

   }


   private void setupSignOutButton() {
      binding.btCerrarSession.setOnClickListener(v -> {
         viewModel.signOutAndNavigateToLogin(requireActivity());
      });
   }

   private void setupFirebase() {
      FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
      if (currentUser != null) {
         uid = currentUser.getUid();
         setupFirebaseStorage();
         loadProfileImageFromFirebaseStorage();
         setupImageUploadButton();
//         loadProfileInfoFromFirebase();
      }
   }


   private void setupFirebaseStorage() {
      FirebaseStorage storage = FirebaseStorage.getInstance();
      StorageReference storageRef = storage.getReference();
      profileImagesRef = storageRef.child("profile_images/" + uid + ".jpg");
   }

   private void setupImageUploadButton() {
      binding.btnUploadImage.setOnClickListener(v -> {
         Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
         intent.setType("image/*");
         startActivityForResult(intent, IMAGE_PICK);
      });
   }

   private void loadProfileImageFromFirebaseStorage() {
      ProgressBar progressBar = binding.progressBar;
      progressBar.setVisibility(View.VISIBLE);
      profileImagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
         String downloadUrl = uri.toString();
         loadImageToImageView(downloadUrl);
         progressBar.setVisibility(View.GONE);
      }).addOnFailureListener(e -> {
         handleImageDownloadError(e);
         progressBar.setVisibility(View.GONE);
      });
   }

   private void handleImageDownloadError(Exception e) {
      Log.e("ProfileFragment", "Error al obtener la URL de la imagen: " + e.getMessage());
   }

   private void loadImageToImageView(String imageUrl) {
      viewModel.setImageUrl(imageUrl);
      Picasso.get().load(imageUrl).into(binding.ivProfileImage);
   }

   private void setupProfileInfo() {
      DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
      String userNodePath = "users/" + uid + "/nombre";
      String telefonoPath = "users/" + uid + "/telefono";

      loadUserNameFromFirebase(databaseRef, userNodePath);
      loadUserPhoneNumberFromFirebase(databaseRef, telefonoPath);
   }

   private void loadUserNameFromFirebase(DatabaseReference databaseRef, String userNodePath) {
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
            handleDatabaseError(databaseError);
         }
      });
   }

   private void loadUserPhoneNumberFromFirebase(DatabaseReference databaseRef, String telefonoPath) {
      databaseRef.child(telefonoPath).addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
               String telefonoUsuario = snapshot.getValue(String.class);
               if (telefonoUsuario != null) {
                  binding.ivProfileCelular.setText(telefonoUsuario);
               }
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            handleDatabaseError(error);
         }
      });
   }

   private void handleDatabaseError(DatabaseError error) {
      Toast.makeText(requireContext(), "Error en la base de datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
   }

   @Override
   public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == IMAGE_PICK && resultCode == -1 && data != null) {
         Uri imageUri = data.getData();
         if (imageUri != null && profileImagesRef != null) {
            uploadImageToFirebaseStorage(imageUri);
         }
      }
   }

   private void uploadImageToFirebaseStorage(Uri imageUri) {
      profileImagesRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
         profileImagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String downloadUrl = uri.toString();
            saveImageUrlToFirebaseDatabase(downloadUrl);
         });
      }).addOnFailureListener(this::handleImageUploadError);
   }

   private void saveImageUrlToFirebaseDatabase(String downloadUrl) {
      DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
      String userImageURLPath = "users/" + uid + "/imagenURL";

      databaseRef.child(userImageURLPath).setValue(downloadUrl).addOnSuccessListener(aVoid -> {
         Toast.makeText(requireContext(), "Imagen de perfil actualizada", Toast.LENGTH_SHORT).show();
         loadImageToImageView(downloadUrl);
      }).addOnFailureListener(e -> {
         Toast.makeText(requireContext(), "Error al guardar la URL de la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
      });
   }

   private void handleImageUploadError(Exception e) {
      Toast.makeText(requireContext(), "Error al subir la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
   }
}
