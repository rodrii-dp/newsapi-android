package com.rodrigo.newsapi;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private EditText usuarioEdit, contrasenaEdit;
    private Button boton;
    private String usuario, contrasena;
    private UserService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userService = UserService.get(this);
        usuarioEdit = findViewById(R.id.usuario);
        contrasenaEdit = findViewById(R.id.contrasena);
        boton = findViewById(R.id.boton);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }
    private void login() {
        usuario = usuarioEdit.getText().toString();
        contrasena = contrasenaEdit.getText().toString();

        User usuarioObject = checkUser(usuario, contrasena);
        if (usuarioObject != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("usuario", usuarioObject);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
        }
    }

    private User checkUser(String nombreUsuarioIng, String contrasena) {
        User user = userService.getUserbyName(nombreUsuarioIng);

        if (user != null && user.getPassword().equals(contrasena)) {
            Log.d(TAG, "checkUsuario: Usuario encontrado y contraseña correcta");
            return user;
        } else {
            Log.d(TAG, "checkUsuario: No se encontró el usuario o la contraseña no es correcta.");
            return null;
        }
    }

    public void crearCuenta(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}