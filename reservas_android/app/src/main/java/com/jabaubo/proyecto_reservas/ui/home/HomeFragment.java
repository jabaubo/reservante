package com.jabaubo.proyecto_reservas.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.MainActivity;
import com.jabaubo.proyecto_reservas.Objetos.Reserva;
import com.jabaubo.proyecto_reservas.Objetos.Ocupacion;

import com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter;
import com.jabaubo.proyecto_reservas.Objetos.ReservaFechas;
import com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentHomeBinding;
import com.jabaubo.proyecto_reservas.databinding.FragmentLoginBinding;
import com.jabaubo.proyecto_reservas.ui.ReservaDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment {
    private int intentosLogin = 0;
    private MainActivity mainActivity;
    private FragmentHomeBinding binding;
    private FragmentLoginBinding bindingLogin;
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
    public Boolean login = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View[] root = new View[1];
        HomeFragment homeFragment = this;
        ViewGroup viewGroup = (ViewGroup) root[0];
        /*
         * Comprobamos el ID del restaurante del main activity
         * 0->No ha iniciado sesión
         * Otro ->Ha iniciado sesión*/
        if (((MainActivity) getActivity()).getIdRestaurante() == 0) {
            //Inciamos con el layout de login
            mainActivity = (MainActivity) this.getActivity();
            bindingLogin = FragmentLoginBinding.inflate(inflater, container, false);
            root[0] = bindingLogin.getRoot();
            Button b = root[0].findViewById(R.id.btLogin);
            EditText etPassword = root[0].findViewById(R.id.etLoginPassword);
            EditText etUser = root[0].findViewById(R.id.etLoginUser);
            TextView tvUser = root[0].findViewById(R.id.tvUsuarioLogin);
            TextView tvPassword = root[0].findViewById(R.id.tvPasswordLogin);
            ImageView ivLogin = root[0].findViewById(R.id.ivLogo);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /* Comprobamos el login para usuario y contraseña
                     * correcto-> Ponemos el formato de login
                     * Incorrecto -> Avisamos , sumamos 1 a los intentos*/

                    if (intentarLogin(etUser.getText().toString(), etPassword.getText().toString())) {
                        login = true;
                        ViewGroup viewGroup = (ViewGroup) root[0];
                        //Desactivamos el  layout de login
                        viewGroup.removeView(root[0]);

                        b.setAlpha(0);
                        b.setEnabled(false);
                        b.setX(0);
                        b.setY(0);

                        etPassword.setAlpha(0);
                        etPassword.setEnabled(false);
                        etPassword.setX(0);
                        etPassword.setY(0);

                        etUser.setAlpha(0);
                        etUser.setEnabled(false);
                        etUser.setX(0);
                        etUser.setY(0);

                        tvUser.setAlpha(0);
                        tvUser.setEnabled(false);
                        tvUser.setX(0);
                        tvUser.setY(0);

                        tvPassword.setAlpha(0);
                        tvPassword.setEnabled(false);
                        tvPassword.setX(0);
                        tvPassword.setY(0);

                        ivLogin.setAlpha(0f);
                        ivLogin.setEnabled(false);
                        ivLogin.setX(0);
                        ivLogin.setY(0);
                        //PReparamos el layout de Inicio
                        root[0] = homeFragment.getLayoutInflater().inflate(R.layout.fragment_home, viewGroup);
                        //Activamos menú lateral
                        MainActivity mainActivity = (MainActivity) homeFragment.getActivity();
                        mainActivity.setLogin(true);
                        mainActivity.controlMenuLateral();
                        //Cargamos componentes
                        binding = FragmentHomeBinding.inflate(inflater, container, false);
                        rvOcupacion = root[0].findViewById(R.id.rvOcupacionInicio);
                        btAnterior = root[0].findViewById(R.id.btAnteriorInicio);
                        btSiguiente = root[0].findViewById(R.id.btSiguienteInicio);
                        btReservar = root[0].findViewById(R.id.btReservarInicio);
                        btVolverInicio = root[0].findViewById(R.id.btVolverInicio);
                        tvReservasDiaHora = root[0].findViewById(R.id.tvReservasDiaHoraInicio);
                        spinnerFiltro = root[0].findViewById(R.id.spinFiltroInicio);
                        System.out.println("NULO: " + rvOcupacion.getAdapter() == null);
                        incremento = leerIncremento();

                        String[] salones = leerSalones();
                        /*
                         * Comprobamos si ha devuelto correctamente lo salones
                         * Hay salones -> Cargamos en el filtro
                         * No hay -> Avisamos*/
                        if (salones != null) {
                            ArrayAdapter<String> listaSalones = new ArrayAdapter<>(homeFragment.getContext(), android.R.layout.simple_spinner_dropdown_item, salones);
                            spinnerFiltro.setAdapter(listaSalones);
                        } else {
                            android.app.AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                    .setTitle("Advertencia")
                                    .setMessage("No hay salones,\nconfigure alguno antes de reservar.")
                                    .setPositiveButton(android.R.string.yes, null)
                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert).create();
                            alertDialog.show();
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            tvReservasDiaHora.setText(LocalDate.now().toString());
                        }
                        //Cargamos los listeners
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
                        btReservar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                reservar();
                            }
                        });
                        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                //Comprobamos que el adapter no es null
                                if (rvOcupacion.getAdapter() != null) {
                                    //Comprobamos que es de la clase correcta
                                    if (rvOcupacion.getAdapter().getClass().toString().equals("class com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter")) {
                                        //Filtramos
                                        cambioSpinner();
                                    }

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            cargarOcupacion(LocalDate.now().toString());
                        }
                        comprobarBotones();
                    } else {
                        android.app.AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle("Error")
                                .setMessage("Usuario o contraseña incorrectos")
                                .setPositiveButton(android.R.string.yes, null)
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert).create();
                        alertDialog.show();

                    }
                }
            });

        } else {
            //Cargamos el layout de inicio
            root[0] = homeFragment.getLayoutInflater().inflate(R.layout.fragment_home, viewGroup);

            MainActivity mainActivity = (MainActivity) homeFragment.getActivity();
            mainActivity.setLogin(true);
            mainActivity.controlMenuLateral();
            //Cargamos los componentes
            binding = FragmentHomeBinding.inflate(inflater, container, false);
            rvOcupacion = root[0].findViewById(R.id.rvOcupacionInicio);
            btAnterior = root[0].findViewById(R.id.btAnteriorInicio);
            btSiguiente = root[0].findViewById(R.id.btSiguienteInicio);
            btReservar = root[0].findViewById(R.id.btReservarInicio);
            btVolverInicio = root[0].findViewById(R.id.btVolverInicio);
            tvReservasDiaHora = root[0].findViewById(R.id.tvReservasDiaHoraInicio);
            spinnerFiltro = root[0].findViewById(R.id.spinFiltroInicio);
            /*
             * Leemos lo salones
             * Hay -> Cargamos el filtro
             * No hay -> Avisamos*/
            String[] salones = leerSalones();
            if (salones != null) {
                ArrayAdapter<String> listaSalones = new ArrayAdapter<>(homeFragment.getContext(), android.R.layout.simple_spinner_dropdown_item, salones);
                spinnerFiltro.setAdapter(listaSalones);
            } else {
                android.app.AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Advertencia")
                        .setMessage("No hay salones")
                        .setPositiveButton(android.R.string.yes, null)
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                alertDialog.show();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tvReservasDiaHora.setText(LocalDate.now().toString());
            }
            //Cargamos los listeneres
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
            btReservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reservar();
                }
            });
            spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Comprobamos que el adapter no sea nulo
                    if (rvOcupacion.getAdapter() != null) {
                        //Comprobamos que el adapter es de la clase correcta
                        if (rvOcupacion.getAdapter().getClass().toString().equals("class com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter")) {
                            //Filtramos
                            cambioSpinner();
                        }

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cargarOcupacion(LocalDate.now().toString());
            }
            comprobarBotones();
        }
        return root[0];
    }

    public boolean intentarLogin(String usuario, String pass) {
        int respuesta[] = new int[2];
        //Preparamos la petición
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/login");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Accept", "application/json");
                        //Preparamos y cargamos el json
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonStr = "{\n"
                                + "    \"usuario\":\"#PARAMUSUARIO#\",\n"
                                + "    \"password\":\"#PARAMPASSWORD#\"\n"
                                + "}";
                        jsonStr = jsonStr.replace("#PARAMUSUARIO#", usuario);
                        jsonStr = jsonStr.replace("#PARAMPASSWORD#", pass);
                        osw.write(jsonStr);
                        osw.flush();
                        int responseCode = connection.getResponseCode();
                        //Ver si la respuesta es correcta
                        System.out.println(responseCode == HttpURLConnection.HTTP_OK);
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            // Si es correcta la leemos
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String line;
                            StringBuilder response = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            reader.close();
                            respuesta[0] = new JSONObject(response.toString()).getInt("codigo");
                            if (respuesta[0] == 1) {
                                respuesta[1] = new JSONObject(response.toString()).getJSONArray("restaurante").getJSONObject(0).getInt("id_restaurante");

                            }

                        }
                        connection.disconnect();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (ProtocolException e) {
                        System.out.println(e.getMessage());
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            };
            //Arrancamos el hilo , y lo sincronizamos para que la app espere la respuesta
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Si la respuesta es correcta , pasamos el idRestaurante al MainActivity
        if (respuesta[0] == 1) {
            ((MainActivity) this.getActivity()).setIdRestaurante(respuesta[1]);
            return true;
        }
        //Si ya ha hecho 3 intentos incorrectos , cerramos el programa
        if (intentosLogin == 3) {
            System.exit(20);
        }
        return false;
    }

    public void reservar() {
        //Leemos los datos del TextView
        String fecha = tvReservasDiaHora.getText().toString().substring(0, tvReservasDiaHora.getText().toString().indexOf("Tramo") + 6);
        String hora = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo") + 6);
        //Se lo pasamos al dialog
        ReservaDialog reservaDialog = new ReservaDialog(getView(), hora, fecha, this);
        reservaDialog.show(getActivity().getSupportFragmentManager(), "A");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void cargarOcupacion(String fecha) {
        //Preparamos el json
        JSONObject[] json = new JSONObject[1];
        try {
            String consulta = leerTramos(fecha);
            /*
             * Comprobamos la consulta
             * Nulo -> Error , no cargamos reservas
             * Otra cosa -> Continuamos*/
            if (consulta == null) {
                //Vemos si hay adapter
                if (rvOcupacion.getAdapter() != null) {
                    //Depende de la clase le pasamos como lista un Array vacío
                    String clase = rvOcupacion.getAdapter().getClass().getName();
                    if (clase.equals("com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter")) {
                        ((ReservaAdapter) rvOcupacion.getAdapter()).setDataList(new ArrayList<>());
                        rvOcupacion.getAdapter().notifyDataSetChanged();
                        System.out.println();
                    } else if (clase.equals("com.jabaubo.proyecto_reservas.Objetos.ReservasFechaAdapter")) {
                        ((ReservasFechaAdapter) rvOcupacion.getAdapter()).setDataList(new ArrayList<>());
                        rvOcupacion.getAdapter().notifyDataSetChanged();
                    }
                }
                //Avisamos
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Error")
                        .setMessage("Revise el horario del día (Horario) y la duración de las reservas (Configuración)")
                        .setPositiveButton(android.R.string.yes, null)
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                alertDialog.show();
            } else {
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
                            jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(mainActivity.getIdRestaurante()));
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
                                json[0] = new JSONObject(response.toString());
                            }
                            connection.disconnect();
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        } catch (ProtocolException e) {
                            throw new RuntimeException(e);
                        } catch (JSONException e) {
                            System.exit(2030);
                            throw new RuntimeException(e);
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                };
                //Sincronizamos el hilo para esperar la respuesta
                Thread thread = new Thread(runnable);
                thread.start();
                thread.join();

                //Mensaje a mostrar si el restaurante está cerrado
                if (json[0].toString().contains("restaurante cerrado")) {
                    Snackbar.make(getView(), "El restaurante está cerrado", Snackbar.LENGTH_SHORT).show();
                    rvOcupacion.setAdapter(null);
                }
                //Mensaje a mostrar si el restaurante está de vacaciones
                else if (json[0].toString().contains("restaurante de vacaciones")) {
                    Snackbar.make(getView(), "El restaurante está de vacaciones", Snackbar.LENGTH_SHORT).show();
                    rvOcupacion.setAdapter(null);
                }
                //Cargamos ocupación
                else {
                    //Comprobamos el codigo de respuesta
                    int codRespuesta = json[0].getInt("codigo");
                    if (codRespuesta == 1) {
                        JSONArray jsonArray = json[0].getJSONArray("reservas");
                        int n_salones = jsonArray.getJSONObject(0).getInt("n_salones");
                        ArrayList<ReservaFechas> lista = new ArrayList<>();
                        //Recorremos el Array
                        for (int i = 0; i < jsonArray.length(); i += n_salones) {
                            int reservasTotal = 0;
                            String ocupacion = "";
                            //Recorremos el Array del array
                            for (int j = i; j < (i + n_salones); j++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                //Leemos los datos
                                String nombreSalon = jsonObject.getString("nombre");
                                String nReservas = jsonObject.getString("n_reservas");
                                reservasTotal += Integer.valueOf(nReservas);
                                String nPersonas = jsonObject.getString("n_personas");
                                String aforoSalon = jsonObject.getString("aforo");
                                //Calculamos el ratio de ocupación de salón y formateamos el texto en base al %
                                float ratio = Float.parseFloat(nPersonas) / Float.parseFloat(aforoSalon);
                                if (ratio < 0.33f) {
                                    ocupacion += String.format("%s <font color='#008000'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                                } else if (ratio < 0.66f) {
                                    ocupacion += String.format("%s <font color='#FFEB00'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                                } else {
                                    ocupacion += String.format("%s <font color='#8B0000'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                                }
                            }
                            //Creamos el objeto , rellenamos y añadimos a la lista
                            ReservaFechas o = new ReservaFechas();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                o.setHora(jsonArray.getJSONObject(i).getString("value"));
                                o.setFecha(fecha);
                            }
                            o.setnReservas(reservasTotal);
                            o.setOcupacion(ocupacion);
                            lista.add(o);
                        }
                        rvOcupacion.setAdapter(new ReservasFechaAdapter(getActivity().getSupportFragmentManager(), rvOcupacion, tvReservasDiaHora, lista, this));
                        rvOcupacion.setLayoutManager(new LinearLayoutManager(this.getContext()));
                    }
                }
            }

        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public RecyclerView getRvOcupacion() {
        return rvOcupacion;
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
                    json = json.replace("#ID#", String.valueOf(mainActivity.getIdRestaurante()));
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
                        connection.disconnect();
                        System.out.println("json leer Salones" + json);
                        System.out.println("Respuesta leer Salones" + response);
                        if (!new JSONObject(String.valueOf(response)).getString("codigo").equals("2")) {
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
                    throw new RuntimeException(e);
                }

            }
        };
        //Arrancamos el hilo y sincronizamos para que la app espere el resultado
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
            //Cargamos un array para el filtro
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

    public void cambioSpinner() {
        //Comprobamos el filtro para ver cual es el seleccionado
        String opcion = spinnerFiltro.getSelectedItem().toString();
        ReservaAdapter reservaAdapter = (ReservaAdapter) rvOcupacion.getAdapter();
        if (opcion.equals("--- Seleccione filtro ---")) {
            //Restauramos la lista completa
            reservaAdapter.restaurarDatos();
        } else {
            //Filtramos con la id del salon
            reservaAdapter.filtrarId(Integer.valueOf(opcion.substring(0, opcion.indexOf("-") - 1)));
        }
    }

    public String leerTramos(String fecha) {
        LocalDate fechaDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fechaDate = LocalDate.parse(fecha);
        }
        final JSONArray[] horario = {new JSONArray()};
        //Preparamos la petición
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
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Vemos que dia hay que leer
        int dia = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dia = fechaDate.getDayOfWeek().getValue();
        }
        JSONObject jsonObject;
        try {
            jsonObject = (horario[0].getJSONObject(dia - 1));
        } catch (JSONException e) {
            return null;
        }
        //Leemos la duración de reservas y vemos que sea un valor válido
        incremento = leerIncremento();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (incremento.getMinute() == 0 && incremento.getHour() == 0) {
                return null;
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
                //Cargamos los valores
                inicio_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_m"), dateTimeFormatter);
                fin_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_m"), dateTimeFormatter);
                inicio_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_t"), dateTimeFormatter);
                fin_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_t"), dateTimeFormatter);
                //Calculamos la cantidad de tramos matutinos
                Long tramos_m = inicio_m.until(fin_m, ChronoUnit.MINUTES) / (incremento.getHour() * 60 + incremento.getMinute());
                //Calculamos la cantidad de tramos de la tarde
                Long tramos_t = inicio_t.until(fin_t, ChronoUnit.MINUTES) / (incremento.getHour() * 60 + incremento.getMinute());
                tramos = new LocalDateTime[(int) (tramos_m + tramos_t) + 1];
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            int contador = 0;
            //Cálculo de tramos matutinos
            LocalDateTime tramo = inicio_m;
            while (fin_m.isAfter(tramo)) {
                tramos[contador] = tramo;
                contador++;
                tramo = tramo.plusHours(incremento.getHour());
                tramo = tramo.plusMinutes(incremento.getMinute());
                if (fin_m.isBefore(tramo) || tramo.until(fin_m, ChronoUnit.MINUTES) < (incremento.getHour() * 60 + incremento.getMinute())) {
                    break;
                }

            }
            //Cálculo de tramos de la tarde
            tramo = inicio_t;
            while (fin_t.isAfter(tramo)) {
                tramos[contador] = tramo;
                contador++;
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
            //Vemos si se ha preparado correctamente
            if (texto.contains("#PARAM1#")) {
                return null;
            }
            return texto;
        }
        return null;
    }


    public LocalTime leerIncremento() {
        final LocalTime[] incremento = new LocalTime[1];
        try {
            //Preparamos la petición
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
                        jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(mainActivity.getIdRestaurante()));
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
                            System.out.println(incremento[0].toString());
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
            //Arrancamos el hilo y lo sincronizamos para que la app espere al resultado de la petición
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
        /*Vemos la clase del Adapter
        * ReservasFechaAdapter -> Estamos en tramos , hay que pasar al día siguiente
        * ReservasAdapter -> Estamos en reservas , hay que pasar al tramo siguiente*/
        String clase = rvOcupacion.getAdapter().getClass().getName().toString();
        boolean enTramos = clase.equals("com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter");
        if (!enTramos) {
            String horaOriginal = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo") + 6);
            String horaTramo = horaOriginal.toString();
            LocalDateTime localTime;
            String fecha = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                fecha = LocalDate.now().toString();
                leerTopes(fecha);
                //Cálculo de la hora
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
                /*
                * Comprobamos si hay más tramos en el mismo día después de el actual
                * Si hay -> Dejar el botón del siguiente día activo , con el color de activo
                * Si no -> Deshabilitamos el botón y cambiamos el color*/
                LocalDateTime nextTramo = localTime.plusHours(incremento.getHour());
                nextTramo = nextTramo.plusMinutes(incremento.getMinute());
                if (nextTramo.isAfter(hora_fin_t) || nextTramo.until(hora_fin_t, ChronoUnit.MINUTES) < (incremento.getHour() * 60 + incremento.getMinute())) {
                    btSiguiente.setEnabled(false);
                    btSiguiente.setBackgroundColor(Color.GRAY);
                }
                if (nextTramo.isAfter(hora_inicio_m)) {
                    btAnterior.setEnabled(true);
                    btAnterior.setBackgroundColor(Color.rgb(139, 42, 139));
                }
                horaTramo = localTime.toLocalTime().toString();
            }
            //Cargamos los datos actualizados
            ArrayList<Reserva> lista = verReservas(fecha, horaTramo);
            rvOcupacion.setAdapter(new ReservaAdapter(lista, getActivity().getSupportFragmentManager(), rvOcupacion));
            tvReservasDiaHora.setText(fecha + " Tramo " + horaTramo);
        }
        else {
            Calendar calendar = Calendar.getInstance();
            String fecha = calendar.get(Calendar.YEAR) + "-";
            if ((calendar.get(Calendar.MONTH) + 1) < 10) {
                fecha += 0;
            }
            fecha += (calendar.get(Calendar.MONTH) + 1) + "-";
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                fecha += 0;
            }
            fecha += calendar.get(Calendar.DAY_OF_MONTH);
            leerTopes(fecha);
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
                    btSiguiente.setEnabled(false);
                    btSiguiente.setBackgroundColor(Color.GRAY);
                }
                if (nextTramo.isAfter(hora_inicio_m)) {
                    btAnterior.setEnabled(true);
                    btAnterior.setBackgroundColor(Color.rgb(109, 34, 109));
                }
                horaTramo = localTime.toLocalTime().toString();
            }
            //Vemos las reservas con la hora y fecha
            ArrayList<Reserva> lista = verReservas(fecha, horaTramo);
            rvOcupacion.setAdapter(new ReservaAdapter(lista, getActivity().getSupportFragmentManager(), this, rvOcupacion));
            tvReservasDiaHora.setText(fecha + " Tramo " + horaTramo);
        }
    }

    public void clickAnterior() {
        comprobarBotones();
        /*Vemos la clase del Adapter
         * ReservasFechaAdapter -> Estamos en tramos , hay que pasar al día anterior
         * ReservasAdapter -> Estamos en reservas , hay que pasar al tramo anterior*/
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
                //Cálculo de la hora
                if (localTime.isBefore(hora_inicio_t) && localTime.isAfter(hora_fin_m)) {
                    localTime = localTime.withHour(hora_inicio_m.getHour());
                    localTime = localTime.withMinute(hora_inicio_m.getMinute());
                    while (localTime.until(hora_fin_m, ChronoUnit.MINUTES) < (incremento.getHour() * 60 + incremento.getMinute())) {
                        localTime.plusMinutes(incremento.getMinute());
                        localTime.plusHours(incremento.getHour());
                    }

                }
                /*
                 * Comprobamos si hay más tramos en el mismo día después de el actual
                 * Si hay -> Dejar el botón del siguiente día activo , con el color de activo
                 * Si no -> Deshabilitamos el botón y cambiamos el color*/
                LocalDateTime nextTramo = localTime.minusHours(incremento.getHour());
                nextTramo = nextTramo.minusMinutes(incremento.getMinute());
                if (nextTramo.isBefore(hora_inicio_m)) {
                    btAnterior.setEnabled(false);
                    btAnterior.setBackgroundColor(Color.GRAY);
                }
                if (nextTramo.isBefore(hora_fin_t)) {
                    btSiguiente.setEnabled(true);
                    btSiguiente.setBackgroundColor(Color.rgb(139, 42, 139));

                }
                horaTramo = localTime.toLocalTime().toString();
            }
            //Cargamos las reservas de la hora
            ArrayList<Reserva> lista = verReservas(fecha, horaTramo);
            rvOcupacion.setAdapter(new ReservaAdapter(lista, getActivity().getSupportFragmentManager(), rvOcupacion));
            tvReservasDiaHora.setText(fecha + " Tramo " + horaTramo);
        }
    }

    public void clickVolver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            cargarOcupacion(LocalDate.now().toString());
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
                            //Hay que ver cual es el día
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
                        //Escribimos el json
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonRequest = "{\"dia\": \"#PARAMDIA#\" , \"id\": \"#PARAMID#\"}";
                        jsonRequest = jsonRequest.replace("#PARAMDIA#", dia);
                        jsonRequest = jsonRequest.replace("#PARAMID#", String.valueOf(mainActivity.getIdRestaurante()));
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
                            System.out.println(response);
                            reader.close();
                            //Cargamos los tramos
                            JSONObject jsonObject = new JSONObject(response.toString()).getJSONArray("horario").getJSONObject(0);
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                hora_inicio_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_m"), dtf);
                                System.out.println(hora_inicio_m);
                                hora_fin_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_m"), dtf);
                                System.out.println(hora_fin_m);
                                hora_inicio_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_t"), dtf);
                                System.out.println(hora_inicio_t);
                                hora_fin_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_t"), dtf);
                                System.out.println(hora_fin_t);
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
            //Arrancamos el hilo y sincronizamos para que la app espere el resultado
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Reserva> verReservas(String fecha, String hora) {
        final JSONArray[] jsonArray = new JSONArray[1];
        ArrayList<Reserva> lista = new ArrayList<>();
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getreservahora");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        //Escribimos el json
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonRequest = "{\"fecha\": \"#PARAMFECHA#\",\"hora\":\"#PARAMHORA#\",\"id\":\"#PARAMID#\"}";
                        jsonRequest = jsonRequest.replace("#PARAMFECHA#", fecha);
                        jsonRequest = jsonRequest.replace("#PARAMHORA#", hora);
                        jsonRequest = jsonRequest.replace("#PARAMID#", String.valueOf(mainActivity.getIdRestaurante()));
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
                            //Recorremos el array y vamos guardando las reservas
                            for (int i = 0; i < jsonArray[0].length(); i++) {
                                Reserva r = new Reserva();
                                JSONObject json = jsonArray[0].getJSONObject(i);
                                r.setId(json.getInt("id_reserva"));
                                r.setFecha(json.getString("fecha"));
                                r.setId_salon(json.getInt("id_salon"));
                                r.setN_personas(json.getInt("n_personas"));
                                r.setHora(json.getString("hora"));
                                r.setObservaciones(json.getString("observaciones"));
                                r.setNombre_apellidos(json.getString("nombre_apellidos"));
                                r.setTelefono(json.getString("telefono"));
                                r.setEmail(json.getString("email"));
                                lista.add(r);
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
            //Arrancamos el hilo y sincronizamos para esperar la respuesta de la petición
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void comprobarBotones() {
        //Comprobamos si el adapter es null
        boolean adapterNull = rvOcupacion.getAdapter() == null;
        boolean enReservas = false;
        if (!adapterNull) {
            //Si el adapter no es null comprobamos si estamos en la vista de reservas
            String clase = rvOcupacion.getAdapter().getClass().getName();
            enReservas = clase.equals("com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter");
        }

        btVolverInicio.setEnabled(enReservas);
        btAnterior.setEnabled(enReservas);
        btSiguiente.setEnabled(enReservas);
        btReservar.setEnabled(enReservas);
        spinnerFiltro.setEnabled(enReservas);
        if (enReservas) {
            btVolverInicio.setBackgroundColor(Color.rgb(139, 42, 139));
            btSiguiente.setBackgroundColor(Color.rgb(139, 42, 139));
            btAnterior.setBackgroundColor(Color.rgb(139, 42, 139));
            btReservar.setBackgroundColor(Color.rgb(139, 42, 139));
        } else {
            btVolverInicio.setBackgroundColor(Color.GRAY);
            btSiguiente.setBackgroundColor(Color.GRAY);
            btAnterior.setBackgroundColor(Color.GRAY);
            btReservar.setBackgroundColor(Color.GRAY);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}