package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    EditText nom, correo, pass, dirr;
    Button confirmar, cancelar;
    daoUsuario dao;
    CheckBox terYCond;
    RadioButton masculino, femenino;
    RadioGroup genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        nom = (EditText) findViewById(R.id.Nombre);
        correo = (EditText) findViewById(R.id.Correo);
        pass = (EditText) findViewById(R.id.Pass);
        dirr = (EditText) findViewById(R.id.direccion);

        confirmar = (Button) findViewById(R.id.buttonConfirm);
        cancelar = (Button) findViewById(R.id.buttonCancelar);

        terYCond = (CheckBox)findViewById(R.id.checkBox);

        masculino = (RadioButton)findViewById(R.id.masculino);
        femenino = (RadioButton)findViewById(R.id.femenino);

        genero = (RadioGroup)findViewById(R.id.genero);

        confirmar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        dao = new daoUsuario(this);

    }

    @Override
    public void onClick(View v) {
        boolean gen = false;
        boolean valCorreo = false;

        switch (v.getId()){

            case R.id.buttonConfirm:
                Usuario u=new Usuario();
                u.setNombre(nom.getText().toString());
                u.setCorreo(correo.getText().toString());
                u.setPassword(pass.getText().toString());
                u.setDireccion(dirr.getText().toString());
                u.setContagiado("Probabilidad Baja");

                if (masculino.isChecked()){
                    u.setGenero("Masculino");
                    gen=true;
                }
                    if (femenino.isChecked()){
                        u.setGenero("Femenino");
                        gen=true;
                    }
                    if (validarEmail(correo.getText().toString())){
                        valCorreo=true;
                    }

                if (validar()&&gen&&valCorreo) {
                    if (!u.isNull()) {
                        Toast.makeText(this, "ERROR: Campos vacios", Toast.LENGTH_LONG).show();
                    } else if (dao.insertUsuario(u)) {
                        Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_LONG).show();
                        Intent i2 = new Intent(Registro.this, Login.class);
                        startActivity(i2);
                        finish();
                    } else {
                        Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(this, "Lo sentimos, no aceptaste los termínos y condiciones", Toast.LENGTH_LONG).show();
                }
                    break;

            case R.id.buttonCancelar:
                Intent i = new Intent(Registro.this, Login.class);
                startActivity(i);
                break;


        }

        }

    private boolean validar(){
        boolean v =  terYCond.isChecked();
        return v;
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


}
