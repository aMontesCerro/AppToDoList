package com.example.actividadtodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity implements Animation.AnimationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.animsplash);
        Animation animacionLogo = AnimationUtils.loadAnimation(this, R.anim.animlogo);
        Animation animacionCirculo = AnimationUtils.loadAnimation(this, R.anim.animcirculo);

        TextView titulo = (TextView) findViewById(R.id.titulo);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        ImageView circulo = (ImageView) findViewById(R.id.circuloSplash);

        titulo.startAnimation(animacion);
        logo.startAnimation(animacionLogo);
        circulo.startAnimation(animacionCirculo);
        animacion.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}