package com.co.esteban;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    EditText inputCorreoCliente;
    EditText inputClaveCliente;
    Button btnIniciarSesion;
    Button btnIrRegistro;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciarSesion = findViewById(R.id.btn_iniciar);
        btnIrRegistro = findViewById(R.id.btn_ir_registro);
        inputCorreoCliente = findViewById(R.id.input_correo_cliente);
        inputClaveCliente = findViewById(R.id.input_clave_cliente);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = inputCorreoCliente.getText().toString();
                String clave = inputClaveCliente.getText().toString();

                if (correo.isEmpty() || clave.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                iniciarSesion(correo, clave);
            }
        });

        btnIrRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irRegistro();
            }
        });
    }

    public void iniciarSesion(String correo, String clave) {
        auth.signInWithEmailAndPassword(correo, clave)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Logeado correctamente", Toast.LENGTH_LONG).show();
                            irInicio();
                        } else {
                            Toast.makeText(MainActivity.this, "Valide sus credenciales", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void irInicio() {
        Intent intent = new Intent(MainActivity.this, Inicio.class);
        startActivity(intent);
        finish();
    }

    public void irRegistro() {
        Intent intent = new Intent(MainActivity.this, registro.class);
        startActivity(intent);
        finish();
    }
}
