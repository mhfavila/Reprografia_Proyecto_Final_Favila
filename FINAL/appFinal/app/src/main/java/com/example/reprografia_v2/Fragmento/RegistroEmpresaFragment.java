package com.example.reprografia_v2.Fragmento;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * clase encargada del registro del empresa
 */
public class RegistroEmpresaFragment extends Fragment {
    private EditText campoCorreo, campoContrasena, campoNombre, campoPrecioFotocopia, campoDireccion, campoTelefono;
    private Button botonaceptar, botonclear;

    private Properties propiedades;
    private PropiedadesLeer propiedadesleer;
    private Context contexto;
    ImageView imageView;
    Button btnTakePicture;
    String currentPhotoPath;
    Bitmap decoded;

    RequestQueue requestQueue;
    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_TAKE_PHOTO = 101;
    /**
     * @param inflater           utiliza para construir y añadir las Views a los diseños
     * @param container          contenedor
     * @param savedInstanceState la instancia actual
     * @return devuelve la vista
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro_empresa_fragment, container, false);
        // Intent intent = new Intent(getActivity(), registrarEmpresa.class);
        // startActivity(intent);

        botonaceptar = view.findViewById(R.id.botonaceptar);
        botonclear = view.findViewById(R.id.botonlimpiar);

        campoCorreo = view.findViewById(R.id.editemail);
        campoContrasena = view.findViewById(R.id.editpasss);
        campoNombre = view.findViewById(R.id.editnombre);
        campoPrecioFotocopia = view.findViewById(R.id.precioFotocopia);
        campoDireccion = view.findViewById(R.id.editdireccion);

        campoTelefono = view.findViewById(R.id.edittelefono);
        contexto =this.getContext();
        propiedadesleer = new PropiedadesLeer(contexto);
        propiedades = propiedadesleer.getPropiedades("datos.properties");

        imageView = view.findViewById(R.id.picture);
        btnTakePicture = view.findViewById(R.id.btnTakePicture);


        requestQueue = Volley.newRequestQueue(this.getContext());


        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        botonaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Entra dentro del if si los editText no están vacíos y además si el editCorreo cumple el formato de corréo.
                if (!campoCorreo.getText().toString().equals("") && !campoNombre.getText().toString().equals("") && !campoContrasena.getText().toString().equals("") && !campoPrecioFotocopia.getText().toString().equals("") && !campoDireccion.getText().toString().equals("") && !campoTelefono.getText().toString().equals("")) {


                    //Entra dentro del if si el formato de correo introducido es correcto llama al método validarCorreoBaseDatos()
                    if (validarEmail("miEmail@gmail.com")) {
                        validarCorreoBaseDatos(propiedades.getProperty("url") + "validarCorreoEmpresa.php");
                        //validarCorreoBaseDatos("http://192.168.1.68/proyectoPHP/validarCorreoCliente.php");

                    }

                    //Si no entra dentro del if muestra un Toast con el mensaje "Formato correo no válido"
                    else {
                        Toast.makeText(contexto, "Formato correo no válido", Toast.LENGTH_SHORT).show();
                    }


                }

                //Si no entra dentro del if muestra en la pantalla un Toast con el mensaje "Campos vacíos"
                else {
                    Toast.makeText(contexto, "Campos vacíos", Toast.LENGTH_SHORT).show();
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
                campoPrecioFotocopia.setText("");
                campoDireccion.setText("");
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
                Toast.makeText(contexto, "Registro correcto", Toast.LENGTH_SHORT).show();

                // Intent i = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(i);



                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setReorderingAllowed(true);

                fragmentTransaction.replace(R.id.container_fragment, new LoginEmpresaFragment());
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
                Toast.makeText(contexto, error.toString(), Toast.LENGTH_SHORT).show();

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
                parametros.put("precioFotocopia", campoPrecioFotocopia.getText().toString()+" cent/hoja");
                parametros.put("direccion", campoDireccion.getText().toString());
                parametros.put("telefono", campoTelefono.getText().toString());
                parametros.put("path", getStringImage(decoded));
                parametros.put("ruta",  propiedades.getProperty("url"));


                return parametros;
            }
        };

        //Objeto que procesa las peticiones hechas por la app para que la librería se encarge de ejecutarlas
        RequestQueue peticion = Volley.newRequestQueue(this.contexto);


        //Envía la solicitud
        peticion.add(cadenaRespuesta);
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
                    Toast.makeText(contexto, "Correo ya usado", Toast.LENGTH_SHORT).show();
                }

                //Si no entra dentro del if llama al método ejecutarRegistro()
                else {
                    ejecutarRegistro(propiedades.getProperty("url") + "insertarEmpresa.php");
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
                Toast.makeText(contexto, "no tiene acceso al servidor", Toast.LENGTH_SHORT).show();

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
        RequestQueue peticion = Volley.newRequestQueue(this.contexto);


        //Envía la solicitud
        peticion.add(cadenaRespuesta);
    }

    /**
     * se encarde revisar si estan habilitados los permisos de la camara
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                takePictureFullSize();
            } else {
                ActivityCompat.requestPermissions(
                        this.getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_PERMISSION_CAMERA
                );
            }
        } else {
            takePictureFullSize();
        }
    }

    /**
     * se encarga de tomar la imagen del registro
     */
    private void takePictureFullSize() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photofile = null;
            try {
                photofile = createImageFile();

            } catch (IOException e) {
                e.getMessage();
            }
            if (photofile != null) {
                Uri photouri = FileProvider.getUriForFile(
                        this.getContext(),
                        "com.example.reprografia_v2",
                        photofile
                );
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    /**
     * crea el archivo de la imagen
     * @return nos devuelve la imagen
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss", Locale.getDefault()).format(new Date());
        String imgFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imgFileName,
                ".jpg",
                storageDir
        );



        currentPhotoPath = image.getAbsolutePath();

        return image;
    }

    /**
     * guarda la foto y corrige la orientacion para que quede en vertical
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    File file = new File(currentPhotoPath);
                    Uri uri = Uri.fromFile(file);
                    ExifInterface ei = new ExifInterface(file);//para rotar la imgane que tomamos desde la camara
                    int orientacion = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);



                    Matrix matrix = new Matrix();
                    switch(orientacion) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.postRotate(90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.postRotate(180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            break;
                    }



                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), uri);

                    Bitmap imageBitmapNuevo = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);




                    setToImageView(getResizedBitmap(imageBitmapNuevo, 1024));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ajusta la imagen
     * @param bitmap la imagen
     * @param maxSize el tamaño maximo
     * @return la imgen ajustada
     */
    private Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= maxSize && height <= maxSize) {
            return bitmap;

        }
        float bitmapRadio = (float) width / (float) height;
        if (bitmapRadio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRadio);

        } else {
            height = maxSize;
            width = (int) (height * bitmapRadio);

        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);

    }

    /**
     *
     * @param bitmap
     */
    private void setToImageView(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        imageView.setImageBitmap(
                decoded
        );


    }

    /**
     * tomar la imagen
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePictureFullSize();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * codifica la imgen para poder subirla
     * @param bitmap la imagen
     * @return la imagen codificada
     */
    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);

    }


}