package com.example.reprografia_v2.Fragmento;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.reprografia_v2.Activity.MainActivity;
import com.example.reprografia_v2.R;

/**
 * se encarga de salir del usuario y volver al menu principal
 */
public class CerrarSesionFragment extends Fragment {
    Button exit;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cerrar_sesion_fragment, container, false);
        exit = view.findViewById(R.id.cerrarSesion);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}