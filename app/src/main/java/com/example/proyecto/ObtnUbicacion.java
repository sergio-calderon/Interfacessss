package com.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;


public class ObtnUbicacion extends AppCompatActivity implements View.OnClickListener {

    public static class Datos {
        String latitud;
        String longitud;
        String hora;
    }

    private List<Datos> my_datos;
    private boolean actualizar_ubicacion;
    int id;
    LocationManager locationManager;
    Button finViaje, obtnData, contagio;
    TextView ubicaciones;

    //variables de otras clases
    Ubicacion my_ubicacion;
    daoUbicacion my_daoUbicacion;
    daoUsuario my_daoUsuario;
    Usuario user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obtnubicacion);

        my_ubicacion = new Ubicacion();
        my_daoUbicacion = new daoUbicacion(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        my_datos = new ArrayList<Datos>();

        finViaje = (Button) findViewById(R.id.FinViaje);
        obtnData = (Button) findViewById(R.id.ObtnInfo);
        contagio = (Button) findViewById(R.id.contagio);

        ubicaciones = (TextView) findViewById(R.id.my_ubicar);

        actualizar_ubicacion = true;
        finViaje.setOnClickListener(this);
        obtnData.setOnClickListener(this);
        contagio.setOnClickListener(this);

        Bundle b=getIntent().getExtras();
        id=b.getInt("id");
        my_daoUsuario=new daoUsuario(this);
        user=my_daoUsuario.getUserById(id);

    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void toggleGPSUpdates(View view)  {

        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals("Ejecutando...")) {
            locationManager.removeUpdates(locationListenerGPS);
            button.setText("Iniciar Viaje");
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 0, locationListenerGPS);
            button.setText("Ejecutando...");

        }
    }

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Datos var_data = new Datos();
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    var_data.longitud = "" + location.getLongitude();
                    var_data.latitud = "" + location.getLatitude();
                    var_data.hora = date;
                    // Called when a new location is found by the network location provider.
                    my_datos.add(var_data);
                    Toast.makeText(ObtnUbicacion.this, "GPS Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}
        @Override
        public void onProviderDisabled(String s) {}
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FinViaje:
                toggleGPSUpdates(v);
                break;

            case R.id.ObtnInfo:
                imprimirUbicaciones();
                break;
            case R.id.contagio:
                //validar contagio
                Intent contagio = new Intent(ObtnUbicacion.this, Contagio.class);
                contagio.putExtra("id",user.getId());
                startActivity(contagio);
                break;
        }
    }



    public void imprimirUbicaciones() {
        String dato = "Tus ubicaciones del día son: \n";
        String ubicacion_db = "";
        my_ubicacion = new Ubicacion();

        for (int i = 0; i < my_datos.size(); i++) {
            dato += "Ubicación: " + my_datos.get(i).hora + " Long: " + my_datos.get(i).longitud + " Latitud: " + my_datos.get(i).latitud + "\n";
            ubicacion_db += "" + my_datos.get(i).longitud + ";" + "" + my_datos.get(i).latitud + ";" + "Hr:"+ my_datos.get(i).hora + "--";
        }
        my_ubicacion.setId(id);
        my_ubicacion.setUbicacionDatos(ubicacion_db);
        System.out.println("MIS UBICACIONES: " + ubicacion_db);

        if (my_daoUbicacion.insertUbicacion(my_ubicacion)) {
            Toast.makeText(this, "Registro de ubicación COMPLETED", Toast.LENGTH_LONG).show();
        }else{
            if(my_daoUbicacion.updateUbicacion(my_ubicacion)){
                Toast.makeText(this, "Registro de ubicación UPDATED", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Im sorry i cant do whatever you need", Toast.LENGTH_LONG).show();
            }
        }

        ubicaciones.setText(dato);
        actualizar_ubicacion = false;

    }
}
