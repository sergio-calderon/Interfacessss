package com.example.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoUsuario {
    Context c;
    Usuario user;
    ArrayList<Usuario> lista;
    SQLiteDatabase sql;
    String db = "CovidDBFinal03";
    String tabla = "create table if not exists usuario(id integer primary key autoincrement, nombre text, correo text, pass text, direcc text, gen text, contagiado text)";

    public daoUsuario(Context c) {
        this.c = c;
        sql = c.openOrCreateDatabase(db, c.MODE_PRIVATE, null);
        sql.execSQL(tabla);

        user = new Usuario();
    }

    public boolean insertUsuario(Usuario user) {
        if (buscar(user.getCorreo()) == 0) {
            ContentValues ct = new ContentValues();
            ct.put("nombre", user.getNombre());
            ct.put("correo", user.getCorreo());
            ct.put("pass", user.getPassword());
            ct.put("direcc", user.getDireccion());
            ct.put("gen", user.getGenero());
            ct.put("contagiado", user.getContagiado());
            return (sql.insert("usuario", null, ct) > 0);

        } else {
            return false;
        }
    }

    public boolean updateUsuario(Usuario user){
        ContentValues ct = new ContentValues();
        ct.put("nombre", user.getNombre());
        ct.put("correo", user.getCorreo());
        ct.put("pass", user.getPassword());
        ct.put("direcc", user.getDireccion());
        ct.put("gen", user.getGenero());
        ct.put("contagiado", user.getContagiado());
        return (sql.update("usuario", ct, "id="+user.getId(), null) > 0);

    }

    public int buscar(String u) {
        int x = 0;
        lista = selectUsuarios();
        for (Usuario us : lista) {
            if (us.getCorreo().equals(u)) {
                x++;
            }
        }
        return x;
    }

    public ArrayList<Usuario> selectUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        lista.clear();
        Cursor cr = sql.rawQuery("select * from usuario", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                Usuario u = new Usuario();
                u.setId(cr.getInt(0));
                u.setNombre(cr.getString(1));
                u.setCorreo(cr.getString(2));
                u.setPassword(cr.getString(3));
                u.setDireccion(cr.getString(4));
                u.setGenero(cr.getString(5));
                u.setContagiado(cr.getString(6));
                lista.add(u);
            } while (cr.moveToNext());

        }
        return lista;
    }

    public int login(String u, String p) {
        int a = 0;
        Cursor cr = sql.rawQuery("select * from usuario", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                if (cr.getString(2).equals(u) && cr.getString(3).equals(p)) {
                    a++;

                }
            } while (cr.moveToNext());

        }
        return a;
    }

    public Usuario getUser(String u, String p) {
        lista = selectUsuarios();
        for (Usuario us : lista) {
            if (us.getCorreo().equals(u) && us.getPassword().equals(p)) {
                return us;
            }
        }
        return null;
    }



    public Usuario getUserById(int id) {
        lista = selectUsuarios();
        for (Usuario us : lista) {
            if (us.getId() == id) {
                return us;
            }
        }
        return null;
    }

    public boolean deleteUsuario(int id) {
        return (sql.delete("usuario", "id=" + id, null) > 0);
    }
}


