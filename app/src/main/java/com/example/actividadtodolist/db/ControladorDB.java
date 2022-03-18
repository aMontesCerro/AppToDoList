package com.example.actividadtodolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.actividadtodolist.Login;
import com.example.actividadtodolist.MainActivity;

public class ControladorDB extends SQLiteOpenHelper {

    public ControladorDB(@Nullable Context context) {
        super(context, "com.example.todolist.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TAREAS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL, USERID INT REFERENCES USUARIOS(ID) );");
        db.execSQL("CREATE TABLE USUARIOS(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT , PASS TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean comprobarUser(String nombre){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USUARIOS WHERE NOMBRE='"+nombre+"'", null);
        if(cursor.getCount()==0){
            return false;
        }else{
            return true;
        }
    }

    public boolean validarUserPass(String usuario, String pass){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USUARIOS WHERE NOMBRE='"+usuario+"' and PASS='"+pass+"'", null);
        if(cursor.getCount()==0){
            db.close();
            return false;
        }else{
            db.close();
            return true;
        }

    }

    public void addTarea (String tarea, int usuario) {

        ContentValues registro = new ContentValues();
        registro.put("NOMBRE", tarea);
        registro.put("USERID", usuario);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("TAREAS", null, registro );
        db.close();

    }

    public String[] obtenerTareas(int usuario){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS WHERE USERID = '"+usuario+"'", null);


        int reg = cursor.getCount();
        if( reg==0){
            db.close();
            return null;
        }else{
            String[] tareas= new String[reg];
            cursor.moveToFirst();
            for (int i = 0 ; i<reg; i++){
                tareas[i]= cursor.getString(1);
                cursor.moveToNext();

            }
            db.close();
            return tareas;
        }


    }

    public int numeroRegistros(int usuario){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS WHERE USERID = '"+usuario+"'", null);
        return cursor.getCount();
    }

    public void borrarTarea(String tarea, int usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TAREAS", "NOMBRE= '"+tarea+"' and USERID = " + usuario, null);
        db.close();
    }

    public void modificarTarea(String nueva, String tarea, int usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NOMBRE", nueva);
        db.update("TAREAS", cv, "NOMBRE = '"+tarea+"' and USERID ="+usuario, null);

       // db.execSQL("UPDATE TAREAS SET NOMBRE = '"+ nueva+"' WHERE USERID="+usuario+ " and NOMBRE = '"+tarea+"'");


        db.close();
    }

    public void crearUsuario(String nombre, String pass){

        ContentValues registro = new ContentValues();
        registro.put("NOMBRE", nombre);
        registro.put("PASS", pass);


        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("USUARIOS", null, registro );
        db.close();
    }

    public int getUserId(String nombre){
        int num=0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM USUARIOS WHERE NOMBRE = ?", new String[]{nombre});

        if(cursor!= null){
            if(cursor.moveToFirst()==false){
                return 0;
            }
            int numeroIndice = cursor.getColumnIndex("ID");
            num = Integer.parseInt(cursor.getString(numeroIndice));
        } 

        db.close();

        return num;
    }












}
