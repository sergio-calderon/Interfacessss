package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonView, buttonSalir, botonGps, botonSintomas;
    TextView NombreUsuario;
    int id=0;
    Usuario u;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        buttonView = (Button) findViewById(R.id.buttonEdit);
        buttonSalir = (Button) findViewById(R.id.buttonSalir);
        botonGps = (Button) findViewById(R.id.botonGps);
        botonSintomas = (Button) findViewById(R.id.buttonSintomas);

        NombreUsuario = (TextView) findViewById(R.id.NombreUsuario);

        buttonView.setOnClickListener(this);
        buttonSalir.setOnClickListener(this);
        botonGps.setOnClickListener(this);
        botonSintomas.setOnClickListener(this);


        Bundle b=getIntent().getExtras();
        id=b.getInt("id");
        dao=new daoUsuario(this);
        u=dao.getUserById(id);
        //NombreUsuario.setText(u.getNombre());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSintomas:
                Intent sintomas = new Intent(MainActivity.this, TengoSintomas.class);
                sintomas.putExtra("id",u.getId());
                startActivity(sintomas);
                break;
            case R.id.buttonSalir:
                Intent i3 = new Intent(MainActivity.this, Login.class);
                startActivity(i3);
                break;

            case R.id.buttonEdit:
                Intent i4 = new Intent(MainActivity.this, Edit_and_Delete.class);
                i4.putExtra("id",u.getId());
                startActivity(i4);
                break;
            case R.id.botonGps:
                Intent my_gps = new Intent(MainActivity.this, ObtnUbicacion.class);
                my_gps.putExtra("id",id);
                startActivity(my_gps);
                break;
        }
    }
}