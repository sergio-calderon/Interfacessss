package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class TengoSintomas extends AppCompatActivity implements View.OnClickListener {

    int sintomas, id;
    Button enviar;
    daoUsuario my_daoUsuario;
    Usuario user;

    RadioButton res_01_yes, fiebre, respirar, garganta, nariz, tos, fatiga, sabor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tengo_sintomas);

        sintomas = 0;
        enviar = (Button) findViewById(R.id.Enviar);
        enviar.setOnClickListener(this);

        res_01_yes = (RadioButton) findViewById(R.id.Res_01_01);
        fiebre = (RadioButton) findViewById(R.id.Fiebre);
        respirar = (RadioButton) findViewById(R.id.Respirar);
        garganta = (RadioButton) findViewById(R.id.Garganta);
        nariz = (RadioButton) findViewById(R.id.Nariz);
        tos = (RadioButton) findViewById(R.id.Tos);
        fatiga = (RadioButton) findViewById(R.id.Fatiga);
        sabor = (RadioButton) findViewById(R.id.Sabor);

        Bundle b=getIntent().getExtras();
        id=b.getInt("id");
        my_daoUsuario=new daoUsuario(this);
        user=my_daoUsuario.getUserById(id);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Enviar:
                resultadoCovid();
                Intent mainActivity = new Intent(TengoSintomas.this, MainActivity.class);
                mainActivity.putExtra("id",user.getId());
                startActivity(mainActivity);
                break;
        }
    }

    public void resultadoCovid(){
        if(res_01_yes.isChecked()){
            sintomas += 40;
        }
        if(fiebre.isChecked()){
            sintomas += 10;
        }
        if(respirar.isChecked()){
            sintomas += 10;
        }
        if(garganta.isChecked()){
            sintomas += 5;
        }
        if(nariz.isChecked()){
            sintomas += 10;
        }
        if(tos.isChecked()){
            sintomas += 5;
        }
        if(fatiga.isChecked()){
            sintomas += 10;
        }
        if(sabor.isChecked()){
            sintomas += 10;
        }

        String contagio = "";

        if(sintomas <= 20){
            contagio = "Probabilidad Baja";
        }
        if(sintomas > 20 && sintomas <= 70){
            contagio = "Probabilidad Media";
        }
        if(sintomas > 70 && sintomas <= 100){
            contagio = "Probabilidad Alta";
        }

        user.setContagiado(contagio);
        if(my_daoUsuario.updateUsuario(user)){
            Toast.makeText(this, "Sintomas UPDATED", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Sintomas Can not be UPDATED", Toast.LENGTH_LONG).show();
        }

    }
}