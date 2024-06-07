package com.jabaubo.proyecto_reservas.ui.reservas_fechas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.MainActivity;
import com.jabaubo.proyecto_reservas.Objetos.Ocupacion;
import com.jabaubo.proyecto_reservas.Objetos.ReservaFechas;
import com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentReservasFechaBinding;
import com.jabaubo.proyecto_reservas.ui.ReservaDialog;
import com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter;
import com.jabaubo.proyecto_reservas.Objetos.Reserva;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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

public class ReservasFragmentFechas extends Fragment {

    private FragmentReservasFechaBinding binding;
    private CalendarView calendarView;
    private TextView tvReservasDiaHora;
    private RecyclerView rvOcupacion;
    private Button btnReservar;
    private Button btSiguienteDia;
    private Button btAnteriorDia;
    private Button btLayoutCalendario;
    private ScrollView scrollView;
    private Spinner spinnerFiltro;

    private LocalTime incremento;
    private LocalDateTime hora_inicio_m;
    private LocalDateTime hora_fin_m;
    private LocalDateTime hora_inicio_t;
    private LocalDateTime hora_fin_t;
    private String[] salones;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReservasFechaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Cargamos los campos
        calendarView = root.findViewById(R.id.calendarView);
        rvOcupacion = root.findViewById(R.id.rvOcupacion);
        tvReservasDiaHora = root.findViewById(R.id.tvReservasDiaHora);
        btSiguienteDia = root.findViewById(R.id.btnSiguienteDia);
        btAnteriorDia = root.findViewById(R.id.btnAnteriorDia);
        btnReservar = root.findViewById(R.id.btnReservar);
        btLayoutCalendario = root.findViewById(R.id.btCalendarReservaFechas);
        scrollView = root.findViewById(R.id.svReservas);
        spinnerFiltro = root.findViewById(R.id.spinFiltro);

