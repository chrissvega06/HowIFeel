package com.example.howifeel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Inicio extends AppCompatActivity {

    @Override

    //prueba github
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void cerrarsesion (View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }
}