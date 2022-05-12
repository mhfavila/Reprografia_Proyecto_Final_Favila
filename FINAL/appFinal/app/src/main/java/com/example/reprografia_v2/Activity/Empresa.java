package com.example.reprografia_v2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.example.reprografia_v2.Adaptador.AdaptadorEmpresa;
import com.example.reprografia_v2.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * en esta activity tenemos la empresa y desde aqui abrimos los otros fragments
 */
public class Empresa extends AppCompatActivity {
    AdaptadorEmpresa adapter;
    ViewPager2 viewPager2;

    /**
     * creamos el adaptador para los fragmentos
     * @param savedInstanceState la instancia actual
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        Log.i("mytag",Variables.correoEmpresa.toString());
        viewPager2 = findViewById(R.id.viewPager2);
        adapter = new AdaptadorEmpresa(getSupportFragmentManager(), getLifecycle());



        viewPager2.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        int[] icon = new int[]{
                R.drawable.ic_action_list,
                R.drawable.ic_action_setting,
                R.drawable.ic_action_exit
        };

        new TabLayoutMediator(tabLayout,viewPager2,(tab, position) ->tab.setIcon(icon[position])).attach();

    }
}