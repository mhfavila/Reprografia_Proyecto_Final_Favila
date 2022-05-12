package com.example.reprografia_v2.Fragmento;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.reprografia_v2.Activity.MainActivity;
import com.example.reprografia_v2.Activity.PropiedadesLeer;
import com.example.reprografia_v2.Activity.Variables;
import com.example.reprografia_v2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * desde aqui se podran modificar ciertos datos del cliente
 */
public class AjustesClienteFragment extends Fragment {
    Button editar,buscar;
    private EditText campoContrasena, campoNombre, campoApellidos;
    private TextView campoCorreo,campoTelefono;
    RequestQueue peticion;
    private Properties propiedades;
    private PropiedadesLeer propiedadesleer;
    private Context contexto;
    private String telfonomovil;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ajustes_cliente_fragment, container, false);
        campoCorreo=view.findViewById(R.id.editemail);
        campoContrasena = view.findViewById(R.id.editpasss);
        campoNombre = view.findViewById(R.id.editnombre);
        campoApellidos = view.findViewById(R.id.editapellidos);
        campoTelefono = view.findViewById(R.id.edittelefono);
        editar = view.findViewById(R.id.editar);

        buscar = view.findViewById(R.id.buscar);
        contexto = this.getContext();
        propiedadesleer = new PropiedadesLeer(contexto);
        propiedades = propiedadesleer.getPropiedades("datos.properties");

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoCorreo.setText(Variables.correoCliente);
                buscar(propiedades.getProperty("url") + "buscarCliente.php?correo="+ Variables.correoCliente +"");

                telfonomovil = campoTelefono.getText().toString();
            }
        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarActualizacion(propiedades.getProperty("url") + "actualizarCliente.php?correo="+ Variables.correoCliente +"");

            }
        });
/*
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }); */
        return view;
    }


    /**
     * Método que  se le pasa como parámetro una URL la cual está compuesta por la dirección ip pública y por el archivo al cual está llamando al archivo el cual contiene  la consulta que va a realizar a la base
     * de datos y en este caso el método se encarga de insertar datos en la tabla usuario
     *
     * @param URL
     */
    private void ejecutarActualizacion(String URL) {

        //Se declara la petición
        StringRequest cadenaRespuesta = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            /**
             * Método que se procesa si la consulta realizada a la base de datos es correcta
             * @param responese
             */
            public void onResponse(String response) {

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "ACTUALIZADO", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            /**
             * Método que muestra por pantalla un Toast con el error producido en caso de que la consulta a la base de datos
             * de error
             * @param error
             */
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            /**
             * Método que se encarga de enviar los parámetros al servidor
             * @return un mapa con los parámetros que se van a enviar al servidor.
             * @throws AuthFailureError
             */
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", campoCorreo.getText().toString());
                parametros.put("pass", campoContrasena.getText().toString());
                parametros.put("nombre", campoNombre.getText().toString());
                parametros.put("apellidos", campoApellidos.getText().toString());
                //parametros.put("telefono", campoTelefono.getText().toString());


                return parametros;
            }
        };

        //Objeto que procesa las peticiones hechas por la app para que la librería se encarge de ejecutarlas
        peticion = Volley.newRequestQueue(this.getContext());

        //Envía la solicitud
        peticion.add(cadenaRespuesta);
    }

    /**
     * metodo para buscar el cliente
     * @param URL es la url del archivo php para el web service
     */
    private void buscar(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        campoContrasena.setText(jsonObject.getString("pass"));
                        campoNombre.setText(jsonObject.getString("nombre"));
                        campoApellidos.setText(jsonObject.getString("apellidos"));
                        campoTelefono.setText(jsonObject.getString("telefono"));

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        }
        );
        peticion = Volley.newRequestQueue(this.getContext());
        peticion.add(jsonArrayRequest);

    }

}
