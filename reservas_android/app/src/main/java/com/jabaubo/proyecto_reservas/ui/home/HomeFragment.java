package com.jabaubo.proyecto_reservas.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private LocalTime incremento;

    private RecyclerView rvOcupacion;
    private TextView tvReservasDiaHora;
    private Button btSiguiente;
    private Button btAnterior;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvOcupacion = root.findViewById(R.id.rvOcupacionInicio);
        rvOcupacion.setLayoutManager(new LinearLayoutManager(this.getContext()));
        tvReservasDiaHora = root.findViewById(R.id.tvReservasDiaHoraInicio);
        cargarOcupacion();
        System.out.println("TENGO " + rvOcupacion.getAdapter().getItemCount());;
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            consulta = leerTramos(String.valueOf(LocalDate.now()));
                            System.out.println("LAFECHA:" + LocalDate.now());
                        }
                        String jsonFecha = "{\"consulta\":\"SELECT range_values.value, COUNT(*) AS n_reservas, COALESCE(SUM(reservas.n_personas), 0) AS n_personas, (SELECT SUM(aforo) FROM salones) AS aforo FROM (#tramos#) AS range_values LEFT JOIN reservas ON range_values.value = reservas.hora AND reservas.fecha = '#PARAMFECHA#' GROUP BY range_values.value ORDER BY range_values.value;\"}";
                        jsonFecha = jsonFecha.replace("#tramos#",consulta);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            jsonFecha = jsonFecha.replace("#PARAMFECHA#",LocalDate.now().toString());
                        }
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
                            for (int i = 0 ; i < jsonArray.length() ; i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                System.out.println(json);
                                String hora = json.getString("value");
                                int n_personas = 0;
                                if (!json.get("n_personas").equals(null)) {
                                    n_personas = json.getInt("n_personas");
                                }
                                int n_reservas = 0;
                                if (n_personas > 0 && !json.get("n_reservas").equals(null)) {
                                    n_reservas = json.getInt("n_reservas");
                                }
                                int aforo = json.getInt("aforo");
                                ReservaFechas rf = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    rf = new ReservaFechas(hora,n_reservas,n_personas,aforo, LocalDate.now().toString());
                                }
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
                        e.printStackTrace();
                    }

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
            for (int i = 0 ; i < lista.size() ; i++){
                System.out.println("Iteracion " + (i+1) + " " + lista.get(i));
            }
            rvOcupacion.setAdapter(new ReservasFechaAdapter(getActivity().getSupportFragmentManager(),rvOcupacion,tvReservasDiaHora,lista));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String leerTramos(String fecha){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate fechaDate = LocalDate.parse(fecha);
            final JSONArray[] horario = {new JSONArray()};
            Runnable runnable= new Runnable() {
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
                            horario[0] =  new JSONObject(response.toString()).getJSONArray("horarios");
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
                jsonObject = (horario[0].getJSONObject(dia-1));
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
                inicio_m = LocalDateTime.parse(fecha +" " + jsonObject.getString("hora_inicio_m"),dateTimeFormatter);
                fin_m = LocalDateTime.parse(fecha +" " + jsonObject.getString("hora_fin_m"),dateTimeFormatter);
                inicio_t = LocalDateTime.parse(fecha +" " + jsonObject.getString("hora_inicio_t"),dateTimeFormatter);
                fin_t = LocalDateTime.parse(fecha +" " + jsonObject.getString("hora_fin_t"),dateTimeFormatter);
                Long tramos_m = inicio_m.until(fin_m, ChronoUnit.MINUTES)/(incremento.getHour()*60+incremento.getMinute());
                Long tramos_t = inicio_t.until(fin_t, ChronoUnit.MINUTES)/(incremento.getHour()*60+incremento.getMinute());
                tramos = new LocalDateTime[(int) (tramos_m + tramos_t)+1];
                System.out.println("En teoría se ejecuta " + tramos.length);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            int contador = 0;
            LocalDateTime tramo = inicio_m;
            while (fin_m.isAfter(tramo)){
                tramos[contador] = tramo;
                System.out.println(tramo + " ejecucion " + (contador+1) + " tope " + fin_m);
                contador++;
                tramo = tramo.plusHours(incremento.getHour());
                tramo = tramo.plusMinutes(incremento.getMinute());
                if (fin_m.isBefore(tramo)){
                    break;
                }

            }
            tramo = inicio_t;
            while (fin_t.isAfter(tramo)){
                tramos[contador] = tramo;
                System.out.println(tramo + " ejecucion " + (contador+1) + " tope " + fin_t );
                contador++;
                tramo = tramo.plusHours(incremento.getHour());
                tramo = tramo.plusMinutes(incremento.getMinute());
                if (fin_t.isBefore(tramo)){
                    break;
                }
            }
            String texto = "SELECT '#PARAM1#' AS value ";
            for (LocalDateTime t:tramos){
                if (texto.contains("'#PARAM1#'")){
                    texto = texto.replace("#PARAM1#",t.toLocalTime().toString());
                }
                else {
                    texto += " UNION SELECT '" + t.toLocalTime().toString() + "'";
                }
            }
            System.out.println(texto);
            return  texto;

        }
        return null;
    }
    public LocalTime leerIncremento(){
        final LocalTime[] incremento = new LocalTime[1];
        try {
            Runnable runnable= new Runnable() {
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
}