        //Cargamos los valores
        calendarView.setDate(Calendar.getInstance().getTimeInMillis() - 86400000);
        ViewGroup.LayoutParams layoutParams = calendarView.getLayoutParams();
        layoutParams.height = (int) (root.getHeight() * 0.9);
        calendarView.setLayoutParams(layoutParams);
        /*
         * Leemos los salones
         * Hay -> Preparación
         * No hay -> Avisamos y no dejamos habilitado nada*/
        salones = leerSalones();
        if (salones != null) {
            //Cargamos el filtro
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, salones);
            spinnerFiltro.setAdapter(adapter);
            spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (rvOcupacion.getAdapter().getClass().toString().equals("class com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter")) {
                        cambioSpinner();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            rvOcupacion.setLayoutManager(new LinearLayoutManager(this.getContext()));
            incremento = leerIncremento();
            final ArrayList<ReservaFechas>[] lista = new ArrayList[]{new ArrayList<>()};
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    if (year == 1900) {
                        calendarView.setDate(Calendar.getInstance().getTimeInMillis());
                    }
                    ViewGroup.LayoutParams layoutParams = calendarView.getLayoutParams();
                    layoutParams.height = 1;
                    calendarView.setLayoutParams(layoutParams);
                    //Formateamos la fecha
                    month = month + 1;
                    String fecha = year + "-";
                    if (month < 10) {
                        fecha += 0;
                    }
                    fecha += month + "-";
                    if (dayOfMonth < 10) {
                        fecha += 0;
                    }
                    fecha += dayOfMonth;
                    if (calendarView.getLayoutParams().height == 1) {
                        cargarOcupacion(fecha);
                    }
                    //Cargamos los topes para ese día
                    leerTopes(fecha);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month - 1, dayOfMonth);
                    calendarView.setDate(calendar.getTimeInMillis());
                    tvReservasDiaHora.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
                    comprobarBotones();
                }
            });
            rvOcupacion.setAdapter(new ReservasFechaAdapter(getActivity().getSupportFragmentManager(), rvOcupacion, tvReservasDiaHora, lista[0], this));
            ReservasFragmentFechas reservasFragmentFechas = this;
            btnReservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Comprobamos la clase
                    String clase = rvOcupacion.getAdapter().getClass().toString();
                    if (clase.equals("class com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter")) {
                        Snackbar.make(root, "Ocupacion", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        //Leemos y formateamos la fecha
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(calendarView.getDate());
                        String fecha = calendar.get(Calendar.YEAR) + "-";
                        if ((calendar.get(Calendar.MONTH) + 1) < 10) {
                            fecha += 0;
                        }
                        fecha += (calendar.get(Calendar.MONTH) + 1) + "-";
                        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                            fecha += 0;
                        }
                        fecha += calendar.get(Calendar.DAY_OF_MONTH);
                        String hora = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo") + 6);
                        //Abrimos el dialog para insertar
                        ReservaDialog reservaDialog = new ReservaDialog(getView(), hora, fecha, reservasFragmentFechas);
                        reservaDialog.show(getActivity().getSupportFragmentManager(), "A");
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
            comprobarBotones();
        } else {
            comprobarBotones();
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage("No hay salones,revise la configuración")
                    .setPositiveButton(android.R.string.yes, null)
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert).create();
            alertDialog.show();
        }
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void cargarOcupacion(String fecha) {
        JSONObject[] json = new JSONObject[1];
        String consulta = leerTramos(fecha);
        if (consulta == null) {
            //Si la consulta da null ,rellenamos con un arraylist vacío
            String clase = rvOcupacion.getAdapter().getClass().getName();
            if (clase.equals("com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter")) {
                ((ReservaAdapter) rvOcupacion.getAdapter()).setDataList(new ArrayList<>());
                rvOcupacion.getAdapter().notifyDataSetChanged();
                System.out.println();
            } else if (clase.equals("com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter")) {
                ((ReservasFechaAdapter) rvOcupacion.getAdapter()).setDataList(new ArrayList<>());
                rvOcupacion.getAdapter().notifyDataSetChanged();
            }
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage("Revise el horario del día (Horario) y la duración de las reservas (Configuración)")
                    .setPositiveButton(android.R.string.yes, null)
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert).create();
            alertDialog.show();
        } else {
            try {
                //Preparamos la petición
                Runnable runnable = new Runnable() {
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
                            //Escribimos el json
                            OutputStream os = connection.getOutputStream();
                            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                            String jsonFecha = "{\n"
                                    + "    \"consulta\":\"SELECT range_values.value,salones.nombre,(SELECT COUNT(*) FROM salones WHERE id_restaurante = #PARAMID#) as n_salones,COUNT(reservas.id_salon) AS n_reservas,COALESCE(SUM(reservas.n_personas), 0) AS n_personas,salones.aforo AS aforo  FROM (#TRAMOS#) AS range_values  CROSS JOIN salones on salones.id_salon in (SELECT id_salon FROM salones WHERE id_restaurante = #PARAMID#) LEFT JOIN reservas ON range_values.value = reservas.hora AND reservas.fecha = '#PARAMFECHA#' AND salones.id_salon = reservas.id_salon  GROUP BY range_values.value, salones.id_salon  ORDER BY range_values.value ASC\",\n"
                                    + "    \"fecha\":\"#PARAMFECHA#\",\n"
                                    + "    \"id_restaurante\":\"#PARAMID#\",\n"
                                    + "    \"dia\":\"#PARAMDIA#\"}";
                            jsonFecha = jsonFecha.replace("#TRAMOS#", consulta);
                            jsonFecha = jsonFecha.replace("#PARAMFECHA#", fecha);
                            jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(((MainActivity) getActivity()).getIdRestaurante()));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                jsonFecha = jsonFecha.replace("#PARAMDIA#", String.valueOf(LocalDate.parse(fecha).getDayOfWeek().getValue()));
                            }
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
                                System.out.println("RESPUESTA api " + response);
                                json[0] = new JSONObject(response.toString());
                                System.out.println("JSON en ARRAY hilo: " + json[0]);
                            }
                            connection.disconnect();

                        } catch (
                                MalformedURLException e) {
                            throw new RuntimeException(e);
                        } catch (
                                ProtocolException e) {
                            throw new RuntimeException(e);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } catch (
                                IOException ex) {
                        }

                    }
                };
                //Arrancamos la petición y la sincronizamos para que la app espere el resultado
                Thread thread = new Thread(runnable);
                thread.start();
                thread.join();
                //Comprobamos si el restaurante está cerrado
                if (json[0].toString().contains("restaurante cerrado")) {
                    Snackbar.make(getView(), "El restaurante está cerrado", Snackbar.LENGTH_SHORT).show();
                    ReservasFechaAdapter adapter = (ReservasFechaAdapter) rvOcupacion.getAdapter();
                    adapter.setDataList(new ArrayList<ReservaFechas>());
                    adapter.notifyDataSetChanged();
                    rvOcupacion.setAdapter(adapter);

                }
                //Comprobamos si el restaurante está de vacaciones
                else if (json[0].toString().contains("restaurante de vacaciones")) {
                    Snackbar.make(getView(), "El restaurante está de vacaciones", Snackbar.LENGTH_SHORT).show();
                    ReservasFechaAdapter adapter = (ReservasFechaAdapter) rvOcupacion.getAdapter();
                    adapter.setDataList(new ArrayList<ReservaFechas>());
                    adapter.notifyDataSetChanged();
                    rvOcupacion.setAdapter(adapter);
                }
                //Si no ha saltado ninguno anterior , hay reservas
                else {
                    if (json[0].getInt("codigo") == 1) {
                        JSONArray jsonArray = json[0].getJSONArray("reservas");
                        int n_salones = jsonArray.getJSONObject(0).getInt("n_salones");
                        ArrayList<ReservaFechas> lista = new ArrayList<>();
                        //Vamos cargando la ocupacion recorriendo el jsonArray
                        for (int i = 0; i < jsonArray.length(); i += n_salones) {
                            int reservasTotal = 0;
                            String ocupacion = "";
                            for (int j = i; j < (i + n_salones); j++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                System.out.println(jsonObject);
                                String nombreSalon = jsonObject.getString("nombre");
                                String nReservas = jsonObject.getString("n_reservas");
                                reservasTotal += Integer.valueOf(nReservas);
                                String nPersonas = jsonObject.getString("n_personas");
                                String aforoSalon = jsonObject.getString("aforo");
                                float ratio = Float.parseFloat(nPersonas) / Float.parseFloat(aforoSalon);
                                if (ratio < 0.33f) {
                                    ocupacion += String.format("%s <font color='#008000'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                                } else if (ratio < 0.66f) {
                                    ocupacion += String.format("%s <font color='#FFEB00'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                                } else {
                                    ocupacion += String.format("%s <font color='#8B0000'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                                }

                            }
                            ReservaFechas r = new ReservaFechas();
                            r.setHora(jsonArray.getJSONObject(i).getString("value"));
                            r.setnReservas(reservasTotal);
                            r.setOcupacion(ocupacion);
                            r.setFecha(fecha);
                            lista.add(r);
                        }
                        rvOcupacion.setAdapter(new ReservasFechaAdapter(getChildFragmentManager(), rvOcupacion, tvReservasDiaHora, lista, this));
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle("Error")
                                .setMessage("No hay reservas")
                                .setPositiveButton(android.R.string.yes, null)
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert).create();
                        alertDialog.show();
                    }


                }
            } catch (InterruptedException ex) {
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void siguienteDia(CalendarView calendarView) {
        String clase = rvOcupacion.getAdapter().getClass().toString();
        /*
         * Vemos la clase del adapter
         * ReservasFechaAdapter-> Estamos en los días , hay que pasar al siguiente día
         *ReservasAdapter-> Estamos en los tramos , hay que pasar al siguiente tramo */
        if (clase.equals("class com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter")) {
            //Leemos y formateamos la fecha
            calendarView.setDate(calendarView.getDate() + 86400000);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calendarView.getDate());
            String fecha = calendar.get(Calendar.YEAR) + "-";
            if ((calendar.get(Calendar.MONTH) + 1) < 10) {
                fecha += 0;
            }
            fecha += (calendar.get(Calendar.MONTH) + 1) + "-";
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                fecha += 0;
            }
            fecha += calendar.get(Calendar.DAY_OF_MONTH);
            tvReservasDiaHora.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
            cargarOcupacion(fecha);
            leerTopes(fecha);
        } else {
            //Leemos los formateamos la fecha y la hora
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calendarView.getDate());
            String fecha = calendar.get(Calendar.YEAR) + "-";
            if ((calendar.get(Calendar.MONTH) + 1) < 10) {
                fecha += 0;
            }
            fecha += (calendar.get(Calendar.MONTH) + 1) + "-";
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                fecha += 0;
            }
            fecha += calendar.get(Calendar.DAY_OF_MONTH);
            String horaOriginal = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo") + 6);
            String horaTramo = horaOriginal.toString();
            LocalDateTime localTime;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                //Calculamos el siguiente tramo
                localTime = LocalDateTime.parse(fecha + " " + horaOriginal, dtf);
                localTime = localTime.plusHours(incremento.getHour());
                localTime = localTime.plusMinutes(incremento.getMinute());
                if (localTime.isBefore(hora_fin_m) || localTime.isEqual(hora_fin_m)) {
                    if (localTime.until(hora_fin_m, ChronoUnit.MINUTES) < (incremento.getHour() * 60 + incremento.getMinute())) {
                        localTime = localTime.withHour(hora_inicio_t.getHour());
                        localTime = localTime.withMinute(hora_inicio_t.getMinute());
                    }
                } else if (localTime.isBefore(hora_inicio_t) && localTime.isAfter(hora_fin_m)) {
                    localTime = localTime.withHour(hora_inicio_t.getHour());
                    localTime = localTime.withMinute(hora_inicio_t.getMinute());
                } else if (localTime.isAfter(hora_fin_t) || localTime.isEqual(hora_fin_t)) {
                    if (localTime.until(hora_fin_t, ChronoUnit.MINUTES) < (incremento.getHour() * 60 + incremento.getMinute())) {
                        localTime = LocalDateTime.parse(fecha + " " + horaOriginal, dtf);
                    }
                }
                //Calculamos el siguiente del siguiente , para ver si deshabilitamos el boton
                LocalDateTime nextTramo = localTime.plusHours(incremento.getHour());
                nextTramo = nextTramo.plusMinutes(incremento.getMinute());
                if (nextTramo.isAfter(hora_fin_t) || nextTramo.until(hora_fin_t, ChronoUnit.MINUTES) < (incremento.getHour() * 60 + incremento.getMinute())) {
                    btSiguienteDia.setEnabled(false);
                    btSiguienteDia.setBackgroundColor(Color.GRAY);
                }
                if (nextTramo.isAfter(hora_inicio_m)) {
                    btAnteriorDia.setEnabled(true);
                    btAnteriorDia.setBackgroundColor(Color.rgb(109, 34, 109));
                }
                horaTramo = localTime.toLocalTime().toString();
            }
            //Vemos las reservas con la hora y fecha
            JSONArray jsonArray = verReservas(fecha, horaTramo);
            ArrayList<Reserva> lista = new ArrayList<>();
            if (jsonArray != null) {
                //Recorremos el JSONArray y vamos guardando
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
            rvOcupacion.setAdapter(new ReservaAdapter(lista, getActivity().getSupportFragmentManager(), this, rvOcupacion));
            tvReservasDiaHora.setText(fecha + " Tramo " + horaTramo);

        }
    }

    public void anteriorDia(CalendarView calendarView) {

        /*
         * Vemos la clase del adapter
         * ReservasFechaAdapter-> Estamos en los días , hay que pasar al día anterior
         *ReservasAdapter-> Estamos en los tramos , hay que pasar al tramo anterior */
        String clase = rvOcupacion.getAdapter().getClass().toString();
        if (clase.equals("class com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter")) {
            //Leemos la fecha y la formateamos
            calendarView.setDate(calendarView.getDate() - 86400000);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calendarView.getDate());
            tvReservasDiaHora.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
            String fecha = calendar.get(Calendar.YEAR) + "-";
            if ((calendar.get(Calendar.MONTH) + 1) < 10) {
                fecha += 0;
            }
            fecha += (calendar.get(Calendar.MONTH) + 1) + "-";
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                fecha += 0;
            }
            fecha += calendar.get(Calendar.DAY_OF_MONTH);
            cargarOcupacion(fecha);
        } else {
            //Leemos la fecha y la formateamos
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calendarView.getDate());
            String fecha = calendar.get(Calendar.YEAR) + "-";
            if ((calendar.get(Calendar.MONTH) + 1) < 10) {
                fecha += 0;
            }
            fecha += (calendar.get(Calendar.MONTH) + 1) + "-";
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                fecha += 0;
            }
            fecha += calendar.get(Calendar.DAY_OF_MONTH);
            String horaOriginal = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo") + 6);
            String horaTramo = horaOriginal.toString();
            LocalDateTime localTime;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                localTime = LocalDateTime.parse(fecha + " " + horaOriginal, dtf);
                localTime = localTime.minusHours(incremento.getHour());
                localTime = localTime.minusMinutes(incremento.getMinute());
                if (localTime.isBefore(hora_inicio_t) && localTime.isAfter(hora_fin_m)) {
                    localTime = localTime.withHour(hora_inicio_m.getHour());
                    localTime = localTime.withMinute(hora_inicio_m.getMinute());
                    while (localTime.until(hora_fin_m, ChronoUnit.MINUTES) < (incremento.getHour() * 60 + incremento.getMinute())) {
                        localTime.plusMinutes(incremento.getMinute());
                        localTime.plusHours(incremento.getHour());
                    }

                }
                LocalDateTime nextTramo = localTime.minusHours(incremento.getHour());
                nextTramo = nextTramo.minusMinutes(incremento.getMinute());
                if (nextTramo.isBefore(hora_inicio_m)) {
                    btAnteriorDia.setEnabled(false);
                    btAnteriorDia.setBackgroundColor(Color.GRAY);
                }
                if (nextTramo.isBefore(hora_fin_t)) {
                    btSiguienteDia.setEnabled(true);
                    btSiguienteDia.setBackgroundColor(Color.rgb(109, 34, 109));
                }
                horaTramo = localTime.toLocalTime().toString();
            }
            JSONArray jsonArray = verReservas(fecha, horaTramo);
            ArrayList<Reserva> lista = new ArrayList<>();
            //Vamos recorriendo el array si no es null
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
            rvOcupacion.setAdapter(new ReservaAdapter(lista, getActivity().getSupportFragmentManager(), this, rvOcupacion));
            tvReservasDiaHora.setText(fecha + " Tramo " + horaTramo);

        }

    }

    public void layoutConCalendario() {
        //Le damos tamaño al calendario
        ViewGroup.LayoutParams layoutParams = calendarView.getLayoutParams();
        layoutParams.height = (int) (this.getView().getHeight() * 0.9);
        calendarView.setLayoutParams(layoutParams);
        //Vaciamos el RecyclerView
        String clase = rvOcupacion.getAdapter().getClass().getName();
        if (clase.equals("com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter")) {
            ((ReservaAdapter) rvOcupacion.getAdapter()).setDataList(new ArrayList<>());
        } else if (clase.equals("com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter")) {
            ((ReservasFechaAdapter) rvOcupacion.getAdapter()).setDataList(new ArrayList<>());
        }
        rvOcupacion.getAdapter().notifyDataSetChanged();
        comprobarBotones();
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
                        //Escribimos el json
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
            //Arrancamos el hilo y lo sincronizamos para que la app espere la respueta
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray[0];
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
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Accept", "application/json");
                        //Escribimos el json
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonFecha = "{\n"
                                + "    \"id\":\"#PARAMID#\"\n"
                                + "}";
                        jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(((MainActivity) getActivity()).getIdRestaurante()));
                        osw.write(jsonFecha);
                        osw.flush();
                        int responseCode = connection.getResponseCode();
