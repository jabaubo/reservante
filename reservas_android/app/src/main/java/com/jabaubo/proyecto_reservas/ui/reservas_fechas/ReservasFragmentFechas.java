package com.jabaubo.proyecto_reservas.ui.reservas_fechas;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentReservasFechaBinding;
import com.jabaubo.proyecto_reservas.ui.ReservaDialog;
import com.jabaubo.proyecto_reservas.ui.reservas.MyAdapter;
import com.jabaubo.proyecto_reservas.ui.reservas.Reserva;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReservasFragmentFechas extends Fragment {
    /*
    * json fecha
    * {"fecha": "2024-04-08"}
    * */
    private FragmentReservasFechaBinding binding;
    private CalendarView calendarView;
    private TextView tvReservasDiaHora;
    private RecyclerView rvOcupacion;
    private Button btnReservar;
    private Button btSiguienteDia;
    private Button btAnteriorDia;
    private Button btLayoutCalendario;
    private ScrollView scrollView;
    private LocalTime incremento;
    private LocalDateTime hora_inicio_m;
    private LocalDateTime hora_fin_m;
    private LocalDateTime hora_inicio_t;
    private LocalDateTime hora_fin_t;
    //private ArrayList<String> tramos;
    public String horaSeleccionada;
    public String fechaSeleccionada;

    public TextView getTvReservasDiaHora() {
        return tvReservasDiaHora;
    }
    public ReservasFragmentFechas singleton(){
        if (this.equals(null)){
            return new ReservasFragmentFechas();
        }
        return this;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReservasFechaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        calendarView = root.findViewById(R.id.calendarView);
        calendarView.setDate(Calendar.getInstance().getTimeInMillis()-86400000);
        ViewGroup.LayoutParams layoutParams = calendarView.getLayoutParams();
        layoutParams.height = (int) (root.getHeight()*0.9);
        calendarView.setLayoutParams(layoutParams);
        rvOcupacion = root.findViewById(R.id.rvOcupacion);
        tvReservasDiaHora = root.findViewById(R.id.tvReservasDiaHora);
        btSiguienteDia = root.findViewById(R.id.btnSiguienteDia);
        btAnteriorDia = root.findViewById(R.id.btnAnteriorDia);
        btnReservar = root.findViewById(R.id.btnReservar);
        btLayoutCalendario = root.findViewById(R.id.btCalendarReservaFechas);
        scrollView = root.findViewById(R.id.svReservas);
        rvOcupacion.setLayoutManager(new LinearLayoutManager(this.getContext()));
        incremento = leerIncremento();
        System.out.println("INCREMENTO = " + incremento);
        final ArrayList<ReservaFechas>[] lista = new ArrayList[]{new ArrayList<>()};
        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(calendarView.getDate());
                System.out.println(calendar.getTime());
                cargarOcupacion(calendar.getTime().toString());
                leerTopes(calendar.getTime().toString());
                ViewGroup.LayoutParams layoutParams = calendarView.getLayoutParams();
                layoutParams.height=1;
                calendarView.setLayoutParams(layoutParams);
                calendarView.setDate(calendar.getTimeInMillis());
                tvReservasDiaHora.setText(String.format("%d/%d/%d",calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.YEAR)));
                btSiguienteDia.setAlpha(1f);
                btSiguienteDia.setEnabled(true);
                btAnteriorDia.setAlpha(1f);
                btAnteriorDia.setEnabled(true);
                btLayoutCalendario.setAlpha(1f);
                btLayoutCalendario.setClickable(true);
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month+1;
                String fecha = year+"-";
                if (month < 10){
                    fecha += 0;
                }
                fecha+=month+"-";
                if (dayOfMonth < 10){
                    fecha += 0;
                }
                fecha+=dayOfMonth;
                cargarOcupacion(fecha);
                leerTopes(fecha);
                ViewGroup.LayoutParams layoutParams = calendarView.getLayoutParams();
                layoutParams.height=1;
                calendarView.setLayoutParams(layoutParams);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month-1,dayOfMonth);
                calendarView.setDate(calendar.getTimeInMillis());
                tvReservasDiaHora.setText(String.format("%d/%d/%d",dayOfMonth,month,year));
                btSiguienteDia.setAlpha(1f);
                btSiguienteDia.setEnabled(true);
                btAnteriorDia.setAlpha(1f);
                btAnteriorDia.setEnabled(true);
                btLayoutCalendario.setAlpha(1f);
                btLayoutCalendario.setClickable(true);
            }

        });
        rvOcupacion.setAdapter(new ReservasFechaAdapter(getActivity().getSupportFragmentManager(),rvOcupacion,tvReservasDiaHora,lista[0],this));
        ReservasFragmentFechas reservasFragmentFechas = this;
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clase = rvOcupacion.getAdapter().getClass().toString();
                if (clase.equals("class com.jabaubo.proyecto_reservas.ui.reservas_fechas.ReservasFechaAdapter")){
                    Snackbar.make(root, "Ocupacion", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(calendarView.getDate());
                    String fecha = calendar.get(Calendar.YEAR)+"-";
                    if ((calendar.get(Calendar.MONTH)+1)<10){
                        fecha+=0;
                    }
                    fecha+=(calendar.get(Calendar.MONTH)+1)+"-";
                    if (calendar.get(Calendar.DAY_OF_MONTH)<10){
                        fecha+=0;
                    }
                    fecha+=calendar.get(Calendar.DAY_OF_MONTH);
                    String hora = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo")+6);
                    ReservaDialog reservaDialog = new ReservaDialog(getView(),hora,fecha,reservasFragmentFechas);
                    reservaDialog.show(getActivity().getSupportFragmentManager(), "A");
                    ViewGroup.LayoutParams layoutParams = calendarView.getLayoutParams();
                    layoutParams.height = 1;
                    calendarView.setLayoutParams(layoutParams);
                }
            }
        });
        btSiguienteDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteDia(calendarView);
            }
        });
        btAnteriorDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anteriorDia(calendarView);
            }
        });
        btLayoutCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutConCalendario();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void cargarOcupacion( String fecha){
        try {
            ArrayList<ReservaFechas> lista = new ArrayList<>();
            Runnable runnable= new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getocupacion");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Accept", "application/json");
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String consulta = leerTramos(fecha);
                        String jsonFecha = "{\"consulta\":\"SELECT range_values.value, COUNT(*) AS n_reservas, COALESCE(SUM(reservas.n_personas), 0) AS n_personas, (SELECT SUM(aforo) FROM salones) AS aforo FROM (#tramos#) AS range_values LEFT JOIN reservas ON range_values.value = reservas.hora AND reservas.fecha = '#PARAMFECHA#' GROUP BY range_values.value ORDER BY range_values.value;\"}";
                        jsonFecha = jsonFecha.replace("#tramos#",consulta);
                        jsonFecha = jsonFecha.replace("#PARAMFECHA#",fecha);
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
                                ReservaFechas rf = new ReservaFechas(hora,n_reservas,n_personas,aforo,fecha);
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
            rvOcupacion.setAdapter(new ReservasFechaAdapter(getActivity().getSupportFragmentManager(),rvOcupacion,tvReservasDiaHora,lista,this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void siguienteDia(CalendarView calendarView){
        String clase = rvOcupacion.getAdapter().getClass().toString();
        if (clase.equals("class com.jabaubo.proyecto_reservas.ui.reservas_fechas.ReservasFechaAdapter")){
            calendarView.setDate(calendarView.getDate()+86400000);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calendarView.getDate());
            String fecha = calendar.get(Calendar.YEAR)+"-";
            if ((calendar.get(Calendar.MONTH)+1)<10){
                fecha+=0;
            }
            fecha+=(calendar.get(Calendar.MONTH)+1)+"-";
            if (calendar.get(Calendar.DAY_OF_MONTH)<10){
                fecha+=0;
            }
            fecha+=calendar.get(Calendar.DAY_OF_MONTH);
            tvReservasDiaHora.setText(calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR));
            cargarOcupacion(fecha);
            leerTopes(fecha);
        }else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calendarView.getDate());
            String fecha = calendar.get(Calendar.YEAR)+"-";
            if ((calendar.get(Calendar.MONTH)+1)<10){
                fecha+=0;
            }
            fecha+=(calendar.get(Calendar.MONTH)+1)+"-";
            if (calendar.get(Calendar.DAY_OF_MONTH)<10){
                fecha+=0;
            }
            fecha+=calendar.get(Calendar.DAY_OF_MONTH);
            String horaOriginal = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo")+6);
            String horaTramo = horaOriginal.toString();
            LocalDateTime localTime;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                localTime = LocalDateTime.parse(fecha + " " +horaOriginal,dtf);
                localTime = localTime.plusHours(incremento.getHour());
                localTime= localTime.plusMinutes(incremento.getMinute());
                if (localTime.isAfter(hora_fin_m)){
                    if (LocalDateTime.parse(fecha+ " " + horaOriginal,dtf).isBefore(hora_inicio_t)){
                        localTime = LocalDateTime.parse(fecha + " " + hora_inicio_t.toLocalTime(),dtf);
                    }
                    if (localTime.isAfter(hora_inicio_t) ){
                        if (localTime.isAfter(hora_fin_t)){
                            localTime = LocalDateTime.parse(fecha + " " +horaOriginal,dtf);
                        }
                    }
                }
                LocalDateTime nextTramo = localTime.plusHours(incremento.getHour());
                nextTramo = nextTramo.plusMinutes(incremento.getMinute());
                if (nextTramo.isAfter(hora_fin_t)){
                    btSiguienteDia.setEnabled(false);
                    btSiguienteDia.setAlpha(0);
                }
                if (nextTramo.isAfter(hora_inicio_m)){
                    btAnteriorDia.setEnabled(true);
                    btAnteriorDia.setAlpha(1f);
                }
                horaTramo = localTime.toLocalTime().toString();
            }
            JSONArray jsonArray = verReservas(fecha,horaTramo);
            ArrayList<Reserva> lista = new ArrayList<>();
            if (jsonArray != null){
                for (int i  = 0 ; i < jsonArray.length() ; i++){
                    try {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        int id = jsonObject.getInt("id_reserva");
                        String nombre_apellidos = jsonObject.getString("nombre_apellidos");
                        String telefono = jsonObject.getString("telefono");
                        String email = jsonObject.getString("email");
                        int n_personas = jsonObject.getInt("n_personas");
                        int id_salon = jsonObject.getInt("id_salon");
                        String[] fechaMiembros = jsonObject.getString("fecha").split("-");
                        String fechaBuena = String.format("%s/%s/%s",fechaMiembros[2],fechaMiembros[1],fechaMiembros[0]);
                        String hora = jsonObject.getString("hora");
                        String observaciones = jsonObject.getString("observaciones");
                        Reserva r = new Reserva(id,nombre_apellidos,telefono,email,n_personas,id_salon,fechaBuena,hora,observaciones);
                        lista.add(r);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            rvOcupacion.setAdapter(new MyAdapter(lista,getActivity().getSupportFragmentManager(),rvOcupacion));
            tvReservasDiaHora.setText(fecha + " Tramo " + horaTramo);

        }
    }
    public void anteriorDia(CalendarView calendarView){
        String clase = rvOcupacion.getAdapter().getClass().toString();
        if (clase.equals("class com.jabaubo.proyecto_reservas.ui.reservas_fechas.ReservasFechaAdapter")){
            calendarView.setDate(calendarView.getDate()-86400000);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calendarView.getDate());
            tvReservasDiaHora.setText(calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR));
            String fecha = calendar.get(Calendar.YEAR)+"-";
            if ((calendar.get(Calendar.MONTH)+1)<10){
                fecha+=0;
            }
            fecha+=(calendar.get(Calendar.MONTH)+1)+"-";
            if (calendar.get(Calendar.DAY_OF_MONTH)<10){
                fecha+=0;
            }
            fecha+=calendar.get(Calendar.DAY_OF_MONTH);
            cargarOcupacion(fecha);
        }
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calendarView.getDate());
            String fecha = calendar.get(Calendar.YEAR)+"-";
            if ((calendar.get(Calendar.MONTH)+1)<10){
                fecha+=0;
            }
            fecha+=(calendar.get(Calendar.MONTH)+1)+"-";
            if (calendar.get(Calendar.DAY_OF_MONTH)<10){
                fecha+=0;
            }
            fecha+=calendar.get(Calendar.DAY_OF_MONTH);
            String horaOriginal = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo")+6);
            String horaTramo = horaOriginal.toString();
            LocalDateTime localTime;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                localTime = LocalDateTime.parse(fecha + " " +horaOriginal,dtf);
                localTime = localTime.minusHours(incremento.getHour());
                localTime= localTime.minusMinutes(incremento.getMinute());

                if (localTime.isAfter(hora_inicio_m)){
                    if (localTime.isBefore(hora_inicio_t)){
                        if (localTime.isAfter(hora_fin_m)){
                            localTime = LocalDateTime.parse(fecha + " " + hora_fin_m.toLocalTime(),dtf);
                        }
                    }/*
                    if (LocalDateTime.parse(fecha+ " " + horaOriginal,dtf).isBefore(hora_inicio_t)){
                        localTime = LocalDateTime.parse(fecha + " " + hora_fin_t);
                    }
                    else if (localTime.isBefore(hora_inicio_t) ){
                        if (localTime.isBefore(hora_fin_t)){
                            localTime = LocalDateTime.parse(fecha + " " +horaOriginal,dtf);
                        }
                    }*/
                }
                LocalDateTime nextTramo = localTime.minusHours(incremento.getHour());
                nextTramo = nextTramo.minusMinutes(incremento.getMinute());
                if (nextTramo.isBefore(hora_inicio_m)){
                    btAnteriorDia.setEnabled(false);
                    btAnteriorDia.setAlpha(0);
                }
                if (nextTramo.isBefore(hora_fin_t)){
                    btSiguienteDia.setEnabled(true);
                    btSiguienteDia.setAlpha(1);
                }
                horaTramo = localTime.toLocalTime().toString();
            }
            JSONArray jsonArray = verReservas(fecha,horaTramo);
            ArrayList<Reserva> lista = new ArrayList<>();
            if (jsonArray != null){
                for (int i  = 0 ; i < jsonArray.length() ; i++){
                    try {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        int id = jsonObject.getInt("id_reserva");
                        String nombre_apellidos = jsonObject.getString("nombre_apellidos");
                        String telefono = jsonObject.getString("telefono");
                        String email = jsonObject.getString("email");
                        int n_personas = jsonObject.getInt("n_personas");
                        int id_salon = jsonObject.getInt("id_salon");
                        String[] fechaMiembros = jsonObject.getString("fecha").split("-");
                        String fechaBuena = String.format("%s/%s/%s",fechaMiembros[2],fechaMiembros[1],fechaMiembros[0]);
                        String hora = jsonObject.getString("hora");
                        String observaciones = jsonObject.getString("observaciones");
                        Reserva r = new Reserva(id,nombre_apellidos,telefono,email,n_personas,id_salon,fechaBuena,hora,observaciones);
                        lista.add(r);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            rvOcupacion.setAdapter(new MyAdapter(lista,getActivity().getSupportFragmentManager(),rvOcupacion));
            tvReservasDiaHora.setText(fecha + " Tramo " + horaTramo);

        }

    }
    public void layoutConCalendario(){
        calendarView.setDate(Calendar.getInstance().getTimeInMillis()-86400000);
        ViewGroup.LayoutParams layoutParams = calendarView.getLayoutParams();
        layoutParams.height = (int) (this.getView().getHeight()*0.9);
        calendarView.setLayoutParams(layoutParams);
        tvReservasDiaHora.setText("");
        rvOcupacion.setAdapter(null);
        btLayoutCalendario.setAlpha(0);
        btLayoutCalendario.setClickable(false);
        btAnteriorDia.setAlpha(0);
        btAnteriorDia.setEnabled(false);
        btSiguienteDia.setAlpha(0);
        btSiguienteDia.setEnabled(false);
    }
    public JSONArray verReservas(String fecha , String hora){
        final JSONArray[] jsonArray = new JSONArray[1];
        try {
            ArrayList<ReservaFechas> lista = new ArrayList<>();
            Runnable runnable= new Runnable() {
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
                        jsonRequest = jsonRequest.replace("#PARAMFECHA#",fecha);
                        jsonRequest = jsonRequest.replace("#PARAMHORA#",hora);
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
    public void leerTopes(String fecha){
        try {
            Runnable runnable= new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        LocalDate localDate ;
                        String dia = "";
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            localDate= LocalDate.parse(fecha);
                            switch (localDate.getDayOfWeek().getValue()){
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
                        jsonRequest = jsonRequest.replace("#PARAMDIA#",dia);
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
                                hora_inicio_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_m"),dtf);
                                hora_fin_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_m"),dtf);
                                hora_inicio_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_t"),dtf);
                                hora_fin_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_t"),dtf);
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
    public void avisarBorradoRecyclerView(int position){
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rvOcupacion.getAdapter().notifyItemRemoved(position);
            }
        });
    }

    public RecyclerView getRvOcupacion() {
        return rvOcupacion;
    }
}