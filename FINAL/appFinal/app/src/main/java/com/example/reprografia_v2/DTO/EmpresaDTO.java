package com.example.reprografia_v2.DTO;

public class EmpresaDTO {
    private String correo;
    private String pass;
    private String nombre;
    private String precioFotocopia;
    private String direccion;
    private int telefono;
    private String boton;
    private String rutaImagen;


    public EmpresaDTO(String correo, String pass, String nombre, String precioFotocopia, String direccion, int telefono, String boton, String rutaImagen) {
        this.correo = correo;
        this.pass = pass;
        this.nombre = nombre;
        this.precioFotocopia = precioFotocopia;
        this.direccion = direccion;
        this.telefono = telefono;
        this.boton = boton;
        this.rutaImagen = rutaImagen;
    }

    public EmpresaDTO() {

    }


    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getCorreo() {
        return correo;
    }

    public String setCorreo(String correo) {
        this.correo = correo;
        return correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecioFotocopia() {
        return precioFotocopia;
    }

    public void setPrecioFotocopia(String precioFotocopia) {
        this.precioFotocopia = precioFotocopia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getBoton() {
        return boton;
    }

    public void setBoton(String boton) {
        this.boton = boton;
    }
}
