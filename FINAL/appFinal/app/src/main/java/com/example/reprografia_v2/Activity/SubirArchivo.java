package com.example.reprografia_v2.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.reprografia_v2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * desde esta clse es desde donde el cliente subira los archivos a la base de datos
 */
public class SubirArchivo extends AppCompatActivity {

    private Button btnSelect, btnUpload;
    private TextView textView;
    private EditText nombreArchivo;
    private Spinner spinner;
    private LottieAnimationView animationView;

    private int REQ_PDF = 21;
    private String encodedPDF="";
    private Properties propiedades;
    private PropiedadesLeer propiedadesleer;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_archivo);

        contexto =this;
        propiedadesleer = new PropiedadesLeer(contexto);
        propiedades = propiedadesleer.getPropiedades("datos.properties");

        nombreArchivo = findViewById(R.id.nombreArchivo);

        Log.e("myTAg", Variables.nombreEmpresa.toString());
        textView = findViewById(R.id.textView);
        btnSelect = findViewById(R.id.btnSelect);
        btnUpload = findViewById(R.id.btnUpload);
        animationView = findViewById(R.id.likeImageView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(SubirArchivo.this, Cliente.class);
                startActivity(volver);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("application/pdf");
                chooseFile = Intent.createChooser(chooseFile, "elige el archivo");
                startActivityForResult(chooseFile, REQ_PDF);


            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subirArchivo(propiedades.getProperty("url")+"upload_document1.php");


            }
        });

    }

    /**
     * subir el archivo
     * @param URL la ruta del archivo php
     */
    private void subirArchivo(String URL) {
        String[] parte = Variables.correoCliente.split("(@)");
        String nombreCliente = parte[0].trim();
        String nombre = nombreArchivo.getText().toString().trim();
        String nombreArchivo1 = nombre + "_" + nombreCliente;
        String cliente = Variables.correoCliente;
        String empresa = Variables.nombreEmpresa;
        String ruta =  Variables.url;

        if( encodedPDF.equals("")){
            Toast.makeText(SubirArchivo.this, "SELECIONA UN ARCHIVO", Toast.LENGTH_SHORT).show();
        }else{
            if (nombre.isEmpty()) {

                nombreArchivo.setError("Name Required");
                nombreArchivo.requestFocus();
                return;
            }
            StringRequest cadenaRespuesta = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("myTAg",encodedPDF);
                    Boolean like = false;
                    like=likeAnimation(animationView,R.raw.subir1,like);
                    Toast.makeText(SubirArchivo.this, "subido", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SubirArchivo.this, "Fallo", Toast.LENGTH_SHORT).show();
                    Boolean like = false;
                    like=likeAnimation(animationView,R.raw.error,like);
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("NOMBRE", nombreArchivo1.toString().trim());
                    parametros.put("CLIENTE", cliente.toString().trim());
                    parametros.put("EMPRESA", empresa.trim());
                    parametros.put("PDF", encodedPDF);
                    parametros.put("ruta",  propiedades.getProperty("url"));

                    return parametros;
                }
            };

            RequestQueue peticion = Volley.newRequestQueue(this.contexto);



            peticion.add(cadenaRespuesta);

        }
    }

    /**
     * sirbe para comenzar las animaciones
     * @param imageView donde va a ir la animacion
     * @param animation se le pasa la animacion
     * @param like si esta ya activada
     * @return
     */
    private Boolean likeAnimation(LottieAnimationView imageView,int animation,Boolean like){
        if(!like){
            imageView.setAnimation(animation);
            imageView.playAnimation();
        }
        return !like;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_PDF && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();


            try {
                InputStream inputStream = SubirArchivo.this.getContentResolver().openInputStream(path);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read(pdfInBytes);
                encodedPDF = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);


                Toast.makeText(this, "documento selecionado", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}