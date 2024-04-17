package com.jabaubo.proyecto_reservas.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.SignalStrength;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.R;

import org.json.JSONArray;
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

public class ReservaDialog extends DialogFragment {
    private View vistaPadre;
    private Boolean editando = false;
    private String nombre;
    private String tlf;
    private String email;
    private String comensales;
    private String salon;
    private String observaciones;
    private String idReserva;
    private String fecha;
    private String hora;

    private EditText etNombre;
    private EditText etTlf;
    private EditText etEmail;
    private EditText etComensales;
    private EditText etSalon;
    private EditText etObservaciones;


    public ReservaDialog(Boolean editando,View vistaPadre, String nombre, String tlf, String email, String comensales, String salon, String observaciones, String idReserva, String fecha, String hora) {
        this.editando = editando;
        this.nombre = nombre;
        this.tlf = tlf;
        this.email = email;
        this.comensales = comensales;
        this.salon = salon;
        this.observaciones = observaciones;
        this.idReserva = idReserva;
        this.fecha = fecha;
        this.hora = hora;
        this.vistaPadre = vistaPadre;
    }


    public ReservaDialog(View vistaPadre , String hora , String fecha) {
        this.vistaPadre = vistaPadre;
        this.hora = hora;
        this.fecha = fecha;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_dialog_reservar, null);
        etNombre = view.findViewById(R.id.etClienteRD);
        etTlf = view.findViewById(R.id.etTlfRD);
        etEmail = view.findViewById(R.id.etEmailRD);
        etComensales = view.findViewById(R.id.etComensalesRD);
        etSalon = view.findViewById(R.id.etSalonRD);
        etObservaciones = view.findViewById(R.id.etObservacionesRD);
        System.out.println((this.getContext() == null) +  "contex");
        alertDialogBuilder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialogo_guardar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String nombre = etNombre.getText().toString();
                        String telefono = etTlf.getText().toString();
                        String email = etEmail.getText().toString();
                        String comensales = etComensales.getText().toString();
                        String salon = etSalon.getText().toString();
                        String observaciones = etObservaciones.getText().toString();
                        System.out.println(observaciones.equals(""));
                        System.out.println(observaciones == null);
                        if (nombre.equals("") | telefono.equals("") | email.equals("") | comensales.equals("") | salon.equals("") | observaciones.equals("")) {
                            String error = "";
                            if (nombre.equals("")) {
                                error += "Nombre no tiene valor\n";
                            }
                            if (telefono.equals("")) {
                                error += "Teléfono no tiene valor\n";
                            }
                            if (email.equals("")) {
                                error += "Email no tiene valor\n";
                            }
                            if (comensales.equals("")) {
                                error += "Comensales no tiene valor\n";
                            }
                            if (salon.equals("")) {
                                error += "Salón no tiene valor\n";
                            }
                            if (observaciones.equals("")) {
                                error += "Observaciones no tiene valor\n";
                            }
                            System.out.println(error);
                            Snackbar.make(vistaPadre, error, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            if (editando){
                                String json = crearJsonActualizar();

                                System.out.println(json);
                                Snackbar.make(vistaPadre, "Reserva " + idReserva + " actualizada", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }else {
                                crearJsonInsertar();
                                /*String jsonStr = crearJsonInsertar();
                                JSONObject json;
                                try {
                                    json = new JSONObject(jsonStr);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                if (!jsonStr.equals("No se ha permitido la reserva")){

                                }
                                else {
                                    Snackbar.make(vistaPadre, "Reserva cancelada", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }*/

                            }

                        }
                    }
                })
                .setNegativeButton(R.string.dialogo_cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        if (editando){
            if (nombre != null) {
                etNombre.setText(nombre);
            }
            if (tlf != null) {
                etTlf.setText(tlf);
            }
            if (email != null) {
                etEmail.setText(email);
            }
            if (comensales != null) {
                etComensales.setText(comensales);
            }
            if (salon != null) {
                etSalon.setText(salon);
            }
            if (observaciones != null) {
                etObservaciones.setText(observaciones);
            }
        }
        return alertDialogBuilder.create();
    }
    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {

        return super.show(transaction, tag);
    }

    public String crearJsonActualizar(){
        //SELECT SUM(n_personas),(SELECT SUM(aforo) FROM salones) FROM reservas WHERE fecha = '2024-04-08' AND hora = '17:00';
        String nombre = etNombre.getText().toString();
        String tlf = etTlf.getText().toString();
        String email = etEmail.getText().toString();
        String n_personas = etComensales.getText().toString();
        String id_salon = etSalon.getText().toString();
        String observaciones = etObservaciones.getText().toString();

        String json = "{\n" +
                "\"id_reserva\": \"#PARAMid_reserva#\",\n" +
                "\"nombre_apellidos\": \"#PARAMnombre_apellidos#\",\n" +
                "\"telefono\": \"#PARAMtelefono#\",\n" +
                "\"email\": \"#PARAMemail#\",\n" +
                "\"n_personas\": \"#PARAMn_personas#\",\n" +
                "\"id_salon\": \"#PARAMid_salon#\",\n" +
                "\"fecha\": \"#PARAMfecha#\",\n" +
                "\"hora\": \"#PARAMhora#\",\n" +
                "\"observaciones\": \"#PARAMobservaciones#\"\n" +
                "}";
        json = json.replace("#PARAMid_reserva#", idReserva)
                .replace("#PARAMnombre_apellidos#", nombre)
                .replace("#PARAMtelefono#", tlf)
                .replace("#PARAMemail#", email)
                .replace("#PARAMn_personas#", n_personas)
                .replace("#PARAMid_salon#", id_salon)
                .replace("#PARAMobservaciones#", observaciones)
                .replace("#PARAMhora",hora)
                .replace("#PARAMfecha",fecha);
        return  json;
    }
    public void crearJsonInsertar(){
        String nombre = etNombre.getText().toString();
        String tlf = etTlf.getText().toString();
        String email = etEmail.getText().toString();
        String n_personas = etComensales.getText().toString();
        String id_salon = etSalon.getText().toString();
        String observaciones = etObservaciones.getText().toString();
        String[] aforoLibre = new String[1];
        final boolean[] permitir = {true};
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/getaforotramo");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    String json = "{\n" +
                            "    \"fecha\":\"#FECHA#\",\n" +
                            "    \"hora\":\"#HORA#\"   \n" +
                            "}";
                    json = json.replace("#FECHA#",fecha);
                    json = json.replace("#HORA#",hora);
                    osw.write(json);
                    osw.flush();
                    int responseCode = connection.getResponseCode();
                    System.out.println("Respuesta insertar aforo" + (responseCode == HttpURLConnection.HTTP_OK));
                    //Ver si la respuesta es correcta
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Si es correcta la leemos
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuilder response = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        System.out.println("Respuesta insertar aforo" + response);
                        JSONObject jsonObject = new JSONObject(String.valueOf(response)).getJSONArray("aforo").getJSONObject(0);
                        aforoLibre[0] = jsonObject.getString("disponible");
                        reader.close();
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
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final boolean[] fin = {false};
        AlertDialog alertDialog = null;
        final String[] json = {""};
        if (Integer.valueOf(n_personas)>Integer.valueOf(aforoLibre[0])){
            alertDialog = new AlertDialog.Builder(vistaPadre.getContext())
                    .setTitle("Exceso de aforo")
                    .setMessage("Si guardas esta reserva pasarás tu aforo")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Runnable runnable= new Runnable() {
                                @Override
                                public void run() {
                                    // Conectamos a la pagina con el método que queramos
                                    try {
                                        json[0] = "{\n" +
                                                "\"nombre_apellidos\": \"#PARAMnombre_apellidos#\",\n" +
                                                "\"telefono\": \"#PARAMtelefono#\",\n" +
                                                "\"email\": \"#PARAMemail#\",\n" +
                                                "\"n_personas\": \"#PARAMn_personas#\",\n" +
                                                "\"id_salon\": \"#PARAMid_salon#\",\n" +
                                                "\"fecha\": \"#PARAMfecha#\",\n" +
                                                "\"hora\": \"#PARAMhora#\",\n" +
                                                "\"observaciones\": \"#PARAMobservaciones#\"\n" +
                                                "}";
                                        json[0] = json[0].replace("#PARAMnombre_apellidos#", nombre)
                                                .replace("#PARAMtelefono#", tlf)
                                                .replace("#PARAMemail#", email)
                                                .replace("#PARAMn_personas#", n_personas)
                                                .replace("#PARAMid_salon#", id_salon)
                                                .replace("#PARAMobservaciones#", observaciones)
                                                .replace("#PARAMhora#",hora)
                                                .replace("#PARAMfecha#",fecha);
                                        URL url = new URL("https://reservante.mjhudesings.com/slim/addreserva");
                                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                        connection.setRequestMethod("POST");
                                        OutputStream os = connection.getOutputStream();
                                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                                        osw.write(json[0]);
                                        System.out.println("JSON EN LA PETICIÓN: " + json[0]);
                                        osw.flush();
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
                                            System.out.println(response);
                                            reader.close();
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
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            Snackbar.make(vistaPadre, "Guardando", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Snackbar.make(vistaPadre, "Intento de reserva cancelado", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert).create();
            alertDialog.show();
        }
    }
}
