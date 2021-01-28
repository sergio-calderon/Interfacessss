package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Contagio extends AppCompatActivity implements View.OnClickListener{

    int id;
    Ubicacion my_ubicacion;
    daoUsuario my_daoUsuario;
    Usuario user, otrosUsers;
    daoUbicacion my_daoUbicacion;
    ArrayList<Ubicacion> ubicacionUsuarios;
    ArrayList<String> obtnUbicacionOtros;

    Double my_longitud, my_latitud;

    Button colorContagio, regresoInicio, irMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contagio);

        Bundle b=getIntent().getExtras();
        id=b.getInt("id");
        my_daoUsuario=new daoUsuario(this);
        user=my_daoUsuario.getUserById(id);

        my_daoUbicacion = new daoUbicacion(this);
        ubicacionUsuarios = my_daoUbicacion.getDatosUbi(id);
        obtnUbicacionOtros = new ArrayList<String>();

        colorContagio = (Button) findViewById(R.id.ColorContagio);
        regresoInicio = (Button) findViewById(R.id.RegresoInicio);
        irMapa = (Button) findViewById(R.id.IrMapa);

        my_longitud = 0.0;
        my_latitud = 0.0;

        regresoInicio.setOnClickListener(this);
        irMapa.setOnClickListener(this);
        ejecutarAlgoritmo();

    }

    public void ejecutarAlgoritmo(){
        String mi_ubicacion = "";
        for (int i = 0; i <ubicacionUsuarios.size(); i++){
            if(ubicacionUsuarios.get(i).id != id){
                otrosUsers = my_daoUsuario.getUserById(ubicacionUsuarios.get(i).id);
                if(!otrosUsers.getContagiado().equals("Probabilidad Baja")){
                    obtnUbicacionOtros.add(ubicacionUsuarios.get(i).ubicacionDatos);
                }
            }else{
                mi_ubicacion = ubicacionUsuarios.get(i).ubicacionDatos;
                System.out.println("MI UBICACION: " + mi_ubicacion);
            }
        }
        System.out.println("DATOS OTROS   "+ obtnUbicacionOtros);
        //Primero separamos mi ubicación
        String array_mi_ubicacion [] = mi_ubicacion.split("--");

        boolean encuentro_contagio = false;

        for(int i = 0; i < array_mi_ubicacion.length; i++){
            String datos[] = array_mi_ubicacion[i].split(";");
            for(int j = 0; j < obtnUbicacionOtros.size(); j++ ){
                String array_otros_ubicacion[] = obtnUbicacionOtros.get(j).split("--");
                for (int k = 0; k < array_otros_ubicacion.length; k++){
                    String datos_otros[] = array_otros_ubicacion[k].split(";");
                    if(datos_otros[2].equals(datos[2])){
                        System.out.println("VOY A COMPARAR METROS");
                        Double lat_01 = Double.parseDouble(datos[1]);
                        Double lat_02 = Double.parseDouble(datos_otros[1]);
                        Double long_01 = Double.parseDouble(datos[0]);
                        Double long_02 = Double.parseDouble(datos_otros[0]);

                        this.my_latitud = lat_01;
                        this.my_longitud = long_01;
                        terminar(calcular_distancia(lat_01, lat_02, long_01, long_02), datos_otros[2]);
                        encuentro_contagio = true;
                    }
                }
            }
        }

        if(!encuentro_contagio){
            terminar_02();
        }
    }

    public Double calcular_distancia(double lat_01, double lat_02, double long_01, double long_02){
        Double radio_tierra = 6371000.0;
        Double delta_lat = lat_02 - lat_01;
        Double delta_long = long_02 - long_01;
        Double a = Math.pow(Math.sin(delta_lat/2) ,2) + Math.cos(lat_01) * Math.cos(lat_02) *  Math.pow(Math.sin(delta_long/2) ,2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)) ;
        Double distancia = radio_tierra * c;
        System.out.println("MI CCCC ES: " + distancia);
        return distancia;
    }

    public void terminar(Double distancia, String hora_contacto){
        String mensaje = "";
        TextView my_mensaje = (TextView) findViewById(R.id.Mensaje);
        if(distancia<= 2.0){
            mensaje += user.getNombre() + " !" +"\n"+"\n"+
                    "Estuviste en contacto con una persona con probabilidad alta de tener Covid19"+"\n"+"\n"+
                    "Aproximadamente el día: " + hora_contacto +"\n"+"\n"+
                    "Porfavor en estos días rellena el formulario en caso de tener sintomas";
            colorContagio.setBackgroundColor(Color.RED);
        }else{
            mensaje += user.getNombre() + " " +"\n"+
                    "Tuviste un viaje tranquilo";
            colorContagio.setBackgroundColor(Color.GREEN);
        }
        my_mensaje.setText(mensaje);
    }

    public void terminar_02(){
        String mensaje = "";
        TextView my_mensaje = (TextView) findViewById(R.id.Mensaje);
        mensaje += user.getNombre() + " " +"\n"+
                "Tuviste un viaje tranquilo";
        colorContagio.setBackgroundColor(Color.GREEN);
        my_mensaje.setText(mensaje);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.IrMapa:
                Intent mapActivity = new Intent(Contagio.this, MapsActivity.class);
                mapActivity.putExtra("longitud", this.my_longitud);
                mapActivity.putExtra("latitud", this.my_latitud);
                startActivity(mapActivity);
                break;
            case R.id.RegresoInicio:
                Intent mainActivity = new Intent(Contagio.this, MainActivity.class);
                mainActivity.putExtra("id",user.getId());
                startActivity(mainActivity);
                break;
        }
    }

    public Double getLatitud(){
        return this.my_latitud;
    }
    public Double getLongitud(){
        return this.my_longitud;
    }
}