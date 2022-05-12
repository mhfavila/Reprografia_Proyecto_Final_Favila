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
import com.example.reprografia_v2.Activity.SubirArchivo;
import com.example.reprografia_v2.Activity.Variables;
import com.example.reprografia_v2.Adaptador.ListaCliente;
import com.example.reprografia_v2.Adaptador.Onclick;
import com.example.reprografia_v2.DTO.EmpresaDTO;
import com.example.reprografia_v2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Properties;

/**
 * donde se genera la lista que aparece en el cliente con las diferentes empresas
 */
public class ListaClienteFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    RecyclerView recyclerEmpresas;

    ProgressDialog progressDialog;
    RequestQueue request;

    JsonObjectRequest jsonObjectRequest;
    private Properties propiedades;
    private PropiedadesLeer propiedadesleer;
    private Context contexto;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_cliente_fragment, container, false);

        Variables.listaEmpresas = new ArrayList<>();
        recyclerEmpresas = (RecyclerView) view.findViewById(R.id.RecyclerId);
        recyclerEmpresas.setLayoutManager(new LinearLayoutManager(this.getContext()));
        contexto = this.getContext();
        propiedadesleer = new PropiedadesLeer(contexto);
        propiedades = propiedadesleer.getPropiedades("datos.properties");
        recyclerEmpresas.setHasFixedSize(true);

        request = Volley.newRequestQueue(getContext());
        cargarWebServices();


        recyclerEmpresas.addOnItemTouchListener(new Onclick(view.getContext(), recyclerEmpresas, new Onclick.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        Intent ircome = new Intent(view.getContext(), SubirArchivo.class);


                        Variables.nombreEmpresa = Variables.listaEmpresas.get(position).getCorreo();


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
        String url = propiedades.getProperty("url") + "listaEmpresas.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(contexto, "No se puede conectar" + error.toString(), Toast.LENGTH_SHORT).show();
        progressDialog.hide();

    }

    @Override
    public void onResponse(JSONObject response) {
        EmpresaDTO empresaDTO = null;
        JSONArray jsonArray = response.optJSONArray("empresa");

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                empresaDTO = new EmpresaDTO();
                JSONObject jsonObject = null;

                jsonObject = jsonArray.getJSONObject(i);
                empresaDTO.setNombre(jsonObject.optString("nombre"));
                empresaDTO.setCorreo(jsonObject.optString("correo"));
                String correoGuardar = empresaDTO.setCorreo(jsonObject.optString("correo"));
                empresaDTO.setPrecioFotocopia(jsonObject.optString("precioFotocopia"));
                empresaDTO.setRutaImagen(jsonObject.optString("path"));
                Variables.listaEmpresas.add(empresaDTO);
                // Variables.nombreEmpresa.add(correoGuardar);


            }
            progressDialog.hide();
            ListaCliente adapter = new ListaCliente(Variables.listaEmpresas, contexto);
            recyclerEmpresas.setAdapter(adapter);

        } catch (JSONException e) {

            e.printStackTrace();
            Toast.makeText(contexto, "No se a podido establecer la conexion con el servidor" + " " + response, Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }

    }
}
