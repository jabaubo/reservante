package com.jabaubo.proyecto_reservas;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.jabaubo.proyecto_reservas.databinding.ActivityMainBinding;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private int idRestaurante;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Menu menu;
    boolean login = false;
    ActionBar actionBar;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavigationView navigationView = binding.navView;
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.lupa));
        actionBar = getSupportActionBar();

        drawer = binding.drawerLayout;
        mAppBarConfiguration = new AppBarConfiguration.Builder().setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        controlMenuLateral();

    }

    public void controlMenuLateral(){
        //Activar el menú lateral si está inciada la sesión
        if (this.login){
            actionBar.setDisplayHomeAsUpEnabled(true);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        else{
            actionBar.setDisplayHomeAsUpEnabled(false);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Menú para buscar en Reservas pendientes
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem buscarNombre = menu.findItem(R.id.buscarClienteNombre);
        buscarNombre.setVisible(false);
        MenuItem buscarTlf = menu.findItem(R.id.buscarClienteTlf);
        buscarTlf.setVisible(false);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public Menu getMenu() {
        return menu;
    }
}