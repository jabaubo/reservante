package com.jabaubo.proyecto_reservas.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.time.temporal.ChronoUnit;
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

        if (((MainActivity) getActivity()).getIdRestaurante() == 0) {
            mainActivity = (MainActivity) this.getActivity();
            bindingLogin = FragmentLoginBinding.inflate(inflater, container, false);
            root[0] = bindingLogin.getRoot();
            Button b = root[0].findViewById(R.id.btLogin);
            EditText etPassword = root[0].findViewById(R.id.etLoginPassword);
            EditText etUser = root[0].findViewById(R.id.etLoginUser);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (intentarLogin(etUser.getText().toString(), etPassword.getText().toString())) {
                        login = true;
                        ViewGroup viewGroup = (ViewGroup) root[0];
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

                        root[0] = homeFragment.getLayoutInflater().inflate(R.layout.fragment_home, viewGroup);

                        MainActivity mainActivity = (MainActivity) homeFragment.getActivity();
                        mainActivity.setLogin(true);
                        mainActivity.controlMenuLateral();

                        binding = FragmentHomeBinding.inflate(inflater, container, false);
                        rvOcupacion = root[0].findViewById(R.id.rvOcupacionInicio);
                        btAnterior = root[0].findViewById(R.id.btAnteriorInicio);
                        btSiguiente = root[0].findViewById(R.id.btSiguienteInicio);
                        btReservar = root[0].findViewById(R.id.btReservarInicio);
                        btVolverInicio = root[0].findViewById(R.id.btVolverInicio);
                        tvReservasDiaHora = root[0].findViewById(R.id.tvReservasDiaHoraInicio);
                        spinnerFiltro = root[0].findViewById(R.id.spinFiltroInicio);
                        System.out.println("NULO: " + rvOcupacion.getAdapter() == null);

                        String[] salones = leerSalones();
                        if (salones != null) {
                            ArrayAdapter<String> listaSalones = new ArrayAdapter<>(homeFragment.getContext(), android.R.layout.simple_spinner_dropdown_item, salones);
                            spinnerFiltro.setAdapter(listaSalones);
                        } else {
                            android.app.AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                    .setTitle("Advertencia")
                                    .setMessage("No hay salones")

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
                        btReservar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                reservar();
                            }
                        });
                        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                System.out.println(rvOcupacion.getAdapter() == null);
                                if (rvOcupacion.getAdapter() != null) {
                                    if (rvOcupacion.getAdapter().getClass().toString().equals("class com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter")) {
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

                    }
                }
            });

        } else {
            root[0] = homeFragment.getLayoutInflater().inflate(R.layout.fragment_home, viewGroup);

            MainActivity mainActivity = (MainActivity) homeFragment.getActivity();
            mainActivity.setLogin(true);
            mainActivity.controlMenuLateral();

            binding = FragmentHomeBinding.inflate(inflater, container, false);
            rvOcupacion = root[0].findViewById(R.id.rvOcupacionInicio);
            btAnterior = root[0].findViewById(R.id.btAnteriorInicio);
            btSiguiente = root[0].findViewById(R.id.btSiguienteInicio);
            btReservar = root[0].findViewById(R.id.btReservarInicio);
            btVolverInicio = root[0].findViewById(R.id.btVolverInicio);
            tvReservasDiaHora = root[0].findViewById(R.id.tvReservasDiaHoraInicio);
            spinnerFiltro = root[0].findViewById(R.id.spinFiltroInicio);
            System.out.println("NULO: " + rvOcupacion.getAdapter() == null);

            String[] salones = leerSalones();
            if (salones != null) {
                ArrayAdapter<String> listaSalones = new ArrayAdapter<>(homeFragment.getContext(), android.R.layout.simple_spinner_dropdown_item, salones);
                spinnerFiltro.setAdapter(listaSalones);
            } else {
                android.app.AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Advertencia")
                        .setMessage("No hay salones")

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
            btReservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reservar();
                }
            });
            spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println(rvOcupacion.getAdapter() == null);
                    if (rvOcupacion.getAdapter() != null) {
                        if (rvOcupacion.getAdapter().getClass().toString().equals("class com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter")) {
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

        }
        return root[0];
    }

    public boolean intentarLogin(String usuario, String pass) {
        int respuesta[] = new int[2];
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
                            System.out.println(response);
                            reader.close();
                            System.out.println(response);
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
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (respuesta[0] == 1) {
            System.out.println("Login correcto " + respuesta[1]);
            ((MainActivity) this.getActivity()).setIdRestaurante(respuesta[1]);
            return true;
        }
        intentosLogin++;
        if (intentosLogin == 3) {
            System.exit(20);
        }
        return false;
    }

    public void reservar() {

        String fecha = tvReservasDiaHora.getText().toString().substring(0, tvReservasDiaHora.getText().toString().indexOf("Tramo") + 6);
        String hora = tvReservasDiaHora.getText().toString().substring(tvReservasDiaHora.getText().toString().indexOf("Tramo") + 6);
        ReservaDialog reservaDialog = new ReservaDialog(getView(), hora, fecha, this);
        reservaDialog.show(getActivity().getSupportFragmentManager(), "A");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*
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
            rvOcupacion.setLayoutManager(new LinearLayoutManager(this.getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public void cargarOcupacion(String fecha) {
        //DefaultListModel<Ocupacion> modelo = new DefaultListModel<>();
        JSONObject[] json = new JSONObject[1];
        try {
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
                        OutputStream os = connection.getOutputStream();
                        System.out.println("TETica");
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String consulta = leerTramos(fecha);
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
                            System.out.println("RESPUESTA api " + response);
                            json[0] = new JSONObject(response.toString());
                            System.out.println("JSON en ARRAY hilo: " + json[0]);
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
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
            if (json[0].toString().contains("restaurante cerrado")) {
                //jListOcupacionReservas.setModel(modelo);
                //JOptionPane.showMessageDialog(panelCalendario, "Ese día no está abierto el restaurante", "Aviso", JOptionPane.PLAIN_MESSAGE);
            } else if (json[0].toString().contains("restaurante de vacaciones")) {
                //jListOcupacionReservas.setModel(modelo);
                //JOptionPane.showMessageDialog(panelCalendario, "Ese día está dentro de un periódo vacaciconal", "Aviso", JOptionPane.PLAIN_MESSAGE);
            } else {
                int codRespuesta = json[0].getInt("codigo");
                if (codRespuesta == 1) {
                    System.out.println("RESPUESTA CARGA: " + json[0]);
                    JSONArray jsonArray = json[0].getJSONArray("reservas");
                    int n_salones = jsonArray.getJSONObject(0).getInt("n_salones");
                    System.out.println("tope :" + n_salones);
                    System.out.println("JSON ARRAY: " + jsonArray);
                    System.out.println("JSON ARRAY LENGTH: " + jsonArray.length());

                    //while ()
                    ArrayList<ReservaFechas> lista = new ArrayList<>();
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
                            System.out.println("pre " + ocupacion);
                            if (ratio < 0.33f) {
                                ocupacion += String.format("%s <font color='#008000'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                            } else if (ratio < 0.66f) {
                                ocupacion += String.format("%s <font color='#FFEB00'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                            } else {
                                ocupacion += String.format("%s <font color='#8B0000'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);

                            }
                            System.out.println("post " + ocupacion);
                        }
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
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (jsonArray[0] == null) {
            System.out.println("No hay salones");
            return null;
        } else {
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
            for (String s : textos) {
                System.out.println(s);
            }
            return textos;
        }
    }

    /*
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
*/
    public void cambioSpinner() {
        String opcion = spinnerFiltro.getSelectedItem().toString();
        ReservaAdapter reservaAdapter = (ReservaAdapter) rvOcupacion.getAdapter();
        if (opcion.equals("--- Seleccione filtro ---")) {
            reservaAdapter.restaurarDatos();
        } else {
            reservaAdapter.filtrarId(Integer.valueOf(opcion.substring(0, opcion.indexOf("-") - 1)));
        }
    }
/*
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
                if (contador < tramos.length){
                    tramos[contador] = tramo;
                }
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
                if (texto.contains("'#PARAM1#'") ) {
                    texto = texto.replace("#PARAM1#", t.toLocalTime().toString());
                } else {
                    if (t != null){
                        texto += " UNION SELECT '" + t.toLocalTime().toString() + "'";
                    }
                }
            }
            System.out.println(texto);
            return texto;

        }
        return null;
    }
*/

    public String leerTramos(String fecha) {
        LocalDate fechaDate = null;
        String texto = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fechaDate = LocalDate.parse(fecha);
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
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonFecha = "{\n"
                                + "    \"id\":\"#PARAMID#\"\n"
                                + "}";
                        jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(mainActivity.getIdRestaurante()));
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
            if (horario[0].length() > 0) {
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
                    System.out.println(inicio_m == null);
                    fin_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_m"), dateTimeFormatter);
                    System.out.println(fin_m == null);
                    inicio_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_t"), dateTimeFormatter);
                    System.out.println(inicio_t == null);
                    fin_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_t"), dateTimeFormatter);
                    System.out.println(fin_t == null);
                    Long tramos_m = inicio_m.until(fin_m, ChronoUnit.MINUTES) / (incremento.getHour() * 60 + incremento.getMinute());
                    System.out.println(tramos_m == null);
                    Long tramos_t = inicio_t.until(fin_t, ChronoUnit.MINUTES) / (incremento.getHour() * 60 + incremento.getMinute());
                    System.out.println(tramos_t == null);
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
                texto = "SELECT '#PARAM1#' AS value ";
                for (LocalDateTime t : tramos) {
                    if (t != null) {
                        if (texto.contains("'#PARAM1#'")) {
                            texto = texto.replace("#PARAM1#", t.toLocalTime().toString());
                        } else {
                            texto += " UNION SELECT '" + t.toLocalTime().toString() + "'";
                        }
                    }
                }
                System.out.println(texto);
            }
            return texto;
        }
        return "Error";
    }

    /*
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
*/
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
                        OutputStream os = connection.getOutputStream();
                        System.out.println("TETica");
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
            ArrayList<Reserva> lista = verReservas(fecha, horaTramo);
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

    /*
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
    */
    public ArrayList<Reserva> verReservas(String fecha, String hora) {
        final JSONArray[] jsonArray = new JSONArray[1];
        ArrayList<Reserva> lista = new ArrayList<>();
        try {
            System.out.println("Pa dentro");
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getreservahora");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonRequest = "{\"fecha\": \"#PARAMFECHA#\",\"hora\":\"#PARAMHORA#\",\"id\":\"#PARAMID#\"}";
                        jsonRequest = jsonRequest.replace("#PARAMFECHA#", fecha);
                        jsonRequest = jsonRequest.replace("#PARAMHORA#", hora);
                        jsonRequest = jsonRequest.replace("#PARAMID#", String.valueOf(mainActivity.getIdRestaurante()));
                        System.out.println("jsonRequest " + jsonRequest);
                        osw.write(jsonRequest);
                        osw.flush();
                        int responseCode = connection.getResponseCode();
                        System.out.println((responseCode == HttpURLConnection.HTTP_OK) + " " + responseCode);
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
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
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

    @Override
    public void onResume() {
        super.onResume();
    }
}