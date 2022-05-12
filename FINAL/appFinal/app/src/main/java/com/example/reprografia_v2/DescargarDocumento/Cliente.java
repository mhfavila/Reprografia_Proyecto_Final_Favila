package com.example.reprografia_v2.DescargarDocumento;

import android.content.Context;

import com.example.reprografia_v2.Activity.PropiedadesLeer;
import com.example.reprografia_v2.Activity.Variables;

import java.util.Properties;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * para usar la libreria Retrofit
 */
public class Cliente {
    private Properties propiedades;
    private PropiedadesLeer propiedadesleer;
    private Context contexto;

    private static final String BASE_URL = Variables.url;

    private static Cliente myClient;
    private Retrofit retrofit;


    private Cliente() {

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    }

    public static synchronized Cliente getInstance() {
        if (myClient == null) {
            myClient = new Cliente();
        }
        return myClient;
    }
    public Api getAPI(){
        return retrofit.create(Api.class);
    }

}