//Ver si la respuesta es correctadai
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
            //Arrancamos el hilo y lo sincronizamos para que la app espere la respuesta
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incremento[0];
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
                            //Vemos de que día hay que seleccionar los topes
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
                        //Cargamos el json
                        String jsonRequest = "{\n" +
                                "  \"dia\":\"#PARAMDIA#\",\n" +
                                "\"id\":\"#PARAMID#\"\n" +
                                "}";
                        jsonRequest = jsonRequest.replace("#PARAMDIA#", dia);
                        jsonRequest = jsonRequest.replace("#PARAMID#", String.valueOf(((MainActivity) getActivity()).getIdRestaurante()));

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
            //Arrancamos el hilo y lo sincronizamos para que la app espere la respuesta
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String leerTramos(String fecha) {

        LocalDate fechaDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fechaDate = LocalDate.parse(fecha);
        }
        final JSONArray[] horario = {new JSONArray()};
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/gethorario");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    //Escribimos el json
                    OutputStream os = connection.getOutputStream();
                    System.out.println("TETica");
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    String jsonFecha = "{\n"
                            + "    \"id\":\"#PARAMID#\"\n"
                            + "}";
                    jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(((MainActivity) getActivity()).getIdRestaurante()));
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
        //Arrancamos el hilo y lo sincronizamos para que la app espere la respuesta
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (horario[0].length() == 0) {
            return null;
        }
        int dia = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dia = fechaDate.getDayOfWeek().getValue();
        }
        //Leemos el json del dia correspondiente
        JSONObject jsonObject;
        try {
            jsonObject = (horario[0].getJSONObject(dia - 1));
            if (jsonObject.getInt("cerrado") == 1) {
                return "Cerrado";
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        incremento = leerIncremento();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (incremento.getMinute() == 0 && incremento.getHour() == 0) {
                return "INCREMENTO";
            }
        }
        LocalDateTime inicio_m;
        LocalDateTime fin_m;
        LocalDateTime inicio_t;
        LocalDateTime fin_t;
        LocalDateTime[] tramos;
        DateTimeFormatter dateTimeFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            try {
                //Cargamos los topes
                inicio_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_m"), dateTimeFormatter);
                fin_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_m"), dateTimeFormatter);
                inicio_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_t"), dateTimeFormatter);
                fin_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_t"), dateTimeFormatter);
                //Calculamos las cantidades de tramos
                Long tramos_m = inicio_m.until(fin_m, ChronoUnit.MINUTES) / (incremento.getHour() * 60 + incremento.getMinute());
                Long tramos_t = inicio_t.until(fin_t, ChronoUnit.MINUTES) / (incremento.getHour() * 60 + incremento.getMinute());
                tramos = new LocalDateTime[(int) (tramos_m + tramos_t) + 1];
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            int contador = 0;
            LocalDateTime tramo = inicio_m;
            while (fin_m.isAfter(tramo)) {
                tramos[contador] = tramo;
                contador++;
                //Calculamos el tramo con el incremento
                tramo = tramo.plusHours(incremento.getHour());
                tramo = tramo.plusMinutes(incremento.getMinute());
                if (fin_m.isBefore(tramo) || tramo.until(fin_m, ChronoUnit.MINUTES) < (incremento.getHour() * 60 + incremento.getMinute())) {
                    break;
                }
            }
            tramo = inicio_t;
            while (fin_t.isAfter(tramo)) {
                tramos[contador] = tramo;
                contador++;
                //Calculamos el tramo con el incremento
                tramo = tramo.plusHours(incremento.getHour());
                tramo = tramo.plusMinutes(incremento.getMinute());
                if (fin_t.isBefore(tramo)) {
                    break;
                }
            }
            //Preparamos la consulta
            String texto = "SELECT '#PARAM1#' AS value ";
            for (LocalDateTime t : tramos) {
                if (t != null) {
                    if (texto.contains("#PARAM1#")) {
                        texto = texto.replace("#PARAM1#", t.toLocalTime().toString());
                    } else {
                        texto += " UNION SELECT '" + t.toLocalTime().toString() + "'";
                    }
                }
            }
            if (texto.contains("#PARAM1#")) {
                return null;
            }
            return texto;
        }
        return "MONDONGO";
    }

    public void avisarBorradoRecyclerView(int position) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rvOcupacion.getAdapter().notifyItemRemoved(position);
            }
        });
    }

    public String[] leerSalones() {
        final JSONArray[] jsonArray = new JSONArray[1];
        final ReservasFragmentFechas reservasFragmentFechas = this;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/getsalones");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    //Escribimos el json
                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    String json = "{\n"
                            + "    \"id\":\"#ID#\"\n"
                            + "}";
                    json = json.replace("#ID#", String.valueOf(((MainActivity) reservasFragmentFechas.getActivity()).getIdRestaurante()));
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
                        if (new JSONObject(String.valueOf(response)).getInt("codigo") == 1) {
                            jsonArray[0] = new JSONObject(String.valueOf(response)).getJSONArray("aforo");
                            System.out.println(jsonArray[0]);
                        }
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
        //Arrancamos el hilo y lo sincronizamos
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (jsonArray[0] == null) {
            return null;
        } else {
            //Cargamos los filtros
            String[] textos = new String[jsonArray[0].length() + 1];
            textos[0] = "--- Seleccione filtro ---";
            for (int i = 0; i < jsonArray[0].length(); i++) {
                try {
                    JSONObject jsonObject = (JSONObject) jsonArray[0].get(i);
                    System.out.println(jsonObject);
                    textos[i + 1] = String.format("%s - %s ", jsonObject.getString("id_salon"), jsonObject.getString("nombre"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
            return textos;
        }
    }

    public RecyclerView getRvOcupacion() {
        return rvOcupacion;
    }

    public void cambioSpinner() {
        //Depende del seleccionado , filtramos o restauramos
        String opcion = spinnerFiltro.getSelectedItem().toString();
        ReservaAdapter reservaAdapter = (ReservaAdapter) rvOcupacion.getAdapter();
        if (opcion.equals("--- Seleccione filtro ---")) {
            reservaAdapter.restaurarDatos();
        } else {
            reservaAdapter.filtrarId(Integer.valueOf(opcion.substring(0, opcion.indexOf("-") - 1)));
        }
    }

    public void comprobarBotones() {
        boolean adapterNull = rvOcupacion.getAdapter() == null;
        boolean adapterCargado = false;
        boolean enReservas = false;
        String clase;
        if (!adapterNull) {
            clase = rvOcupacion.getAdapter().getClass().getName();
            adapterCargado = calendarView.getLayoutParams().height == 1;
            enReservas = clase.equals("com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter");
        }
        btAnteriorDia.setEnabled(adapterCargado);
        btSiguienteDia.setEnabled(adapterCargado);
        btLayoutCalendario.setEnabled(adapterCargado);
        btnReservar.setEnabled(enReservas);
        spinnerFiltro.setEnabled(enReservas);
        if (enReservas) {
            btnReservar.setBackgroundColor(Color.rgb(109, 34, 109));
        } else {
            btnReservar.setBackgroundColor(Color.GRAY);
        }
        if (adapterCargado) {
            btLayoutCalendario.setBackgroundColor(Color.rgb(109, 34, 109));
            btSiguienteDia.setBackgroundColor(Color.rgb(109, 34, 109));
            btAnteriorDia.setBackgroundColor(Color.rgb(109, 34, 109));
        } else {
            btLayoutCalendario.setBackgroundColor(Color.GRAY);
            btSiguienteDia.setBackgroundColor(Color.GRAY);
            btAnteriorDia.setBackgroundColor(Color.GRAY);
        }
    }
}