package com.example.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoUbicacion {
    Context c;
    Ubicacion ubicacion;
    ArrayList<Ubicacion> listaUbicacion;
    SQLiteDatabase sql;
    String db = "CovidDBFinal03";
    String tabla = "create table if not exists ubicacion(id integer primary key, ubicacionDatos text)";


    public daoUbicacion(Context c){
        this.c = c;
        sql = c.openOrCreateDatabase(db, c.MODE_PRIVATE, null);
        sql.execSQL(tabla);

        ubicacion = new Ubicacion();
    }

    public boolean updateUbicacion(Ubicacion ubicacion){
        ContentValues ct = new ContentValues();
        ct.put("id", ubicacion.getId());
        ct.put("ubicacionDatos", ubicacion.getUbicacionDatos());
        return (sql.update("ubicacion", ct, "id="+ubicacion.getId(), null) > 0);

    }

    public boolean insertUbicacion(Ubicacion ubicacion) {
        ContentValues ct = new ContentValues();
        ct.put("id", ubicacion.getId());
        ct.put("ubicacionDatos", ubicacion.getUbicacionDatos());
        return (sql.insert("ubicacion", null, ct) > 0);
    }

    public ArrayList<Ubicacion> selectUbicacion() {
        ArrayList<Ubicacion> lista = new ArrayList<>();
        lista.clear();
        Cursor cr = sql.rawQuery("select * from ubicacion", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                Ubicacion u = new Ubicacion();
                u.setId(cr.getInt(0));
                u.setUbicacionDatos(cr.getString(1));
                lista.add(u);
            } while (cr.moveToNext());

        }
        return lista;
    }

    public Ubicacion getUbicacionById(int id) {
        listaUbicacion = selectUbicacion();
        for (Ubicacion ub : listaUbicacion) {
            if (ub.getId() == id) {
                return ub;
            }
        }
        return null;
    }

    public ArrayList<Ubicacion> getDatosUbi(int id) {
        listaUbicacion = selectUbicacion();
        return listaUbicacion;
    }



}
