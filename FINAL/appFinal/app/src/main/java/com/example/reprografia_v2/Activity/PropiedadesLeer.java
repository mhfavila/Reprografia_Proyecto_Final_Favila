package com.example.reprografia_v2.Activity;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que se encarga de leer las propiedades que haya en el archivo llamado datos.properties
 *
 * @author Jorge
 */
public class PropiedadesLeer {
    private Context contexto;
    private Properties propiedades;

    /**
     * Método que se encarga de leer el contexto
     * @param contexto
     */
    public PropiedadesLeer(Context contexto) {
        this.contexto = contexto;
        propiedades = new Properties();
    }

    /**
     * Método que se encarga de abrir el archivo datos.properties para poder sacar el valor de la propiedad que se le pasa como parámetro
     * @param archivo
     * @return el valor que tenga la propiedad que se le pasa como parámetro
     */
    public Properties getPropiedades(String archivo) {
        try {
            AssetManager am = contexto.getAssets();
            InputStream flujoEntrada = am.open(archivo);
            propiedades.load(flujoEntrada);
        } catch (IOException e) {
            Log.e("PropertiesReader", e.toString());
        }
        return propiedades;
    }

}

