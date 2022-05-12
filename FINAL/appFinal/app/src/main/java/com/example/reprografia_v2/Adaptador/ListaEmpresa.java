package com.example.reprografia_v2.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reprografia_v2.DTO.ArchivoDTO;
import com.example.reprografia_v2.R;

import java.util.List;

public class ListaEmpresa extends RecyclerView.Adapter<ListaEmpresa.ArchivosHolder> {
    List<ArchivoDTO> listaArchivos;

    public ListaEmpresa(List<ArchivoDTO> listaArchivos) {
        this.listaArchivos = listaArchivos;

    }


    @NonNull
    @Override
    /**
     *inflar la vista a partir del layout correspondiente a los elementos de la lista, y crear y devolver un nuevo ViewHolder
     */
    public ListaEmpresa.ArchivosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_archivos,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ArchivosHolder(view);
    }

    @Override
    /**
     * para ir rellenando el cardview
     */
    public void onBindViewHolder(@NonNull ListaEmpresa.ArchivosHolder holder, int position) {
        holder.sn.setText(listaArchivos.get(position).getSn().toString());
        holder.nombreArchivo.setText(listaArchivos.get(position).getNombreArchivo().toString());

    }

    @Override
    /**
     * es el tamaño de la lista
     */
    public int getItemCount() {
        return listaArchivos.size();
    }

    public class ArchivosHolder extends RecyclerView.ViewHolder {

        TextView nombreArchivo,sn;
        Button btn;
        public ArchivosHolder(@NonNull View itemView) {
            super(itemView);
            nombreArchivo=itemView.findViewById(R.id.nombreArchivo);
            sn = itemView.findViewById(R.id.sn);
            btn = (Button) itemView.findViewById(R.id.btnSNDescargar);

        }
    }
}


