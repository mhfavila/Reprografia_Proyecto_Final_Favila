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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.reprografia_v2.Activity.Empresa;
import com.example.reprografia_v2.Activity.PropiedadesLeer;
import com.example.reprografia_v2.Activity.Variables;
import com.example.reprografia_v2.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * clase encargada del login de empresa
 */
public class LoginEmpresaFragment extends Fragment {
    private TextView registro;
    private EditText campoCorreo, campoContrasena;
    private Button aceptar;
    private Properties propiedades;
    private PropiedadesLeer propiedadesleer;
    private Context contexto;

    /**
     * login empresa
     *
     * @param inflater           utiliza para construir y añadir las Views a los diseños
     * @param container          contenedor
     * @param savedInstanceState la instancia actual
     * @return devuelve la vista
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_empresa_fragment, container, false);

        campoCorreo = view.findViewById(R.id.editTextuser);
        campoContrasena = view.findViewById(R.id.editTextpass);
        aceptar = view.findViewById(R.id.botonlogin);
        contexto = this.getContext();
        propiedadesleer = new PropiedadesLeer(contexto);
        propiedades = propiedadesleer.getPropiedades("datos.properties");
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Entra dentro del if si el cotenido del editTextcorreo y  de editTextpass no estan vacíos. Si entra dentro llama al método validarAlumno()
                if (!campoCorreo.getText().toString().equals("") && !campoContrasena.getText().toString().equals("")) {
                    validarEmpresa(propiedades.getProperty("url") + "validarEmpresa.php");

                }


                // Si no entra dentro del if muestra  un Toast con el mensaje "Datos Vacíos"
                else {
                    Toast.makeText(getContext(), "Datos Vacíos", Toast.LENGTH_SHORT).show();
                }

            }
        });
        registro = view.findViewById(R.id.editTextCliqueable);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(v.getContext(), registroEmpresa.class);
                //startActivity(intent);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setReorderingAllowed(true);

                fragmentTransaction.replace(R.id.container_fragment, new RegistroEmpresaFragment());
                fragmentTransaction.commit();

            }
        });

        return view;
    }

    /**
     * Método que le pasa como parámetro una URL la cual esta compuesta por la dirección ip pública, por el archivo al cual esta llamando el método para realizar la consulta a la base
     * de datos y en este caso se encarga de comprobar si el correo que se ha insertado en el correo y la contraseña inserta en el password corresponde con uno ya existente en la base de datos
     *
     * @param URL url del archivo PHP
     */
    private void validarEmpresa(String URL) {

        //Se declara la petición
        StringRequest cadenaRespuesta = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String respuesta) {

                //Entra dentro del if si el response no este vacio indicando que el correo y la contraseña existen en la base de datos. Si esto se cumple llama al método buscar el cual se le pasa como parámetro
                //el valor que contenga campoCorreo
                if (!respuesta.isEmpty()) {


                    Variables.correoEmpresa = campoCorreo.getText().toString();
                    Intent intent = new Intent(getActivity(), Empresa.class);


                    startActivity(intent);


                }

                //Si no entra dentro del if monstrara un Toast con el mensaje "Datos incorrectos"
                else {
                    Toast.makeText(getContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            /**
             * Método que muestra por pantalla un Toast con el error producido en caso de que la consulta a la base de datos
             * de error
             * @param error
             */
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "no tiene acceso al servidor", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            /**
             * Método que se encarga de enviar los parámetros al servidor
             * @return un mapa con los parámetros que se van a enviar al servidor.
             * @throws AuthFailureError
             */
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", campoCorreo.getText().toString());
                parametros.put("pass", campoContrasena.getText().toString());

                return parametros;
            }
        };

        //Objeto que procesa las peticiones hechas por la app para que la librería se encargue de ejecutarlas
        RequestQueue peticion = Volley.newRequestQueue(this.getContext());

        //Envía la solicitud
        peticion.add(cadenaRespuesta);
    }
}