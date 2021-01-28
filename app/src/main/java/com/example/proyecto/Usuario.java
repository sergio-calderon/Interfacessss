package com.example.proyecto;

public class Usuario {
    int id;
    String nombre, correo, password, direccion, genero, contagiado;

    public Usuario() {

    }

    public Usuario(String nombre, String correo, String password, String direccion, String genero, String contagiado) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.direccion = direccion;
        this.genero = genero;
        this.contagiado = contagiado;

    }

    public boolean isNull(){
        if (nombre.equals("")&&correo.equals("")&&password.equals("")&&contagiado.equals("")){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", direccion=" + direccion + '\'' +
                ", genero=" + genero + '\'' +
                ", contagiado=" + contagiado + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getContagiado(){return contagiado;}
    public void setContagiado(String contagiado){this.contagiado = contagiado;}
}
