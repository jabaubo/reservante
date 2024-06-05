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
        binding.appBarMain.toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.lupa));
        actionBar = getSupportActionBar();

        drawer = binding.drawerLayout;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        controlMenuLateral();

    }

    public void controlMenuLateral(){
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
        /*leerDatosRestaurante();
        JSONObject json = leerDatosRestaurante();
        tvNombreMenu = this.findViewById(R.id.tvNombreMenu);
        try {
            tvNombreMenu.setText(json.getString("nombre"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }*/
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
    public JSONObject leerDatosRestaurante(){
        String[] responseStr = new String[1];
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/getdatos");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();

                    //Ver si la respuesta es correcta
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Si es correcta la leemos
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuilder response = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        responseStr[0] = response.toString();
                        connection.disconnect();
                    } else {
                        connection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (ProtocolException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        try {
            System.out.println(responseStr[0]);
            JSONObject jsonObject = new JSONObject(responseStr[0]).getJSONArray("resultado").getJSONObject(0);
            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public Menu getMenu() {
        return menu;
    }
}