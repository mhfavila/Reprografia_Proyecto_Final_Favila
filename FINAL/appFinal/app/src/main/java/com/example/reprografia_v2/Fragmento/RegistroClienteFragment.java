package com.example.reprografia_v2.Fragmento;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.reprografia_v2.Activity.PropiedadesLeer;
import com.example.reprografia_v2.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * clase encargada del registro del cliente
 */
public class RegistroClienteFragment extends Fragment {

    private EditText campoCorreo, campoContrasena, campoNombre, campoApellidos, campoTelefono;
    private Button botonaceptar, botonclear;

    private Properties propiedades;
    private PropiedadesLeer propiedadesleer;
    private Context contexto;

    ProgressDialog progressDialog;
    RequestQueue request;

    /**
     * @param inflater           utiliza para construir y añadir las Views a los diseños
     * @param container          contenedor
     * @param savedInstanceState la instancia actual
     * @return devuelve la vista
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro_cliente_fragment, container, false);

        botonaceptar = view.findViewById(R.id.botonaceptar);
        botonclear = view.findViewById(R.id.botonlimpiar);

        campoCorreo = view.findViewById(R.id.editemail);
        campoContrasena = view.findViewById(R.id.editpasss);
        campoNombre = view.findViewById(R.id.editnombre);
        campoApellidos = view.findViewById(R.id.editapellidos);

        campoTelefono = view.findViewById(R.id.edittelefono);

        contexto = this.getContext();
        propiedadesleer = new PropiedadesLeer(contexto);
        propiedades = propiedadesleer.getPropiedades("datos.properties");
        request = Volley.newRequestQueue(getContext());
        botonaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Entra dentro del if si los editText no están vacíos y además si el editCorreo cumple el formato de corréo.
                if (!campoCorreo.getText().toString().equals("") && !campoNombre.getText().toString().equals("") && !campoContrasena.getText().toString().equals("") && !campoApellidos.getText().toString().equals("") && !campoTelefono.getText().toString().equals("")) {


                    //Entra dentro del if si el formato de correo introducido es correcto llama al método validarCorreoBaseDatos()
                    if (validarEmail("miEmail@gmail.com")) {
                        validarCorreoBaseDatos(propiedades.getProperty("url") + "validarCorreoCliente.php");
                        //validarCorreoBaseDatos("http://192.168.1.68/proyectoPHP/validarCorreoCliente.php");

                    }

                    //Si no entra dentro del if muestra un Toast con el mensaje "Formato correo no válido"
                    else {
                        Toast.makeText(getContext(), "Formato correo no válido", Toast.LENGTH_SHORT).show();
                    }


                }

                //Si no entra dentro del if muestra en la pantalla un Toast con el mensaje "Campos vacíos"
                else {
                    Toast.makeText(getContext(), "Campos vacíos", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //Acción del botón que vacía todos los editText
        botonclear.setOnClickListener(new View.OnClickListener() {

            /**
             * para vaciar los campos del formulario
             * @param v la vista
             */
            @Override
            public void onClick(View v) {
                campoCorreo.setText("");
                campoContrasena.setText("");
                campoNombre.setText("");
                campoApellidos.setText("");

                campoTelefono.setText("");
            }
        });
        return view;

    }

    /**
     * Método que se encarga de comprobar si el formato de la cadena corresponde con la de un email
     *
     * @param email
     * @return true en caso del que el formato de la cadena coincida con la de un correo y false si no coindice con la de un correo
     */
    private boolean validarEmail(String email) {
        email = campoCorreo.getText().toString();
        Pattern patron = Patterns.EMAIL_ADDRESS;
        return patron.matcher(email).matches();
    }

    /**
     * Método  que  se le pasa como parámetro una URL la cual está  compuesta por la dirección ip pública, por el archivo al cual está llamando el método para realizar la consulta a la base
     * de datos y en este caso se encarga de comprobar si el correo que se ha insertado en el editCorreo corresponde con uno ya existente en la base de datos
     *
     * @param URL
     */
    private void validarCorreoBaseDatos(String URL) {
        //Se declara la petición
        StringRequest cadenaRespuesta = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            /**
             * Método que se realiza si la petición a la base de datos es correcta
             * @param responese
             */
            public void onResponse(String respuesta) {

                //Entra dentro del if si la respuesta no este vacío indicando que el correo existe en la base de datos. Si esto se cumple monstrara un Toast con el mensaje "correo ya usado"
                if (!respuesta.isEmpty()) {
                    Toast.makeText(getContext(), "Correo ya usado", Toast.LENGTH_SHORT).show();
                }

                //Si no entra dentro del if llama al método ejecutarRegistro()
                else {
                    ejecutarRegistro(propiedades.getProperty("url") + "insertarCliente.php");
                    // ejecutarRegistro("http://192.168.1.68/proyectoPHP/insertarCliente.php");

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
             */
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", campoCorreo.getText().toString());
                return parametros;
            }
        };

        //Objeto que procesa las peticiones hechas por la app para que la librería se encargue de ejecutarlas
        RequestQueue peticion = Volley.newRequestQueue(this.getContext());

        //Envía la solicitud
        peticion.add(cadenaRespuesta);
    }


    /**
     * Método que  se le pasa como parámetro una URL la cual está compuesta por la dirección ip pública y por el archivo al cual está llamando al archivo el cual contiene  la consulta que va a realizar a la base
     * de datos y en este caso el método se encarga de insertar datos en la tabla usuario
     *
     * @param URL
     */
    private void ejecutarRegistro(String URL) {

        //Se declara la petición
        StringRequest cadenaRespuesta = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            /**
             * Método que se procesa si la consulta realizada a la base de datos es correcta
             * @param responese
             */
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Registro correcto", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getContext(), MainActivity.class);
                //startActivity(intent);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setReorderingAllowed(true);

                fragmentTransaction.replace(R.id.container_fragment, new LoginClienteFragment());
                fragmentTransaction.commit();

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
                parametros.put("telefono", campoTelefono.getText().toString());

                return parametros;
            }
        };

        //Objeto que procesa las peticiones hechas por la app para que la librería se encarge de ejecutarlas
        RequestQueue peticion = Volley.newRequestQueue(this.getContext());

        //Envía la solicitud
        peticion.add(cadenaRespuesta);
    }
}