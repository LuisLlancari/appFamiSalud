package com.famisalud.famisalud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;

import java.util.Timer;
import java.util.TimerTask;

public class splash_intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_intro);
        FirebaseApp.initializeApp(this);
//        getSupportActionBar().hide();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_intro.this,  Login.class);
                startActivity(intent);
                finish();
            }
        };
        Timer tiempo =new Timer();
//        A 0
        tiempo.schedule(tarea,0);
    }
}