package com.example.reprografia_v2.DTO;

public class DocumentoDTO {
    private int pdfSn;
    private String pdfTitle;
    private String encodedPDF;
    private String nombreArchivo;

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public int getPdfSn() {
        return pdfSn;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public String getEncodedPDF() {
        return encodedPDF;
    }
}
