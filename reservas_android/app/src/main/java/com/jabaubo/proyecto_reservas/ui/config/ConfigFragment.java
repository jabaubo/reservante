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
import android.widget.ImageView;
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
    private ImageView imageViewLogo;
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


        JSONObject json = leerDatosRestaurante();
        etNombre = root.findViewById(R.id.etNombre);
        etTlf1 = root.findViewById(R.id.etTlf1);
        etTlf2 = root.findViewById(R.id.etTlf2);
        etDireccion = root.findViewById(R.id.etDireccion);
        btGuardar = root.findViewById(R.id.btGuardar);
        imageViewLogo = root.findViewById(R.id.imageLogoConfig);
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

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        System.out.println("uri " + uri);
                        uriImg = uri;
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

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
            id = jsonRestaurante.getString("id");
            etNombre.setText(jsonRestaurante.getString("nombre"));
            etTlf1.setText(jsonRestaurante.getString("telefono1"));
            etTlf2.setText(jsonRestaurante.getString("telefono2"));
            etCorreo.setText(jsonRestaurante.getString("email"));
            etDireccion.setText(jsonRestaurante.getString("direccion"));
            etDuracionReservas.setText(jsonRestaurante.getString("duracion_reservas"));
            ArrayList<Vacaciones> listaVacaciones = new ArrayList<>();
            if (json.toString().contains("resultado2")) {
                JSONArray jsonVacaciones = json.getJSONArray("resultado2");
                for (int i = 0; i < jsonVacaciones.length(); i++) {
                    //{"id_vacacion":"1","id_restaurante":"1","nombre":"booma","inicio":"2024-04-10","fin":"2024-04-26"}
                    JSONObject jsonVacacionElegida = jsonVacaciones.getJSONObject(i);
                    Vacaciones v = new Vacaciones(jsonVacacionElegida.getString("nombre"),
                            jsonVacacionElegida.getString("inicio"),
                            jsonVacacionElegida.getString("fin"),
                            jsonVacacionElegida.getInt("id_restaurante"),
                            jsonVacacionElegida.getInt("id_vacacion"));
                    listaVacaciones.add(v);
                    System.out.println(v + " " + listaVacaciones.size());
                }
            }
            VacacionesAdapter vacacionesAdapter;
            if (listaVacaciones.size() > 0) {
                vacacionesAdapter = new VacacionesAdapter(listaVacaciones, this);
            } else {
                vacacionesAdapter = new VacacionesAdapter(new ArrayList<>(), this);
                android.app.AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Advertencia")
                        .setMessage("No hay vacaciones registradas")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                alertDialog.show();
            }
            rvVacaciones.setLayoutManager(new LinearLayoutManager(this.getContext()));
            rvVacaciones.setAdapter(vacacionesAdapter);
            rvVacaciones.refreshDrawableState();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        /*
        btFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFecha(tvFechaFin);
            }
        });

        tvFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFecha(tvFechaFin);
            }
        });
        btFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFecha(tvFechaInicio);
            }
        });
        tvFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFecha(tvFechaInicio);
            }
        });*/
        return root;
    }

    public void btGuardarClick(View root) {
        //baseDeDatos.guardarRestaurante(etNombre.getText().toString(), Integer.valueOf(etTlf1.getText().toString()), Integer.valueOf(etTlf2.getText().toString()), etDireccion.getText().toString());
        //{"id":"1","nombre":"Restaurante pepe","telefono1":"604205805","telefono2":"957788921","email":"manuelhidalgourbano@gmail.com","direccion":"calle san marcos 135","duracion_reservas":"01:30:00","inicio":"2024-04-10","fin":"2024-04-18"}
        String json = "{\n" +
                "  \"id\": \"#PARAMID#\",\n" +
                "  \"nombre\": \"#PARAMNOMBRE#\",\n" +
                "  \"telefono1\": \"#PARAMTELEFONO1#\",\n" +
                "  \"telefono2\": \"#PARAMTELEFONO2#\",\n" +
                "  \"email\": \"#PARAMEMAIL#\",\n" +
                "  \"direccion\": \"#PARAMDIRECCION#\",\n" +
                "  \"duracion_reservas\": \"#PARAMDURACION_RESERVAS#\"\n" +
                "  \"inicio\": \"#PARAMINICIO#\",\n" +
                "  \"fin\": \"#PARAMFIN#\"\n" +
                "}\n";
        if (!etNombre.getText().toString().equals("") &&
                !etTlf1.getText().toString().equals("") &&
                !etTlf2.getText().toString().equals("") &&
                !etCorreo.getText().toString().equals("") &&
                !etDireccion.getText().toString().equals("") &&
                !etDuracionReservas.getText().toString().equals("")) {

            json = json.replace("#PARAMID#", String.valueOf(((MainActivity)getActivity()).getIdRestaurante()))
                    .replace("#PARAMNOMBRE#", etNombre.getText().toString())
                    .replace("#PARAMTELEFONO1#", etTlf1.getText().toString())
                    .replace("#PARAMTELEFONO2#", etTlf2.getText().toString())
                    .replace("#PARAMEMAIL#", etCorreo.getText().toString())
                    .replace("#PARAMDIRECCION#", etDireccion.getText().toString())
                    .replace("#PARAMDURACION_RESERVAS#", etDuracionReservas.getText().toString());
            String finalJson = json;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/updatedatos");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("PUT");
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
            Thread thread = new Thread(runnable);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Snackbar.make(root, "Guardando datos", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Advertencia")
                    .setMessage("Algun campo está vacío, rellénelo.")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert).create();
            alertDialog.show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void clickAgregarVacaciones() {
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
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        JSONArray jsonArray;
        try {
            System.out.println("Respuesta json: " + responseStr[0]);
            ArrayList<Salon> lista = new ArrayList<>();
            if (new JSONObject(responseStr[0]).getInt("codigo") == 1) {
                jsonArray = new JSONObject(responseStr[0]).getJSONArray("aforo");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    Salon s = new Salon(jsonObject.getInt("id_salon"), jsonObject.getString("nombre"), jsonObject.getInt("aforo"));
                    System.out.println("SALON EN LISTA " + s);
                    lista.add(s);
                }
                for (int i = 0; i < lista.size(); i++) {
                    System.out.println(lista.get(i));
                }
            }
            if (lista.size() == 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Advertencia")
                        .setMessage("No hay salones registrados,\n" +
                                "Configure alguno antes de intentar reservar.")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                alertDialog.show();
            }
            SalonAdapter salonAdapter = new SalonAdapter(lista, this);
            rvSalones.setAdapter(salonAdapter);
            rvSalones.refreshDrawableState();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickAgregarSalon() {
        SalonDialog salonDialog = new SalonDialog(this);
        salonDialog.show(this.getActivity().getSupportFragmentManager(), "a");
    }

    public void clickHoras(EditText campo) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
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

    public void clickFecha(TextView tvFecha) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month < 10) {
                            tvFecha.setText(String.format("%d/0%d/%d", dayOfMonth, month, year));
                        } else {
                            tvFecha.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
                        }

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public RecyclerView getRvSalones() {
        return rvSalones;
    }

    public RecyclerView getRvVacaciones() {
        return rvVacaciones;
    }

    public String formatearFecha(String fecha) {
        String[] fechaSplit = fecha.split("-");
        return fechaSplit[2] + "/" + fechaSplit[1] + "/" + fechaSplit[0];
    }/*
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
            JSONObject jsonObject = new JSONObject(responseStr[0]);
            System.out.println("JSONNNNNNNNNNNNNNNNNNNNNNNN : " + jsonObject);
            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }*/

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