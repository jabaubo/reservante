package com.jabaubo.proyecto_reservas.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter;
import com.jabaubo.proyecto_reservas.Objetos.Reserva;
import com.jabaubo.proyecto_reservas.ui.reservas_fechas.ReservasFragmentFechas;

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
    private ReservasFragmentFechas reservasFragmentFechas;
    private String nombre;
    private String tlf;
    private String email;
    private String comensales;
    private String salon;
    private String observaciones;
    private String idReserva;
    private int adapterPosition;
    private String fecha;
    private String hora;

    private EditText etNombre;
    private EditText etTlf;
    private EditText etEmail;
    private EditText etComensales;
    private EditText etSalon;
    private EditText etObservaciones;
    private Spinner sSalones;


    private Button btBorrado;
    private Button btLLamar;
    private Button btCorreo;

    public ReservaDialog(Boolean editando, View vistaPadre, String nombre, String tlf, String email, String comensales, String salon, String observaciones, String idReserva, String fecha, String hora,int adapterPosition, ReservasFragmentFechas reservasFragmentFechas) {
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
        this.adapterPosition = adapterPosition;
        this.reservasFragmentFechas = reservasFragmentFechas;
    }


    public ReservaDialog(View vistaPadre, String hora, String fecha, ReservasFragmentFechas reservasFragmentFechas) {
        this.vistaPadre = vistaPadre;
        this.hora = hora;
        this.fecha = fecha;
        this.reservasFragmentFechas = reservasFragmentFechas;
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
        etObservaciones = view.findViewById(R.id.etObservacionesRD);
        btBorrado = view.findViewById(R.id.btBorrarDialog);
        btCorreo = view.findViewById(R.id.btCorreoDialog);
        btLLamar = view.findViewById(R.id.btLlamarDialog);
        sSalones = view.findViewById(R.id.sSalones);
        String[] salones = leerSalones();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item,salones);
        sSalones.setAdapter(adapter);
        if (!editando) {
            btCorreo.setEnabled(false);
            btLLamar.setEnabled(false);
            btBorrado.setEnabled(false);
        }
        System.out.println(view);
        //alertDialogBuilder.setView(view).setPositiveButton(R.string.dialogo_guardar,null).setNegativeButton(R.string.dialogo_guardar,null);
        alertDialogBuilder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialogo_guardar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String nombre = etNombre.getText().toString();
                        String telefono = etTlf.getText().toString();
                        String email = etEmail.getText().toString();
                        String comensales = etComensales.getText().toString();
                        String salon = sSalones.getSelectedItem().toString().substring(0,sSalones.getSelectedItem().toString().indexOf(" -"));
                        System.out.println("postsalon");
                        String observaciones = etObservaciones.getText().toString();
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
                            if (editando) {
                                String json = crearJsonActualizar();
                                System.out.println("JSON actualizar: " + json);
                                if (json.equals("No hay diferencias")){
                                    Snackbar.make(vistaPadre, json, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else {
                                    System.out.println(json);
                                    Runnable runnable= new Runnable() {
                                        @Override
                                        public void run() {
                                            // Conectamos a la pagina con el método que queramos
                                            try {
                                                URL url = new URL("https://reservante.mjhudesings.com/slim/updatereserva");
                                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                                connection.setRequestMethod("PUT");
                                                OutputStream os = connection.getOutputStream();
                                                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                                                osw.write(json);
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
                                                    reader.close();
                                                    System.out.println(response);
                                                }
                                                connection.disconnect();
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

                                    Reserva r =((ReservaAdapter) reservasFragmentFechas.getRvOcupacion().getAdapter()).getDataList().get(adapterPosition);
                                    JSONObject jsonObj;
                                    try {
                                        jsonObj = new JSONObject(json);
                                        r.setEmail(jsonObj.getString("email"));
                                        r.setId_salon(Integer.valueOf(jsonObj.getString("id_salon")));
                                        r.setObservaciones(jsonObj.getString("observaciones"));
                                        r.setNombre_apellidos(jsonObj.getString("nombre_apellidos"));
                                        r.setTelefono(jsonObj.getString("telefono"));
                                        r.setN_personas(Integer.valueOf(jsonObj.getString("n_personas")));
                                        ((ReservaAdapter) reservasFragmentFechas.getRvOcupacion().getAdapter()).getDataList().set(adapterPosition,r);
                                        reservasFragmentFechas.getRvOcupacion().getAdapter().notifyDataSetChanged();
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                    Snackbar.make(vistaPadre,"Actualización correcta",Snackbar.LENGTH_LONG);

                                }

                            } else {
                                crearJsonInsertar();
                            }

                        }
                    }
                })
                .setNegativeButton(R.string.dialogo_cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        if (editando) {
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
                for (int i = 0; i < salones.length ; i++){
                    System.out.println(salon + " " + salones[i].substring(0,salones[i].indexOf("-")-1).length() );
                    if (salones[i].substring(0,salones[i].indexOf("-")-1).equals(salon)){
                        sSalones.setSelection(i);
                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + i);
                        break;
                    }
                }
            }
            if (observaciones != null) {
                etObservaciones.setText(observaciones);
            }
        }
        btBorrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBorrar();
            }
        });

        btLLamar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickLlamar();
            }
        });

        btCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCorreo();
            }
        });

        return alertDialogBuilder.create();
    }

    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {

        return super.show(transaction, tag);
    }

    public String crearJsonActualizar() {
        //SELECT SUM(n_personas),(SELECT SUM(aforo) FROM salones) FROM reservas WHERE fecha = '2024-04-08' AND hora = '17:00';
        String nombre_n = etNombre.getText().toString();
        String tlf_n = etTlf.getText().toString();
        String email_n = etEmail.getText().toString();
        String n_personas_n = etComensales.getText().toString();
        String id_salon_n = sSalones.getSelectedItem().toString().substring(0,sSalones.getSelectedItem().toString().indexOf(" -"));
        System.out.println(id_salon_n);
        String observaciones_n = etObservaciones.getText().toString();
        if (!nombre_n.equals(nombre) || !tlf_n.equals(tlf) || !email_n.equals(email) || !n_personas_n.equals(comensales) || !id_salon_n.equals(salon) || !observaciones_n.equals(observaciones)) {
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
            System.out.println("JSON pre replaces: " + json);json = json.replace("#PARAMid_reserva#", idReserva);
            System.out.println("Replace 1: " + json);
            json = json.replace("#PARAMnombre_apellidos#", nombre_n);
            System.out.println("Replace 2: " + json);
            json = json.replace("#PARAMtelefono#", tlf_n);
            System.out.println("Replace 3: " + json);
            json = json.replace("#PARAMemail#", email_n);
            System.out.println("Replace 4: " + json);
            json = json.replace("#PARAMn_personas#", n_personas_n);
            System.out.println("Replace 5: " + json);
            json = json.replace("#PARAMid_salon#", id_salon_n);
            System.out.println("Replace 6: " + json);
            json = json.replace("#PARAMobservaciones#", observaciones_n);
            System.out.println("Replace 7: " + json);
            json = json.replace("#PARAMhora#", hora);
            System.out.println("Replace 8: " + json);
            json = json.replace("#PARAMfecha#", fecha);
            System.out.println("Replace 9: " + json);

            System.out.println("JSON En el método" + json);
            return json;
        }else{
            return "No hay diferencias";
        }
    }

    public void crearJsonInsertar() {
        String nombre = etNombre.getText().toString();
        String tlf = etTlf.getText().toString();
        String email = etEmail.getText().toString();
        String n_personas = etComensales.getText().toString();
        String salon = sSalones.getSelectedItem().toString();
        String id_salon = salon.substring(0,sSalones.getSelectedItem().toString().indexOf(" -"));
        String aforoLibre = salon.substring(salon.indexOf("libre: ")+7,salon.indexOf("/"));
        System.out.println("Id salon: " + id_salon);
        System.out.println("aforo libre: " + aforoLibre);
        String observaciones = etObservaciones.getText().toString();
        final boolean[] permitir = {true};
        final boolean[] fin = {false};
        AlertDialog alertDialog = null;
        final String[] json = {""};
            if (Integer.valueOf(n_personas) > Integer.valueOf(aforoLibre)) {
                alertDialog = new AlertDialog.Builder(vistaPadre.getContext())
                        .setTitle("Exceso de aforo")
                        .setMessage("Si guardas esta reserva pasarás tu aforo")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Runnable runnable = new Runnable() {
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
                                                    .replace("#PARAMhora#", hora)
                                                    .replace("#PARAMfecha#", fecha);
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

                                JSONObject jsonObj ;
                                Reserva r = new Reserva();
                                try {
                                    jsonObj = new JSONObject(json[0]);
                                    r.setEmail(jsonObj.getString("email"));
                                    r.setId_salon(Integer.valueOf(jsonObj.getString("id_salon")));
                                    r.setObservaciones(jsonObj.getString("observaciones"));
                                    r.setNombre_apellidos(jsonObj.getString("nombre_apellidos"));
                                    r.setTelefono(jsonObj.getString("telefono"));
                                    r.setN_personas(Integer.valueOf(jsonObj.getString("n_personas")));;
                                    ((ReservaAdapter) reservasFragmentFechas.getRvOcupacion().getAdapter()).getDataList().add(r);
                                    reservasFragmentFechas.getRvOcupacion().getAdapter().notifyItemInserted(reservasFragmentFechas.getRvOcupacion().getAdapter().getItemCount()-1);
                                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + (reservasFragmentFechas.getRvOcupacion().getAdapter().getItemCount()-1));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
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
            } else {
                Runnable runnable = new Runnable() {
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
                                    .replace("#PARAMhora#", hora)
                                    .replace("#PARAMfecha#", fecha);
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
                JSONObject jsonObj ;
                Reserva r = new Reserva();
                try {
                    jsonObj = new JSONObject(json[0]);
                    r.setEmail(jsonObj.getString("email"));
                    r.setId_salon(Integer.valueOf(jsonObj.getString("id_salon")));
                    r.setObservaciones(jsonObj.getString("observaciones"));
                    r.setNombre_apellidos(jsonObj.getString("nombre_apellidos"));
                    r.setTelefono(jsonObj.getString("telefono"));
                    r.setN_personas(Integer.valueOf(jsonObj.getString("n_personas")));
                    r.setFecha(jsonObj.getString("fecha"));
                    r.setHora(jsonObj.getString("hora"));
                    ((ReservaAdapter) reservasFragmentFechas.getRvOcupacion().getAdapter()).getDataList().add(r);
                    reservasFragmentFechas.getRvOcupacion().getAdapter().notifyItemInserted(reservasFragmentFechas.getRvOcupacion().getAdapter().getItemCount()-1);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Snackbar.make(vistaPadre, "Guardando", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                
            }
        }



    public String[] leerSalones(){
        final JSONArray[] jsonArray = new JSONArray[1];
        Runnable runnable = new Runnable() {
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
                    json = json.replace("#FECHA#", fecha);
                    json = json.replace("#HORA#", hora);
                    osw.write(json);
                    System.out.println("MANDO :" + json);
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
                        reader.close();
                        connection.disconnect();
                        System.out.println("Respuesta insertar aforo" + response);
                        jsonArray[0] = new JSONObject(String.valueOf(response)).getJSONArray("aforo");
                        System.out.println(jsonArray[0]);
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
        String[] textos = new String[jsonArray[0].length()];
        for (int i = 0 ; i < jsonArray[0].length() ; i++){
            try {
                JSONObject jsonObject = (JSONObject) jsonArray[0].get(i);
                textos[i] = String.format("%s - %s libre: %s/%s ",jsonObject.getString("id_salon"), jsonObject.getString("nombre"), jsonObject.getString("disponible"), jsonObject.getString("aforo"));

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        for (String t:textos){
            System.out.println(t);
        }
        return textos;
    }
    public void clickBorrar() {
        ReservaDialog reservaDialog = this;
        String jsonStr = "{\n" +
                "            \"id_reserva\": \"#PARAMRESERVA#\"\n" +
                "}";
        jsonStr = jsonStr.replace("#PARAMRESERVA#", idReserva);
        String finalJsonStr = jsonStr;
        AlertDialog alertDialog = new AlertDialog.Builder(vistaPadre.getContext())
                .setTitle("Advertencia")
                .setMessage("Vas a borrar una reserva, esta acción no se puede deshacer")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                // Conectamos a la pagina con el método que queramos
                                try {
                                    URL url = new URL("https://reservante.mjhudesings.com/slim/deletereserva");
                                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                    connection.setRequestMethod("DELETE");
                                    OutputStream os = connection.getOutputStream();
                                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                                    osw.write(finalJsonStr);
                                    System.out.println("JSON EN LA PETICIÓN: " + finalJsonStr);
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
                        reservasFragmentFechas.avisarBorradoRecyclerView(adapterPosition);
                        reservaDialog.dismiss();
                        Snackbar.make(vistaPadre, "Borrando", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                })
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

    public void clickLlamar(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+etTlf.getText().toString()));
        reservasFragmentFechas.getActivity().startActivity(intent);
    }
    public void clickCorreo(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+etEmail.getText().toString()));
        reservasFragmentFechas.getActivity().startActivity(intent);
    }

}