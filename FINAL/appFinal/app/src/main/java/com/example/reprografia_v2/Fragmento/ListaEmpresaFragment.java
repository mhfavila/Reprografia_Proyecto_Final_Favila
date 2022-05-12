package com.example.reprografia_v2.Fragmento;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.reprografia_v2.Activity.PropiedadesLeer;
import com.example.reprografia_v2.Activity.Variables;
import com.example.reprografia_v2.Adaptador.ListaEmpresa;
import com.example.reprografia_v2.Adaptador.Onclick;
import com.example.reprografia_v2.DTO.ArchivoDTO;
import com.example.reprografia_v2.DescargarDocumento.DescargarArchivo;
import com.example.reprografia_v2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Properties;

/**
 * donde se genera la lista que le aparece a la empresa con los diferentes archivos
 */
public class ListaEmpresaFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    RecyclerView recyclerArchivos;
    ArrayList<ArchivoDTO> listaArchivos;
    ProgressDialog progressDialog;
    RequestQueue request;

    JsonObjectRequest jsonObjectRequest;
    private Properties propiedades;
    private PropiedadesLeer propiedadesleer;
    private Context contexto;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_empresa_fragment, container, false);


        listaArchivos = new ArrayList<>();
        recyclerArchivos = (RecyclerView) view.findViewById(R.id.RecyclerId1);
        recyclerArchivos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        contexto = this.getContext();
        propiedadesleer = new PropiedadesLeer(contexto);
        propiedades = propiedadesleer.getPropiedades("datos.properties");
        recyclerArchivos.setHasFixedSize(true);

        request = Volley.newRequestQueue(getContext());
        cargarWebServices();
        //mete una accion al clicar en el juego

        recyclerArchivos.addOnItemTouchListener(new Onclick(view.getContext(), recyclerArchivos, new Onclick.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        Intent ircome = new Intent(view.getContext(), DescargarArchivo.class);


                        Variables.snDescargar = listaArchivos.get(position).getSn();
                        Variables.nombreDescargar = listaArchivos.get(position).getNombreArchivo();


                        startActivity(ircome);

                    }

                    /**
                     * te lleva a la activity para subir los archivos
                     * @param view
                     * @param position
                     */
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );


        return view;
    }

    /**
     * carga la lista desde la BBDD
     */
    private void cargarWebServices() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Consultando....");
        progressDialog.show();
        String empresa1 = Variables.correoEmpresa;
        String url = propiedades.getProperty("url") + "listaArchivos.php?empresa=" + empresa1 + "";


        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, this, this);
        request.add(jsonObjectRequest);

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(contexto, "NO LE HAN SUBIDO NINGUN ARCHIVO POR EL MOMENTO", Toast.LENGTH_SHORT).show();

        Log.e("myTAG", error.toString());
        progressDialog.hide();

    }

    @Override
    public void onResponse(JSONObject response) {
        ArchivoDTO archivoDTO = null;
        JSONArray jsonArray = response.optJSONArray("subir");
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                archivoDTO = new ArchivoDTO();
                JSONObject jsonObject = null;

                jsonObject = jsonArray.getJSONObject(i);
                archivoDTO.setSn(jsonObject.optString("sn"));
                archivoDTO.setNombreArchivo(jsonObject.optString("nombreArchivo"));


                listaArchivos.add(archivoDTO);

            }
            progressDialog.hide();
            ListaEmpresa adapter = new ListaEmpresa(listaArchivos);
            recyclerArchivos.setAdapter(adapter);

        } catch (JSONException e) {

            e.printStackTrace();
            Toast.makeText(contexto, "No se a podido establecer la conexion con el servidor" + " " + response, Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }

    }
}
