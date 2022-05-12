package com.example.reprografia_v2.Fragmento;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.reprografia_v2.R;

/**
 * fragmento que se desarollara mas adelante
 */
public class FuturoFragment extends Fragment {
    private onFragmentBtnSelected listener;

    /**
     * para escuchar que fragmento se a selecionado
     * @param inflater utiliza para construir y añadir las Views a los diseños
     * @param container contenedor
     * @param savedInstanceState la instancia actual
     * @return devuelve la vista
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.futuro_fragment, container, false);
        Button clickme =view.findViewById(R.id.clickMe);
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonSelected();
            }
        });
        return view;
    }

    /**
     *
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        if (context instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement listener");
        }


    }

    /**
     * boton para cambiar de fragment
     */
    public interface onFragmentBtnSelected {
        public void onButtonSelected();
    }
}