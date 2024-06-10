package com.jabaubo.proyecto_reservas.ui.config;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
import com.jabaubo.proyecto_reservas.MainActivity;
import com.jabaubo.proyecto_reservas.Objetos.Salon;
import com.jabaubo.proyecto_reservas.Objetos.SalonAdapter;
import com.jabaubo.proyecto_reservas.Objetos.Vacaciones;
import com.jabaubo.proyecto_reservas.Objetos.VacacionesAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentConfigBinding;
import com.jabaubo.proyecto_reservas.ui.SalonDialog;
import com.jabaubo.proyecto_reservas.ui.VacacionesDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ConfigFragment extends Fragment {

    private FragmentConfigBinding binding;
    private BaseDeDatos baseDeDatos;
    private String id = "";
    private Uri uriImg;
    //Componentes
    private EditText etNombre;
    private EditText etTlf1;
    private EditText etTlf2;
    private EditText etDireccion;
    private EditText etCorreo;
    private EditText etDuracionReservas;
    private RecyclerView rvSalones;
    private Button btAgregarSalon;
    private RecyclerView rvVacaciones;
    private Button btAgregarVacaciones;
    private Button btGuardar;
    private TextView tvFechaInicio;
    private TextView tvFechaFin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfigBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Obtenemos los datos del restaurante
        JSONObject json = leerDatosRestaurante();
        //Preparamos todos los componentes
        etNombre = root.findViewById(R.id.etNombre);
        etTlf1 = root.findViewById(R.id.etTlf1);
        etTlf2 = root.findViewById(R.id.etTlf2);
        etDireccion = root.findViewById(R.id.etDireccion);
        btGuardar = root.findViewById(R.id.btGuardar);
        etCorreo = root.findViewById(R.id.etCorreo);
        rvSalones = root.findViewById(R.id.rvSalones);
        rvVacaciones = root.findViewById(R.id.rvVacaciones);
        btAgregarSalon = root.findViewById(R.id.btAgregarSalon);
        etDuracionReservas = root.findViewById(R.id.etIntervalo);
        tvFechaFin = root.findViewById(R.id.tvFechaFin);
        tvFechaInicio = root.findViewById(R.id.tvFechaInicio);
        btAgregarVacaciones = root.findViewById(R.id.btAgregarVacacion);
        rvSalones.setLayoutManager(new LinearLayoutManager(this.getContext()));
        cargarSalones();
        //Asignamos los listeners
        btAgregarVacaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAgregarVacaciones();
            }
        });
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btGuardarClick(root);
            }
        });
        btAgregarSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAgregarSalon();
            }
        });
        etDuracionReservas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etDuracionReservas);
                }
                return false;
            }
        });
        try {
            JSONObject jsonRestaurante = json.getJSONArray("resultado").getJSONObject(0);
            //Obtenemos los datos del json y vamos rellenando los campos
            id = jsonRestaurante.getString("id");
            etNombre.setText(jsonRestaurante.getString("nombre"));
            etTlf1.setText(jsonRestaurante.getString("telefono1"));
            etTlf2.setText(jsonRestaurante.getString("telefono2"));
            etCorreo.setText(jsonRestaurante.getString("email"));
            etDireccion.setText(jsonRestaurante.getString("direccion"));
            etDuracionReservas.setText(jsonRestaurante.getString("duracion_reservas"));
            ArrayList<Vacaciones> listaVacaciones = new ArrayList<>();
            /*
            * Comprobamos que contiene resultado2
            * Tiene-> Vamos rellenando una lista de vacaciones y se la pasamos a un RecyclerView
            * No tiene-> Avisamos*/
            if (json.toString().contains("resultado2")) {
                JSONArray jsonVacaciones = json.getJSONArray("resultado2");
                //Recorremos el jsonArray
                for (int i = 0; i < jsonVacaciones.length(); i++) {
                    JSONObject jsonVacacionElegida = jsonVacaciones.getJSONObject(i);
                    //Leemos el json y creamos un objeto con sus datos
                    Vacaciones v = new Vacaciones(jsonVacacionElegida.getString("nombre"),
                            jsonVacacionElegida.getString("inicio"),
                            jsonVacacionElegida.getString("fin"),
                            jsonVacacionElegida.getInt("id_restaurante"),
                            jsonVacacionElegida.getInt("id_vacacion"));
                    //Se lo añadimos a la lista
                    listaVacaciones.add(v);
                }
            }
            VacacionesAdapter vacacionesAdapter;
            /*
            * Comprobamos longitud de la lista
            * >0 -> se la pasamos al adpter
            * 0-> Avisamos y pasamos un arraylist vacío al adapter*/
            if (listaVacaciones.size() > 0) {
                vacacionesAdapter = new VacacionesAdapter(listaVacaciones, this);
            } else {
                vacacionesAdapter = new VacacionesAdapter(new ArrayList<>(), this);
                android.app.AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Advertencia")
                        .setMessage("No hay vacaciones registradas")
                        .setPositiveButton(android.R.string.yes, null)
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                alertDialog.show();
            }
            rvVacaciones.setLayoutManager(new LinearLayoutManager(this.getContext()));
            rvVacaciones.setAdapter(vacacionesAdapter);
            rvVacaciones.refreshDrawableState();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return root;
    }

    public void btGuardarClick(View root) {
        //Preparamos el json
        String json = "{\n" +
                "  \"id\": \"#PARAMID#\",\n" +
                "  \"nombre\": \"#PARAMNOMBRE#\",\n" +
                "  \"telefono1\": \"#PARAMTELEFONO1#\",\n" +
                "  \"telefono2\": \"#PARAMTELEFONO2#\",\n" +
                "  \"email\": \"#PARAMEMAIL#\",\n" +
                "  \"direccion\": \"#PARAMDIRECCION#\",\n" +
                "  \"duracion_reservas\": \"#PARAMDURACION_RESERVAS#\"\n" +
                "}\n";
        //Comprobamos que la duración de reservas sea un valor válido
        if (etDuracionReservas.getText().toString().equals("00:00:00") || etDuracionReservas.getText().toString().equals("00:00")) {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Advertencia")
                    .setMessage("Inserte una duración de reservas válida.")
                    .setPositiveButton(android.R.string.yes, null)
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert).create();
            alertDialog.show();
        } else {
            //Comrprobamos que el resto de campos tengan valores aptos
            if (!etNombre.getText().toString().equals("") &&
                    !etTlf1.getText().toString().equals("") &&
                    !etDireccion.getText().toString().equals("") &&
                    !etDuracionReservas.getText().toString().equals("")) {
                boolean todoOk = true;
                if (!etTlf1.getText().toString().matches("[0-9]{9}")){
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Advertencia")
                            .setMessage("Inserte un teléfono 1 válido.")
                            .setPositiveButton(android.R.string.yes, null)
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert).create();
                    alertDialog.show();
                    todoOk = false;
                }
                if (!etTlf2.getText().toString().equals("")){
                    if (!etTlf2.getText().toString().matches("[0-9]{9}")){
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle("Advertencia")
                                .setMessage("Inserte un teléfono 2 válido.")
                                .setPositiveButton(android.R.string.yes, null)
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert).create();
                        alertDialog.show();
                        todoOk = false;
                    }
                }
                if (!etCorreo.getText().toString().equals("")){
                    if (!etCorreo.getText().toString().matches("[^\\^$.\\|?*+()\\]\\[}{]{8,16}[@][a-z]{4,5}[.][a-z]{2,3}")){
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle("Advertencia")
                                .setPositiveButton(android.R.string.yes, null)
                                .setMessage("Inserte un correo válido.")
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert).create();
                        alertDialog.show();
                        todoOk = false;
                    }
                }
                if (todoOk){
                    //Preparamos el json
                    json = json.replace("#PARAMID#", String.valueOf(((MainActivity) getActivity()).getIdRestaurante()))
                            .replace("#PARAMNOMBRE#", etNombre.getText().toString())
                            .replace("#PARAMTELEFONO1#", etTlf1.getText().toString())
                            .replace("#PARAMTELEFONO2#", etTlf2.getText().toString())
                            .replace("#PARAMEMAIL#", etCorreo.getText().toString())
                            .replace("#PARAMDIRECCION#", etDireccion.getText().toString())
                            .replace("#PARAMDURACION_RESERVAS#", etDuracionReservas.getText().toString());
                    String finalJson = json;
                    System.out.println(finalJson);
                    //Preparamos la petición
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            // Conectamos a la pagina con el método que queramos
                            try {
                                URL url = new URL("https://reservante.mjhudesings.com/slim/updatedatos");
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("PUT");
                                //Escribimos el json
                                OutputStream os = connection.getOutputStream();
                                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                                osw.write(finalJson);
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
                    //Arrancamos la petición y la sincronizamos para que la app la espere
                    Thread thread = new Thread(runnable);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //Avisamos de que se ha guardado la actualización de datos
                    Snackbar.make(root, "Guardando datos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } else {
                //Avisamos si hay algún campo vacío
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Advertencia")
                        .setMessage("Algun campo está vacío, rellénelo.")
                        .setPositiveButton(android.R.string.yes, null)
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                alertDialog.show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void clickAgregarVacaciones() {
        //Abrimos un dialog para crear la vacación
        VacacionesDialog vacacionesDialog = new VacacionesDialog(this);
        vacacionesDialog.show(this.getChildFragmentManager(), null);
    }

    public void cargarSalones() {
        String[] responseStr = new String[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/getsalones");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    //Preparamos y escribimos el json
                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    String jsonStr = "{\n"
                            + "    \"id\":\"#PARAMID#\"\n"
                            + "}";
                    jsonStr = jsonStr.replace("#PARAMID#", String.valueOf(((MainActivity) getActivity()).getIdRestaurante()));
                    osw.write(jsonStr);
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
        //Arrancamos el hilo y lo sincronizamos para que la app lo espere
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        JSONArray jsonArray;
        try {
            ArrayList<Salon> lista = new ArrayList<>();
            /*
            * Comprobamos el código
            * 1 -> Respuesta correcta
            * Otro -> Error*/
            if (new JSONObject(responseStr[0]).getInt("codigo") == 1) {
                //Seleccionamos el jsonArray a recorrer
                jsonArray = new JSONObject(responseStr[0]).getJSONArray("aforo");
                for (int i = 0; i < jsonArray.length(); i++) {
                    //Vamos creando las vacaciones con los datos leidos
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    Salon s = new Salon(jsonObject.getInt("id_salon"), jsonObject.getString("nombre"), jsonObject.getInt("aforo"));
                    System.out.println("SALON EN LISTA " + s);
                    lista.add(s);
                }
            }
            //Comrpobramos que haya saloness, si no hay avisamos
            if (lista.size() == 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Advertencia")
                        .setMessage("No hay salones registrados,\n" +
                                "Configure alguno antes de intentar reservar.")
                        .setPositiveButton(android.R.string.yes, null)
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                alertDialog.show();
            }
            //Configuramos el adapter
            SalonAdapter salonAdapter = new SalonAdapter(lista, this);
            rvSalones.setAdapter(salonAdapter);
            rvSalones.refreshDrawableState();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickAgregarSalon() {
        //Abrimos un salonDialog para crear el Salón
        SalonDialog salonDialog = new SalonDialog(this);
        salonDialog.show(this.getActivity().getSupportFragmentManager(), "a");
    }

    public void clickHoras(EditText campo) {
        //Modificamos el EditText para que muestre un reloj
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateamos el texto
                String hora = String.valueOf(hourOfDay);
                String minuto = String.valueOf(minute);
                if (hora.length() == 1) {
                    hora = "0" + hora;
                }
                if (minuto.length() == 1) {
                    minuto = "0" + minuto;
                }
                campo.setText(hora + ":" + minuto);
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    public RecyclerView getRvSalones() {
        return rvSalones;
    }

    public RecyclerView getRvVacaciones() {
        return rvVacaciones;
    }

    public JSONObject leerDatosRestaurante() {
        String[] responseStr = new String[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/getdatos");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    //Preparamos y escribimos el json
                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    String jsonStr = "{\n"
                            + "    \"id\":\"#PARAMID#\"\n"
                            + "}";
                    jsonStr = jsonStr.replace("#PARAMID#", String.valueOf(((MainActivity) getActivity()).getIdRestaurante()));
                    osw.write(jsonStr);
                    System.out.println(jsonStr);
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
        //Arrancamos el hilo y lo sincronizamos con la app para que espere los resultados
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        try {
            System.out.println(responseStr[0]);
            JSONObject jsonObject = new JSONObject(responseStr[0]);
            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}