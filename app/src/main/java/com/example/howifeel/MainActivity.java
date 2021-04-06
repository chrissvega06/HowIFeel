package com.example.howifeel;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText CorreoInicioSesion;
    private EditText ContraseñaInicioSesion;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicio de sesion
        CorreoInicioSesion = (EditText) findViewById(R.id.editTextTextPersonName);
        ContraseñaInicioSesion = (EditText) findViewById(R.id.editTextTextPassword);

        //autenticacion
        firebaseAuth = firebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if (!user.isEmailVerified()) {
                        Toast.makeText(MainActivity.this, "Email no verificado", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(Iniciar_sesion.this, "Navegar a la actividad principal",Toast.LENGTH_SHORT).show();
                        goToInicioPrincipal();
                    }
                }
            }
        };
    }

    public void goToInicioPrincipal(){
        Intent intent = new Intent(this, Inicio.class );
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener !=null)
            firebaseAuth.removeAuthStateListener(authStateListener);
    }


    public void iniciar_sesion_boton (View view){
        String username= CorreoInicioSesion.getText().toString();
        String password = ContraseñaInicioSesion.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"no es posible iniciar sesion",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Crear_usuario_boton (View view){
        Intent intent = new Intent(MainActivity.this,Crear_Usuario.class);
        startActivity(intent);
    }
}