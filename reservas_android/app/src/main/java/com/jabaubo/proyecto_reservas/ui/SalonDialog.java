package com.jabaubo.proyecto_reservas.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.Objetos.Reserva;
import com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter;
import com.jabaubo.proyecto_reservas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SalonDialog extends DialogFragment {
    private int aforo;
    private String nombre;

    private EditText etNombre;
    private EditText etAforo;

    public SalonDialog() {
    }

    public SalonDialog(int aforo, String nombre) {
        this.aforo = aforo;
        this.nombre = nombre;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_dialog_salon, null);
        etNombre = view.findViewById(R.id.etNombreSD);
        etAforo = view.findViewById(R.id.etAforoSD);
        if (!String.valueOf(aforo).equals("")){
            etAforo.setText(String.valueOf(aforo));
        }
        if (!nombre.equals("")){
            etNombre.setText(nombre);
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialogo_guardar,null)
                .setNegativeButton(R.string.dialogo_cancelar, null);
        return alertDialogBuilder.create();
    }
}
