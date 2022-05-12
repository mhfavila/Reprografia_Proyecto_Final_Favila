package com.example.reprografia_v2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.reprografia_v2.Fragmento.FuturoFragment;
import com.example.reprografia_v2.Fragmento.InformacionFragment;
import com.example.reprografia_v2.Fragmento.InicioFragment;
import com.example.reprografia_v2.Fragmento.LoginClienteFragment;
import com.example.reprografia_v2.Fragmento.LoginEmpresaFragment;
import com.example.reprografia_v2.Fragmento.RegistroClienteFragment;
import com.example.reprografia_v2.Fragmento.RegistroEmpresaFragment;
import com.example.reprografia_v2.R;
import com.google.android.material.navigation.NavigationView;

/**
 * la actividad principal
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FuturoFragment.onFragmentBtnSelected {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    /**
     * creamos y damos forma al menu lateral y los fragmentos principales
     * @param savedInstanceState la instancia actual
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setTitle("");
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        //load default fragment-cargar el fragmeto predeterminado
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new InicioFragment());
        fragmentTransaction.commit();
    }

    /**
     * desde aqui es desde donde se escucha la opcion que seleciona el usuario
     * @param menuItem le pasamos el menu
     * @return nos devuelve la opcion selecionada
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (menuItem.getItemId() == R.id.LoginCliente) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new LoginClienteFragment());
            fragmentTransaction.commit();

        }
        if (menuItem.getItemId() == R.id.RegistroCliente) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new RegistroClienteFragment());
            fragmentTransaction.commit();

        }if (menuItem.getItemId() == R.id.LoginEmpresa) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new LoginEmpresaFragment());
            fragmentTransaction.commit();

        }
        if (menuItem.getItemId() == R.id.RegistroEmpres) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new RegistroEmpresaFragment());
            fragmentTransaction.commit();

        }
        if (menuItem.getItemId() == R.id.informacion) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new InformacionFragment());
            fragmentTransaction.commit();
            //configurar el fragment de desarrollo despues

        }
        if (menuItem.getItemId() == R.id.proceso) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new FuturoFragment());
            fragmentTransaction.commit();
            //configurar el fragment de desarrollo despues

        }
        return true;
    }

    /**
     * boton para pasar entre el fragmento de proceso e información
     */
    @Override
    public void onButtonSelected() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, new InicioFragment());
        fragmentTransaction.commit();
    }
}