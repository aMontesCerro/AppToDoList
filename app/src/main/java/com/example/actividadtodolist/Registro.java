package com.example.actividadtodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.actividadtodolist.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

public class Registro extends AppCompatActivity {

    ControladorDB controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        controlador = new ControladorDB(this);

        getSupportActionBar().hide();
    }

    public void cancelar(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void crearCuenta(View view){
        TextInputEditText usuarioReg = (TextInputEditText) findViewById(R.id.nombreReg);
        TextInputEditText passReg = (TextInputEditText) findViewById(R.id.contraReg);

        if(usuarioReg.getText().toString().isEmpty()){
            usuarioReg.setError("Falta nombre de usuario");
        }
        if(passReg.getText().toString().isEmpty()){
            passReg.setError("Falta introducir una contraseña");
        }

        if(!(usuarioReg.getText().toString().isEmpty() && passReg.getText().toString().isEmpty())){

            if(controlador.comprobarUser(usuarioReg.getText().toString())){
                Toast toast = Toast.makeText(this, "Usuario ya existente", Toast.LENGTH_LONG);
                toast.show();
            }else{
                controlador.crearUsuario(usuarioReg.getText().toString(), passReg.getText().toString());

                Toast toast = Toast.makeText(this, "Usuario creado con éxito", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("usuario", usuarioReg.getText().toString());
                startActivity(intent);



            }



        }


    }


}