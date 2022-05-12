package com.example.reprografia_v2.Fragmento;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.reprografia_v2.R;

/**
 * el fragmeto principal
 */
public class InicioFragment extends Fragment {
    @Nullable
    @Override
    /**
     * esta es la pantalla que aparece al abrirse la aplicaccion
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inicio_fragment,container,false);
        return view;
    }
}