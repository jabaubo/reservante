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

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.BaseDeDatos;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentConfigBinding;

import java.io.File;
import java.io.FileWriter;

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

    @Override
    public void onStop() {
        super.onStop();
    }
}