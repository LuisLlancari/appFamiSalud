package com.famisalud.famisalud.ui.profile;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.famisalud.famisalud.Login;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileViewModel extends ViewModel {
   // TODO: Implement the ViewModel
   private final MutableLiveData<String> imageUrl = new MutableLiveData<>();

   public LiveData<String> getImageUrl(){
      return imageUrl;
   }

   public void signOutAndNavigateToLogin(Activity activity) {
      // Cerrar sesión en Firebase
      FirebaseAuth.getInstance().signOut();

      // Limpiar la URL de la imagen al cerrar sesión
      setImageUrl(null);

      // Navegar a la actividad de inicio de sesión
      Intent intent = new Intent(activity, Login.class);
      activity.startActivity(intent);
      activity.finish(); // Cierra la actividad actual
   }

   public void setImageUrl(String url) {
      imageUrl.setValue(url);
   }
}
