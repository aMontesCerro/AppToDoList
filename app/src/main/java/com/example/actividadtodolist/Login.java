package com.example.actividadtodolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.actividadtodolist.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    ControladorDB controladorDB;
    TextInputEditText usuario, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controladorDB= new ControladorDB(this);

        getSupportActionBar().hide();



    }

    public void registro(View view){

        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);


    }

    public void login (View view){
        usuario = (TextInputEditText) findViewById(R.id.cajaUser);
        pass = (TextInputEditText) findViewById(R.id.cajaPass);

        if(usuario.getText().toString().isEmpty()){
            usuario.setError("Falta nombre de usuario");
        }

        if(pass.getText().toString().isEmpty()){
            pass.setError("Falta introducir contraseña");
        }



        if(controladorDB.comprobarUser(usuario.getText().toString())){

            if(controladorDB.validarUserPass(usuario.getText().toString(), pass.getText().toString())){
                Intent intent = new Intent(this, MainActivity.class);
                Toast toast = Toast.makeText(this, "Correcto", Toast.LENGTH_LONG);
                toast.show();
                intent.putExtra("usuario", usuario.getText().toString());
                usuario.setText("");
                pass.setText("");
                startActivity(intent);
            } else{
                Toast toast = Toast.makeText(this, "Credenciales no válidas", Toast.LENGTH_LONG);
                toast.show();
            }

        }else{
            usuario.setError("Usuario no existente");
            Toast toast = Toast.makeText(this, "Usuario no existente", Toast.LENGTH_LONG);
            toast.show();
        }


    }



}