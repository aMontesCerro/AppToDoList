package com.example.actividadtodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.actividadtodolist.db.ControladorDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ControladorDB controladorDB;
    private ArrayAdapter<String> miAdapter;
    ListView listViewTareas;
    String user="";
    int userId=0;
    String nueva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controladorDB = new ControladorDB(this);

        user = getIntent().getStringExtra("usuario");
        userId = controladorDB.getUserId(user);

        listViewTareas = (ListView) findViewById(R.id.listaTareas);

        actualizarUI(userId);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final EditText cajaTexto = new EditText(this);

        switch (item.getItemId()){
            case R.id.nuevaTarea:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Nueva tarea")
                        .setMessage("¿Qué quieres hacer a continuación?")
                        .setView(cajaTexto)
                        .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(cajaTexto.getText().toString().isEmpty()){
                                    cajaTexto.setError("Introduce el nombre de la tarea");
                                }else{
                                    String tarea = cajaTexto.getText().toString();
                                    userId = controladorDB.getUserId(user);

                                    controladorDB.addTarea(tarea, userId);
                                    actualizarUI(userId);
                                    Toast.makeText(getApplicationContext(), "Tarea añadida con éxito", Toast.LENGTH_LONG).show();

                                }

                            }
                        })
                        .setNegativeButton("Cancelar" ,null)
                        .create();
                dialog.show();
                break;

            case R.id.Logout:
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    private void actualizarUI(int usuario){
        if(controladorDB.numeroRegistros(usuario)==0){
            listViewTareas.setAdapter(null);
        }else{
            miAdapter = new ArrayAdapter<>(this, R.layout.item_tarea, R.id.task_title, controladorDB.obtenerTareas(usuario));
            listViewTareas.setAdapter(miAdapter);
        }


    }

    public void borrarTarea (View view){
        View parent = (View) view.getParent();

        TextView tareaTexView = (TextView) parent.findViewById(R.id.task_title);
        String tarea = tareaTexView.getText().toString();
        controladorDB.borrarTarea(tarea, userId);
        actualizarUI(userId);
        Toast.makeText(getApplicationContext(), "Tarea completada", Toast.LENGTH_LONG).show();
    }

    public void modificarTarea (View view){

        View parent = (View) view.getParent();

        TextView tareaTextView = (TextView) parent.findViewById(R.id.task_title);
        String tarea = tareaTextView.getText().toString();
        final EditText cajaTexto = new EditText(this);



        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Modificar la tarea")
                .setMessage("Introduce la tarea corregida")
                .setView(cajaTexto)
                .setPositiveButton("Corregir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String intermedia = cajaTexto.getText().toString();

                        Toast.makeText(getApplicationContext(), "No implementado",Toast.LENGTH_LONG).show();

                        if(intermedia.isEmpty()){
                            cajaTexto.setError("Introduce el nombre de la tarea");

                        }else{
                            nueva = cajaTexto.getText().toString();
                            userId = controladorDB.getUserId(user);

                            controladorDB.modificarTarea(tarea, nueva,  userId);
                            actualizarUI(userId);

                        }

                    }
                })
                .setNegativeButton("Cancelar" ,null)
                .create();
        dialog.show();




    }


}