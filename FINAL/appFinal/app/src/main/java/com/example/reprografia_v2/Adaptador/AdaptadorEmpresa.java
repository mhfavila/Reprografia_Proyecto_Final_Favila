package com.example.reprografia_v2.Adaptador;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.reprografia_v2.Fragmento.AjustesEmpresaFragment;
import com.example.reprografia_v2.Fragmento.CerrarSesionFragment;
import com.example.reprografia_v2.Fragmento.ListaEmpresaFragment;

import java.util.ArrayList;
/**
 * clase encargade de crear la navegacion entre los fragmentos de la empresa
 */
public class AdaptadorEmpresa extends FragmentStateAdapter {
    ArrayList<Fragment> listaFragmentos2 = new ArrayList<>();
    public AdaptadorEmpresa(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    /**
     * cada fragmento tiene una posicion
     * @param position le pasamos la posicion selecionada
     * @return nos devuelve el fragmento
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = new ListaEmpresaFragment();
                break;
            case 1:
                fragment = new AjustesEmpresaFragment();
                break;
            case 2:
                fragment = new CerrarSesionFragment();
                break;


        }
        return fragment;
    }
    /**
     * el numero de fragment o secciones
     */
    @Override
    public int getItemCount() {

        return 3;

    }

    public void addFragment(Fragment fragment) {
        listaFragmentos2.add(fragment);
    }

}
