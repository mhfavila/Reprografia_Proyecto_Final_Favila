package com.example.reprografia_v2.Adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.reprografia_v2.DTO.EmpresaDTO;
import com.example.reprografia_v2.R;

import java.util.List;

/**
 * donde se genera la lista que aparece en el cliente con las diferentes empresas
 */
public class ListaCliente extends RecyclerView.Adapter<ListaCliente.EmpresasHolder> {
    List<EmpresaDTO> listaEmpresas;
    Context context ;

    /**
     *
     * @param listaEmpresas
     * @param context
     */
    public ListaCliente(List<EmpresaDTO> listaEmpresas,Context context) {
        this.listaEmpresas = listaEmpresas;
        this.context=context;

    }


    @NonNull
    @Override
    /**
     *inflar la vista a partir del layout correspondiente a los elementos de la lista, y crear y devolver un nuevo ViewHolder
     */
    public EmpresasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_empresas, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new EmpresasHolder(view);
    }

    @Override
    /**
     * para ir rellenando el cardview
     */
    public void onBindViewHolder(@NonNull EmpresasHolder holder, int position) {
        holder.nombre.setText(listaEmpresas.get(position).getNombre().toString());
        holder.precioFotocopias.setText(listaEmpresas.get(position).getPrecioFotocopia().toString());
        holder.correo.setText(listaEmpresas.get(position).getCorreo().toString());

        if(listaEmpresas.get(position).getRutaImagen() != null){
            cargarImagenWebService(listaEmpresas.get(position).getRutaImagen(),holder);
        }else{
            holder.imagen.setImageResource(R.drawable.reprografia);
        }

    }

    /**
     * Para recibir la ruta de la imgen desde la base de datos
     * @param rutaImagen
     * @param holder
     */
    private void cargarImagenWebService(String rutaImagen, EmpresasHolder holder) {
        String urlImagen=rutaImagen;
        urlImagen=urlImagen.replace(" ","%20");
        ImageRequest imageRequest = new ImageRequest(
                urlImagen,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        holder.imagen.setImageBitmap(response);

                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(imageRequest);


    }

    @Override
    /**
     * es el tamaño de la lista
     */
    public int getItemCount() {
        return listaEmpresas.size();
    }

    /**
     *
     */
    public class EmpresasHolder extends RecyclerView.ViewHolder {
        TextView nombre, precioFotocopias, correo;
        private ImageView imagen;
        private Button botonver;

        public EmpresasHolder(@NonNull View itemView) {

            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);

            precioFotocopias = itemView.findViewById(R.id.precioFotocopias);
            correo = itemView.findViewById(R.id.correo);
            imagen = itemView.findViewById(R.id.idImagen);
            botonver =itemView.findViewById(R.id.botonver);



        }
    }

}
