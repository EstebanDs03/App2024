package com.co.esteban;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registro extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference().child("Usuarios");

    Button btnRegistrar;
    Button btnCancelarReg;
    EditText inputTel;
    EditText inputNombre;
    EditText inputCorreo;
    EditText inputClave;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnCancelarReg = findViewById(R.id.btn_cancelar);
        btnRegistrar = findViewById(R.id.btn_registrar);
        inputTel = findViewById(R.id.input_tel_id);
        inputNombre = findViewById(R.id.input_nombre);
        inputCorreo = findViewById(R.id.input_correo);
        inputClave = findViewById(R.id.input_password);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        btnCancelarReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irMain();
            }
        });
    }

    public void irMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Finaliza la actividad actual
    }

    public void registrar() {
        String tel = inputTel.getText().toString();
        String nombre = inputNombre.getText().toString();
        String correo = inputCorreo.getText().toString();
        String clave = inputClave.getText().toString();

        // Validación básica de campos
        if (tel.isEmpty() || nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar en Firebase Realtime Database
        DatabaseReference nuevoUsuario = reference.child(tel);
        nuevoUsuario.child("Tel: ").setValue(tel);
        nuevoUsuario.child("Nombre: ").setValue(nombre);
        nuevoUsuario.child("Correo: ").setValue(correo);
        nuevoUsuario.child("Clave: ").setValue(clave);

        // Registro en Firebase Authentication
        registroFireBaseAuth(correo, clave);
    }

    public void registroFireBaseAuth(String correo, String clave) {
        auth.createUserWithEmailAndPassword(correo, clave)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(registro.this, "Cuenta Creada Correctamente", Toast.LENGTH_LONG).show();
                            irMain(); // Redirige al main después de registrar correctamente
                        } else {
                            Toast.makeText(registro.this, "La cuenta no fue creada correctamente: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("Firebase Auth", "Error al registrar usuario: " + task.getException().getMessage());
                        }
                    }
                });
    }
}
