package com.example.invernaderocaminoverde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonIsesion, buttonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonIsesion = (Button)findViewById(R.id.buttonIniciarSesion);
        buttonIsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar(buttonIsesion);
            }
        });

        buttonRegistro = (Button)findViewById(R.id.buttonRegistro);
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarse(buttonRegistro);
            }
        });
    }


    public void iniciar(View view){
        Intent i = new Intent(this, IniciarSesion.class);
        startActivity(i);
    }

    public void registrarse(View view){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }
}