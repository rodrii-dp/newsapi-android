package com.rodrigo.newsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText editUsuario, editContrasena, editConf;
    private CheckBox checkBox;
    private Button button;
    private String usuarioStr, contrasena, conf;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userService = UserService.get(this);
        editUsuario = findViewById(R.id.usuario);
        editContrasena = findViewById(R.id.contrasena);
        editConf = findViewById(R.id.confirmarContrasena);
        button = findViewById(R.id.boton);
        checkBox = findViewById(R.id.checkBox);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {

        usuarioStr = editUsuario.getText().toString();
        contrasena = editContrasena.getText().toString();
        conf = editConf.getText().toString();

        if (usuarioStr != "" && contrasena != "" && conf != "") {
            if (checkFields(usuarioStr, contrasena, conf, checkBox)) {
                User usuario = new User(usuarioStr, contrasena);
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Rellena todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean userExists(String username) {
        User user = userService.getUserbyName(username);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean safePassword(String contraseña) {
        if (contraseña.length() < 8) {
            return false;
        }

        // Verificar la presencia de letras mayúsculas
        Pattern mayusculaPattern = Pattern.compile("[A-Z]");
        Matcher mayusculaMatcher = mayusculaPattern.matcher(contraseña);
        if (!mayusculaMatcher.find()) {
            return false;
        }

        // Verificar la presencia de letras minúsculas
        Pattern minusculaPattern = Pattern.compile("[a-z]");
        Matcher minusculaMatcher = minusculaPattern.matcher(contraseña);
        if (!minusculaMatcher.find()) {
            return false;
        }

        // Verificar la presencia de números
        Pattern numeroPattern = Pattern.compile("[0-9]");
        Matcher numeroMatcher = numeroPattern.matcher(contraseña);
        if (!numeroMatcher.find()) {
            return false;
        }

        // Verificar la presencia de caracteres especiales
        Pattern especialPattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]");
        Matcher especialMatcher = especialPattern.matcher(contraseña);
        if (!especialMatcher.find()) {
            return false;
        }

        // Si pasa todas las verificaciones, la contraseña es considerada segura
        return true;
    }

    private boolean checkFields(String usuario, String contrasena, String conf, CheckBox checkBox) {
        if (userExists(usuario)) {
            Toast.makeText(RegisterActivity.this, "Este usuario ya existe.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!safePassword(contrasena)) {
            Toast.makeText(RegisterActivity.this, "Contraseña insegura", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!conf.equals(contrasena)) {
            Toast.makeText(RegisterActivity.this, "Contraseña de confirmación incorrecta", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!checkBox.isChecked()) {
            Toast.makeText(RegisterActivity.this, "Acepta los términos y condiciones para continuar.", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }