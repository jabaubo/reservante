package com.jabaubo.proyecto_reservas.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabaubo.proyecto_reservas.Objetos.Reserva;
import com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter;
import com.jabaubo.proyecto_reservas.Objetos.ReservaFechas;
import com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private LocalTime incremento;
    private LocalDateTime hora_inicio_m;
    private LocalDateTime hora_fin_m;
    private LocalDateTime hora_inicio_t;
    private LocalDateTime hora_fin_t;
    private Spinner spinnerFiltro;

    private RecyclerView rvOcupacion;
    private TextView tvReservasDiaHora;
    private Button btSiguiente;
    private Button btAnterior;
    private Button btReservar;
    private Button btVolverInicio;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rvOcupacion = root.findViewById(R.id.rvOcupacionInicio);
        btAnterior = root.findViewById(R.id.btAnteriorInicio);
        btSiguiente = root.findViewById(R.id.btSiguienteInicio);
        btReservar = root.findViewById(R.id.btReservarInicio);
        btVolverInicio = root.findViewById(R.id.btVolverInicio);
        tvReservasDiaHora = root.findViewById(R.id.tvReservasDiaHoraInicio);
        spinnerFiltro = root.findViewById(R.id.spinFiltroInicio);
        String[] salones = leerSalones();
        ArrayAdapter<String> listaSalones = new ArrayAdapter<>(this.getContext(),android.R.layout.simple_spinner_dropdown_item,salones);
        spinnerFiltro.setAdapter(listaSalones);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvReservasDiaHora.setText(LocalDate.now().toString());
        }
        btReservar.setEnabled(false);
        btAnterior.setEnabled(false);
        btSiguiente.setEnabled(false);
        btVolverInicio.setEnabled(false);
        spinnerFiltro.setEnabled(false);
        btSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSiguiente();
            }
        });
        btAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAnterior();
            }
        });
        btVolverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickVolver();
            }
        });
        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (rvOcupacion.getAdapter().getClass().toString().equals("class com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter")){
                    cambioSpinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rvOcupacion.setLayoutManager(new LinearLayoutManager(this.getContext()));
        cargarOcupacion();
        System.out.println("TENGO " + rvOcupacion.getAdapter().getItemCount());
        ;
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void cargarOcupacion(){
        try {
            ArrayList<ReservaFechas> lista = new ArrayList<>();
            Runnable runnable= new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getocupacion");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Accept", "application/json");
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String consulta = null;
                            consulta = leerTramos(String.valueOf(LocalDate.now()));
                        String jsonFecha = "    {\"consulta\":\"SELECT range_values.value,salones.nombre,(SELECT COUNT(*) FROM salones) as n_salones,COUNT(reservas.id_salon) AS n_reservas,COALESCE(SUM(reservas.n_personas), 0) AS n_personas,salones.aforo AS aforo FROM (#TRAMOS#) AS range_values CROSS JOIN salones LEFT JOIN reservas ON range_values.value = reservas.hora AND reservas.fecha = '#PARAMFECHA#' AND salones.id_salon = reservas.id_salon GROUP BY range_values.value, salones.id_salon ORDER BY range_values.value ASC;\"}";
                        jsonFecha = jsonFecha.replace("#TRAMOS#",consulta);
                        jsonFecha = jsonFecha.replace("#PARAMFECHA#",LocalDate.now().toString());
                        System.out.println(jsonFecha);
                        osw.write(jsonFecha);
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
                            JSONArray jsonArray = new JSONObject(response.toString()).getJSONArray("reservas");
                            int n_salones = jsonArray.getJSONObject(0).getInt("n_salones");
                            System.out.println("tope :" + n_salones);
                            System.out.println("JSON ARRAY: " + jsonArray);
                            System.out.println("JSON ARRAY LENGTH: " + jsonArray.length());

                            //while ()

                            for (int i = 0 ; i < jsonArray.length();i += n_salones){
                                int reservasTotal = 0;
                                ReservaFechas rf = new ReservaFechas();
                                String ocupacion = "";
                                for (int j = i ; j < (i + n_salones ) ; j++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                                    String nombreSalon = jsonObject.getString("nombre");
                                    String nReservas = jsonObject.getString("n_reservas");
                                    reservasTotal += Integer.valueOf(nReservas);
                                    String nPersonas = jsonObject.getString("n_personas");
                                    String aforoSalon = jsonObject.getString("aforo");
                                    ocupacion += String.format("%-15s Reservas:%s   %s/%s\n",nombreSalon,nReservas,nPersonas,aforoSalon);
                                }
                                rf.setHora(jsonArray.getJSONObject(i).getString("value"));
                                rf.setnReservas(reservasTotal);
                                rf.setFecha(String.valueOf(LocalDate.now()));
                                rf.setOcupacion(ocupacion);
                                lista.add(rf);
                            }
                        }
                        connection.disconnect();
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
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
            rvOcupacion.setAdapter(new ReservasFechaAdapter(getActivity().getSupportFragmentManager(),rvOcupacion,tvReservasDiaHora,lista,this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] leerSalones(){
        final JSONArray[] jsonArray = new JSONArray[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/getsalones");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
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
        String[] textos = new String[jsonArray[0].length()+1];
        textos[0] = "--- Seleccione filtro ---";
        for (int i = 0 ; i < jsonArray[0].length() ; i++){
            try {
                JSONObject jsonObject = (JSONObject) jsonArray[0].get(i);
                System.out.println(jsonObject);
                textos[i+1] = String.format("%s - %s",jsonObject.getString("id_salon"), jsonObject.getString("nombre"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        for (String t:textos){
            System.out.println("SPINNER " + t);
        }
        return textos;
    }

    public void cambioSpinner(){
        String opcion = spinnerFiltro.getSelectedItem().toString();
        ReservaAdapter reservaAdapter = (ReservaAdapter) rvOcupacion.getAdapter();
        if (opcion.equals("--- Seleccione filtro ---")){
            reservaAdapter.restaurarDatos();
        }
        else{
            reservaAdapter.filtrarId(Integer.valueOf(opcion.substring(0,opcion.indexOf("-")-1)));
        }
    }

    public String leerTramos(String fecha) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate fechaDate = LocalDate.parse(fecha);
            final JSONArray[] horario = {new JSONArray()};
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/gethorario");
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
                            horario[0] = new JSONObject(response.toString()).getJSONArray("horarios");
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
            int dia = fechaDate.getDayOfWeek().getValue();
            JSONObject jsonObject;
            try {
                jsonObject = (horario[0].getJSONObject(dia - 1));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            incremento = leerIncremento();
            LocalDateTime inicio_m;
            LocalDateTime fin_m;
            LocalDateTime inicio_t;
            LocalDateTime fin_t;
            LocalDateTime[] tramos;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            try {
                inicio_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_m"), dateTimeFormatter);
                fin_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_m"), dateTimeFormatter);
                inicio_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_t"), dateTimeFormatter);
                fin_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_t"), dateTimeFormatter);
                Long tramos_m = inicio_m.until(fin_m, ChronoUnit.MINUTES) / (incremento.getHour() * 60 + incremento.getMinute());
                Long tramos_t = inicio_t.until(fin_t, ChronoUnit.MINUTES) / (incremento.getHour() * 60 + incremento.getMinute());
                tramos = new LocalDateTime[(int) (tramos_m + tramos_t) + 1];
                System.out.println("En teoría se ejecuta " + tramos.length);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            int contador = 0;
            LocalDateTime tramo = inicio_m;
            while (fin_m.isAfter(tramo)) {
                tramos[contador] = tramo;
                System.out.println(tramo + " ejecucion " + (contador + 1) + " tope " + fin_m);
                contador++;
                tramo = tramo.plusHours(incremento.getHour());
                tramo = tramo.plusMinutes(incremento.getMinute());
                if (fin_m.isBefore(tramo)) {
                    break;
                }

            }
            tramo = inicio_t;
            while (fin_t.isAfter(tramo)) {
                tramos[contador] = tramo;
                System.out.println(tramo + " ejecucion " + (contador + 1) + " tope " + fin_t);
                contador++;
                tramo = tramo.plusHours(incremento.getHour());
                tramo = tramo.plusMinutes(incremento.getMinute());
                if (fin_t.isBefore(tramo)) {
                    break;
                }
            }
            String texto = "SELECT '#PARAM1#' AS value ";
            for (LocalDateTime t : tramos) {
                if (texto.contains("'#PARAM1#'")) {
                    texto = texto.replace("#PARAM1#", t.toLocalTime().toString());
                } else {
                    texto += " UNION SELECT '" + t.toLocalTime().toString() + "'";
                }
            }
            System.out.println(texto);
            return texto;

        }
        return null;
    }

    public LocalTime leerIncremento() {
        final LocalTime[] incremento = new LocalTime[1];
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getincremento");
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
                            JSONObject jsonObject = new JSONObject(response.toString()).getJSONObject("resultado");
                            String incrementoStr = jsonObject.getString("duracion_reservas");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                incremento[0] = LocalTime.parse(incrementoStr);
                            }
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
                    }

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incremento[0];
    }

    public void clickSiguiente() {
        comprobarBotones();
        String clase = rvOcupacion.getAdapter().getClass().toString();
        boolean enTramos = clase.equals("class com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter");
        if (!enTramos) {
            String horaOriginal = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo") + 6);
            String horaTramo = horaOriginal.toString();
            LocalDateTime localTime;
            String fecha = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                fecha = LocalDate.now().toString();
                leerTopes(fecha);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                localTime = LocalDateTime.parse(fecha + " " + horaOriginal, dtf);
                localTime = localTime.plusHours(incremento.getHour());
                localTime = localTime.plusMinutes(incremento.getMinute());
                if (localTime.isAfter(hora_fin_m) || localTime.isEqual(hora_fin_m)) {
                    if (LocalDateTime.parse(fecha + " " + horaOriginal, dtf).isBefore(hora_inicio_t)) {
                        localTime = LocalDateTime.parse(fecha + " " + hora_inicio_t.toLocalTime(), dtf);
                    }
                    if (localTime.isAfter(hora_inicio_t)) {
                        if (localTime.isAfter(hora_fin_t)) {
                            localTime = LocalDateTime.parse(fecha + " " + horaOriginal, dtf);
                        }
                    }
                }
                LocalDateTime nextTramo = localTime.plusHours(incremento.getHour());
                nextTramo = nextTramo.plusMinutes(incremento.getMinute());
                if (nextTramo.isAfter(hora_fin_t)) {
                    btSiguiente.setEnabled(false);
                }
                if (nextTramo.isAfter(hora_inicio_m)) {
                    btAnterior.setEnabled(true);
                }
                horaTramo = localTime.toLocalTime().toString();
            }
            JSONArray jsonArray = verReservas(fecha, horaTramo);
            ArrayList<Reserva> lista = new ArrayList<>();
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        int id = jsonObject.getInt("id_reserva");
                        String nombre_apellidos = jsonObject.getString("nombre_apellidos");
                        String telefono = jsonObject.getString("telefono");
                        String email = jsonObject.getString("email");
                        int n_personas = jsonObject.getInt("n_personas");
                        int id_salon = jsonObject.getInt("id_salon");
                        String[] fechaMiembros = jsonObject.getString("fecha").split("-");
                        String fechaBuena = String.format("%s/%s/%s", fechaMiembros[2], fechaMiembros[1], fechaMiembros[0]);
                        String hora = jsonObject.getString("hora");
                        String observaciones = jsonObject.getString("observaciones");
                        Reserva r = new Reserva(id, nombre_apellidos, telefono, email, n_personas, id_salon, fechaBuena, hora, observaciones);
                        lista.add(r);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            rvOcupacion.setAdapter(new ReservaAdapter(lista, getActivity().getSupportFragmentManager(), rvOcupacion));
            tvReservasDiaHora.setText(fecha + " Tramo " + horaTramo);
        }
    }
    public void clickAnterior() {
        comprobarBotones();

        String clase = rvOcupacion.getAdapter().getClass().toString();
        boolean enTramos = clase.equals("class com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter");
        if (!enTramos) {
            String fecha = "";
            String horaOriginal = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo") + 6);
            String horaTramo = horaOriginal.toString();
            LocalDateTime localTime;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                fecha = LocalDate.now().toString();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                localTime = LocalDateTime.parse(fecha + " " + horaOriginal, dtf);
                localTime = localTime.minusHours(incremento.getHour());
                localTime = localTime.minusMinutes(incremento.getMinute());

                if (localTime.isAfter(hora_inicio_m)) {
                    if (localTime.isBefore(hora_inicio_t)) {
                        if (localTime.isAfter(hora_fin_m)) {
                            localTime = LocalDateTime.parse(fecha + " " + hora_fin_m.toLocalTime(), dtf);
                        }
                    }
                }
                LocalDateTime nextTramo = localTime.minusHours(incremento.getHour());
                nextTramo = nextTramo.minusMinutes(incremento.getMinute());
                if (nextTramo.isBefore(hora_inicio_m)) {
                    btAnterior.setEnabled(false);
                }
                if (nextTramo.isBefore(hora_fin_t)) {
                    btSiguiente.setEnabled(true);
                    btSiguiente.setAlpha(1);
                }
                horaTramo = localTime.toLocalTime().toString();
            }
            JSONArray jsonArray = verReservas(fecha, horaTramo);
            ArrayList<Reserva> lista = new ArrayList<>();
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        int id = jsonObject.getInt("id_reserva");
                        String nombre_apellidos = jsonObject.getString("nombre_apellidos");
                        String telefono = jsonObject.getString("telefono");
                        String email = jsonObject.getString("email");
                        int n_personas = jsonObject.getInt("n_personas");
                        int id_salon = jsonObject.getInt("id_salon");
                        String[] fechaMiembros = jsonObject.getString("fecha").split("-");
                        String fechaBuena = String.format("%s/%s/%s", fechaMiembros[2], fechaMiembros[1], fechaMiembros[0]);
                        String hora = jsonObject.getString("hora");
                        String observaciones = jsonObject.getString("observaciones");
                        Reserva r = new Reserva(id, nombre_apellidos, telefono, email, n_personas, id_salon, fechaBuena, hora, observaciones);
                        lista.add(r);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            rvOcupacion.setAdapter(new ReservaAdapter(lista, getActivity().getSupportFragmentManager(), rvOcupacion));
            tvReservasDiaHora.setText(fecha + " Tramo " + horaTramo);
        }
    }
    public void clickVolver(){
        cargarOcupacion();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvReservasDiaHora.setText(LocalDate.now().toString());
        }
        comprobarBotones();
    }
    public void leerTopes(String fecha) {
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        LocalDate localDate;
                        String dia = "";
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            localDate = LocalDate.parse(fecha);
                            switch (localDate.getDayOfWeek().getValue()) {
                                case 1:
                                    dia = "Lunes";
                                    break;
                                case 2:
                                    dia = "Martes";
                                    break;
                                case 3:
                                    dia = "Miércoles";
                                    break;
                                case 4:
                                    dia = "Jueves";
                                    break;
                                case 5:
                                    dia = "Viernes";
                                    break;
                                case 6:
                                    dia = "Sábado";
                                    break;
                                case 7:
                                    dia = "Domingo";
                                    break;
                            }
                        }

                        URL url = new URL("https://reservante.mjhudesings.com/slim/getopes");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonRequest = "{\"dia\": \"#PARAMDIA#\"}";
                        jsonRequest = jsonRequest.replace("#PARAMDIA#", dia);
                        osw.write(jsonRequest);
                        osw.flush();
                        int responseCode = connection.getResponseCode();
                        System.out.println("RESPUESTA: " + responseCode + "-" + HttpURLConnection.HTTP_OK + " " + jsonRequest);
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
                            JSONObject jsonObject = new JSONObject(response.toString()).getJSONArray("horario").getJSONObject(0);
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                hora_inicio_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_m"), dtf);
                                hora_fin_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_m"), dtf);
                                hora_inicio_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_t"), dtf);
                                hora_fin_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_t"), dtf);
                            }
                            System.out.println(jsonObject);
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
                    }

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONArray verReservas(String fecha, String hora) {
        final JSONArray[] jsonArray = new JSONArray[1];
        try {
            ArrayList<ReservaFechas> lista = new ArrayList<>();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getreservahora");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonRequest = "{\"fecha\": \"#PARAMFECHA#\",\"hora\":\"#PARAMHORA#\"\n}";
                        jsonRequest = jsonRequest.replace("#PARAMFECHA#", fecha);
                        jsonRequest = jsonRequest.replace("#PARAMHORA#", hora);
                        osw.write(jsonRequest);
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
                            jsonArray[0] = new JSONObject(response.toString()).getJSONArray("reservas");
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
                    }

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray[0];
    }

    public void comprobarBotones() {
        String clase = rvOcupacion.getAdapter().getClass().toString();
        boolean flag = !clase.equals("class com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter");
        System.out.println(clase);
        btReservar.setEnabled(flag);
        btAnterior.setEnabled(flag);
        btSiguiente.setEnabled(flag);
        btVolverInicio.setEnabled(flag);
        spinnerFiltro.setEnabled(flag);
    }

}