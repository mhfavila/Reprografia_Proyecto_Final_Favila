package com.example.reprografia_v2.DescargarDocumento;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.reprografia_v2.Activity.Empresa;
import com.example.reprografia_v2.Activity.PropiedadesLeer;
import com.example.reprografia_v2.Activity.Variables;
import com.example.reprografia_v2.DTO.DocumentoDTO;
import com.example.reprografia_v2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * la empresa podra descargar y guardar los rachivos
 */
public class DescargarArchivo extends AppCompatActivity {
    private Button btnDownload, btnSave;
    private TextView edtSn;
    private byte[] pdfInBytes;
    private String nombreArchivo;
    private int contador=0;
    private String sn;
    private LottieAnimationView animationView;
    private LottieAnimationView animationView1;
    private Properties propiedades;
    private PropiedadesLeer propiedadesleer;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargar_archivo);
        contexto =this;
        propiedadesleer = new PropiedadesLeer(contexto);
        propiedades = propiedadesleer.getPropiedades("datos.properties");
        sn = Variables.snDescargar.toString().trim();
        nombreArchivo = Variables.nombreDescargar.trim();
        Log.e("myTAg",Variables.snDescargar.toString());
        Log.e("myTAg2",nombreArchivo.toString());

        btnDownload = findViewById(R.id.btnDownload);
        btnSave = findViewById(R.id.btnSaveFile);
        animationView = findViewById(R.id.likeImageView1);
        animationView1 = findViewById(R.id.likeImageView2);

        btnSave.setVisibility(View.GONE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(DescargarArchivo.this, Empresa.class);
                startActivity(volver);
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadDocument();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isStoragePermissionGranted()) {
                    try {
                        guardar();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


    }

    /**
     * guarda el archivo y le elimina de la base de datos
     * @throws IOException
     */
    private void guardar() throws IOException {

        // String root = Environment.getExternalStorageDirectory().toString();
        String root = "/storage/self/primary/documents";

        File myDir = new File(root + "/DocumentEmpresa");

        if (!myDir.exists()) {
            myDir.mkdir();
            Toast.makeText(this, "carpeta creada", Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(this, "YA EXISTE", Toast.LENGTH_SHORT).show();
        }

        try {

            File fileLocation = new File(myDir, "PDF_"+sn+"_"+Variables.nombreDescargar.toString()+".pdf");

            FileOutputStream fos = new FileOutputStream(fileLocation);
            fos.write(pdfInBytes);
            fos.flush();
            fos.close();

            Toast.makeText(this, "GUARDADO", Toast.LENGTH_SHORT).show();
            Boolean like = false;
            like=likeAnimation(animationView,R.raw.lf30_editor_bybfimah,like);
            eliminarArchivo(propiedades.getProperty("url")+"eliminarArchivo.php");


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            e.getMessage();
            e.getLocalizedMessage();
            Boolean like = false;
            like=likeAnimation(animationView,R.raw.errordescarga,like);
            Toast.makeText(this, "No se a guardado,ya existe", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * metodo que consulta y borra de la base de datos el archivo con el sn indicado
     * @param URL
     */
    private void eliminarArchivo(String URL) {
        StringRequest cadenaRespuesta = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(DescargarArchivo.this, "el archivo se ha eliminado ,ya descargado", Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DescargarArchivo.this, "Fallo", Toast.LENGTH_SHORT).show();
                Boolean like = false;
                like=likeAnimation(animationView,R.raw.error,like);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("SN", sn);


                return parametros;
            }
        };

        RequestQueue peticion = Volley.newRequestQueue(this.contexto);



        peticion.add(cadenaRespuesta);

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

    /**
     * comprueba que los permisos de almacenamiento esten otorgados
     * @return
     */
    public boolean isStoragePermissionGranted() {
        String TAG = "Storage Permission";
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                Toast.makeText(this, "REVISA QUE LOS PERMISOS DE ALMACENAMIENTO ESTEN PERMITIDOS ", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    private void downloadDocument() {
        contador ++;

        // sn = edtSn.getText().toString().trim();
        //sn = Variables.snDescargar.toString().trim();

        if(sn.isEmpty()){

            edtSn.setError("Introduzca el id del archivo que quiere descargar");
            edtSn.requestFocus();
            return;
        }

        Call<DocumentoDTO> call = Cliente.getInstance().getAPI().downloadDocs(Integer.parseInt(sn));
        call.enqueue(new Callback<DocumentoDTO>() {


            @Override
            public void onResponse(Call<DocumentoDTO> call, Response<DocumentoDTO> response) {

                if (response.body() != null) {
                    btnDownload.setVisibility(View.GONE);
                    nombreArchivo = response.body().getNombreArchivo();

                    String encodedPdf = response.body().getEncodedPDF();
                    pdfInBytes = Base64.decode(encodedPdf, Base64.DEFAULT);

                    Boolean like = false;
                    like=likeAnimation(animationView1,R.raw.btndescargar,like);
                    btnSave.setVisibility(View.VISIBLE);




                    Toast.makeText(DescargarArchivo.this, "El archivo se puede guardar", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DescargarArchivo.this, "SN invalido ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DocumentoDTO> call, Throwable t) {
                Toast.makeText(DescargarArchivo.this, "Network Failed", Toast.LENGTH_SHORT).show();
                Boolean like = false;
                like=likeAnimation(animationView,R.raw.errordescarga,like);

            }
        });

    }
}
