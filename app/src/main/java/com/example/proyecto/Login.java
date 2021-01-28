package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {
EditText email,pass;
Button buttonIngreso, buttonRegistro;
daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email=(EditText)findViewById(R.id.Email);
        pass=(EditText)findViewById(R.id.Pass);

        buttonIngreso=(Button)findViewById(R.id.buttonIngreso);
        buttonRegistro=(Button)findViewById(R.id.buttonRegistro);

        buttonIngreso.setOnClickListener(this);
        buttonRegistro.setOnClickListener(this);

        dao=new daoUsuario(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonIngreso:
                String u=email.getText().toString();
                String p=pass.getText().toString();
                if (u.equals("")&&p.equals("")){
                    Toast.makeText(this,"ERROR: Campos vacíos",Toast.LENGTH_LONG).show();
                }else if (dao.login(u,p)==1){
                    Usuario ux=dao.getUser(u,p);
                    Toast.makeText(this,"Datos correctos",Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(Login.this, MainActivity.class);
                    i2.putExtra("id",ux.getId());
                    startActivity(i2);
                }





                break;

                case R.id.buttonRegistro:
                    Intent i = new Intent(Login.this, Registro.class);
                    startActivity(i);
                    break;

        }

    }
}