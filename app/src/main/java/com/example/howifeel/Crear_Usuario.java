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

public class Crear_Usuario extends AppCompatActivity {
    private EditText CorreoRegistro;
    private EditText ContraseñaRegistro;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear__usuario);

        //creacion de usuario
        CorreoRegistro = (EditText) findViewById(R.id.editTextTextPersonName2);
        ContraseñaRegistro = (EditText) findViewById(R.id.editTextTextPassword2);

        //autenticacion
        firebaseAuth = firebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(Crear_Usuario.this,"Se le envio un email",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(Crear_Usuario.this,"error",Toast.LENGTH_SHORT).show();
                }
            }
        };
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

    public void continuar_crear_usuario(View view){
        String username= CorreoRegistro.getText().toString();
        String password = ContraseñaRegistro.getText().toString();
        //Toast.makeText(this,"usuario creado",Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(Crear_Usuario.this,"Error al crear usuario",Toast.LENGTH_LONG).show();
                }else{
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.sendEmailVerification();
                    //Toast.makeText(Crear_Usuario.this,"Se le envio un correo de verificacion",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Crear_Usuario.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void cancelar_usuario_boton (View view){
        Intent intent = new Intent(Crear_Usuario.this, MainActivity.class);
        startActivity(intent);
    }
}