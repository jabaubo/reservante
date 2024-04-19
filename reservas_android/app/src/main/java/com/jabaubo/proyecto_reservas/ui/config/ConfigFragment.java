package com.jabaubo.proyecto_reservas.ui.config;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.BaseDeDatos;
import com.jabaubo.proyecto_reservas.Objetos.Salon;
import com.jabaubo.proyecto_reservas.Objetos.SalonAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentConfigBinding;
import com.jabaubo.proyecto_reservas.ui.SalonDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ConfigFragment extends Fragment {

    private FragmentConfigBinding binding;
    private Activity activity;
    private BaseDeDatos baseDeDatos;
    private Uri uriImg;
    //Componentes
    private EditText etNombre;
    private EditText etTlf1;
    private EditText etTlf2;
    private EditText etDireccion;
    private EditText etCorreo;
    private ImageView imageViewLogo;
    private RecyclerView rvSalones;
    private Button btAgregarSalon;
    private Button btGuardar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activity = this.getActivity();
        binding = FragmentConfigBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        baseDeDatos = new BaseDeDatos(this.getContext());
        String[] datos = baseDeDatos.leerRestaurante();

        etNombre = root.findViewById(R.id.etNombre);
        etTlf1 = root.findViewById(R.id.etTlf1);
        etTlf2 = root.findViewById(R.id.etTlf2);
        etDireccion = root.findViewById(R.id.etDireccion);
        btGuardar =  root.findViewById(R.id.btGuardar);
        imageViewLogo = root.findViewById(R.id.imageLogoConfig);
        etCorreo = root.findViewById(R.id.etCorreo);
        rvSalones = root.findViewById(R.id.rvSalones);
        btAgregarSalon = root.findViewById(R.id.btAgregarSalon);
        rvSalones.setLayoutManager(new LinearLayoutManager(this.getContext()));
        cargarSalones();

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        System.out.println("uri " + uri);
                        uriImg = uri;
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btGuardarClick(root);
            }
        });
        imageViewLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("foto");
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build());
                imageViewLogo.setImageURI(uriImg);
                imageViewLogo.refreshDrawableState();

            }
        });
        btAgregarSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAgregarSalon();
            }
        });

        etNombre.setText(datos[0]);
        etTlf1.setText(datos[1]);
        etTlf2.setText(datos[2]);
        etDireccion.setText(datos[3]);

        System.out.println("macarron3");
        return root;
    }

    public void btGuardarClick(View root){
        baseDeDatos.guardarRestaurante(etNombre.getText().toString(), Integer.valueOf(etTlf1.getText().toString()), Integer.valueOf(etTlf2.getText().toString()), etDireccion.getText().toString());
        Snackbar.make(root, "Guardando datos", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void cargarSalones(){
        String[] responseStr = new String[1];
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/getsalones");
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
        JSONArray jsonArray;
        try {
            jsonArray = new JSONObject(responseStr[0]).getJSONArray("resultado");
            ArrayList<Salon> lista = new ArrayList<>();
            for (int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Salon s = new Salon(jsonObject.getInt("id_salon"),jsonObject.getString("nombre"),jsonObject.getInt("aforo"));
                lista.add(s);
            }
            for (int i = 0 ; i < lista.size()  ;i++){
                System.out.println(lista.get(i));
            }
            SalonAdapter salonAdapter = new SalonAdapter(lista,this);
            rvSalones.setAdapter(salonAdapter);
            rvSalones.refreshDrawableState();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void clickAgregarSalon(){
        SalonDialog salonDialog = new SalonDialog();
        salonDialog.show(this.getActivity().getSupportFragmentManager(),"a");
    }
    @Override
    public void onStop() {
        super.onStop();
    }
}