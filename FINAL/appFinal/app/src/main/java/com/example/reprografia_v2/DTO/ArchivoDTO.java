package com.example.reprografia_v2.DTO;

public class ArchivoDTO {
    private String sn;
    private String nombreArchivo;
    private String boton;


    public ArchivoDTO(String sn, String nombreArchivo, String boton) {
        this.sn = sn;
        this.nombreArchivo = nombreArchivo;
        this.boton = boton;
    }

    public ArchivoDTO() {

    }

    public String getBoton() {
        return boton;
    }

    public void setBoton(String boton) {
        this.boton = boton;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
