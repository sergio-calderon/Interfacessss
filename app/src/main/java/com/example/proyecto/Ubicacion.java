package com.example.proyecto;


import java.util.List;

public class Ubicacion {

    int id;
    String ubicacionDatos;

    public Ubicacion(){

    }

    public Ubicacion(int id, String ubicacionDatos){
        this.id = id;
        this.ubicacionDatos = ubicacionDatos;
    }

    public boolean isNull(){
        if (ubicacionDatos.equals("")){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "id=" + id +
                ", ubicacionDatos=" + ubicacionDatos +
                '}';
    }

    public int getId() {return id;}
    public void setId(int id){this.id = id;}
    public String getUbicacionDatos(){return ubicacionDatos;}
    public void setUbicacionDatos(String ubicacionDatos){this.ubicacionDatos = ubicacionDatos;}
}
