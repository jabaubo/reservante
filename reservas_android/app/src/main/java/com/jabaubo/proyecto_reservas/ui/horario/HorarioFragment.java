package com.jabaubo.proyecto_reservas.ui.horario;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabaubo.proyecto_reservas.BaseDeDatos;
import com.jabaubo.proyecto_reservas.MainActivity;
import com.jabaubo.proyecto_reservas.Objetos.Horario;
import com.jabaubo.proyecto_reservas.Objetos.HorarioAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentHorarioBinding;

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
import java.util.ArrayList;


public class HorarioFragment extends Fragment {
    private FragmentHorarioBinding binding;
    private BaseDeDatos baseDeDatos;
    //ELEMENTOS
    private RecyclerView rvHorario;
    private ArrayList<Horario> listaOriginal;
    JSONArray jsonArray;
    private Button btGuardarHorario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHorarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        jsonArray = cargarHorarioAPI();
        listaOriginal = new ArrayList<>();
        ArrayList<Horario> lista = new ArrayList<>();
        if (jsonArray != null){
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String dia = jsonObject.getString("dia");
                    Boolean cerrado = jsonObject.getInt("cerrado") == 1;
                    String hora_inicio_m = jsonObject.getString("hora_inicio_m").substring(0, jsonObject.getString("hora_inicio_m").lastIndexOf(":"));
                    String hora_fin_m = jsonObject.getString("hora_fin_m").substring(0, jsonObject.getString("hora_fin_m").lastIndexOf(":"));
                    String hora_inicio_t = jsonObject.getString("hora_inicio_t").substring(0, jsonObject.getString("hora_inicio_t").lastIndexOf(":"));
                    String hora_fin_t = jsonObject.getString("hora_fin_t").substring(0, jsonObject.getString("hora_fin_t").lastIndexOf(":"));
                    Horario h = new Horario(dia, cerrado, hora_inicio_m, hora_fin_m, hora_inicio_t, hora_fin_t);
                    h.setOrden(i+1);
                    h.setId_restaurante(((MainActivity)getActivity()).getIdRestaurante());
                    listaOriginal.add(h);
                    lista.add(h.clonar());
                    System.out.println("JSON: " + jsonObject);
                    System.out.println("HORARIO JSON: " + h.jar());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        else {
            for (int i = 0; i < 7; i++) {
                    String dia = "";
                    switch (i){
                        case 0:
                            dia = "Lunes";
                            break;
                        case 1:
                            dia = "Martes";
                            break;
                        case 2:
                            dia = "Miércoles";
                            break;
                        case 3:
                            dia = "Jueves";
                            break;
                        case 4:
                            dia = "Viernes";
                            break;
                        case 5:
                            dia = "Sábado";
                            break;
                        case 6:
                            dia = "Domingo";
                            break;
                    }
                    Boolean cerrado = false;
                    String hora_inicio_m = "00:00";
                    String hora_fin_m = "00:00";
                    String hora_inicio_t = "00:00";
                    String hora_fin_t = "00:00";
                    Horario h = new Horario(dia, cerrado, hora_inicio_m, hora_fin_m, hora_inicio_t, hora_fin_t);
                    h.setOrden(i+1);
                    h.setId_restaurante(((MainActivity)getActivity()).getIdRestaurante());
                    listaOriginal.add(h);
                    lista.add(h.clonar());
            }
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Advertencia")
                    .setMessage("No hay horario configurado , configúrelo antes de reservar.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert).create();
            alertDialog.show();
        }
        rvHorario = root.findViewById(R.id.rvHorario);
        btGuardarHorario = root.findViewById(R.id.btGuardarHorario);
        btGuardarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarHorarioEnAPI();
            }
        });
        HorarioAdapter adapter = new HorarioAdapter(lista, this);
        rvHorario.setAdapter(adapter);
        rvHorario.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return root;

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
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }


    public JSONArray cargarHorarioAPI() {
        String[] responseStr = new String[1];
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
                    System.out.println("TETica");
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    String jsonFecha = "{\n"
                            + "    \"id\":\"#PARAMID#\"\n"
                            + "}";
                    jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(((MainActivity)getActivity()).getIdRestaurante()));
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
            if (new JSONObject(responseStr[0]).getInt("codigo") == 1){
                JSONArray jsonArray = new JSONObject(responseStr[0]).getJSONArray("horarios");
                return jsonArray;
            }
            return null;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void guardarHorarioEnAPI() {
        ArrayList<Horario> lista = ((HorarioAdapter) rvHorario.getAdapter()).getDatalist();
        System.out.println("#HORARIO#");
        boolean hayDiferencias = false;
        for (int i = 0; i < lista.size(); i++) {
            if (!lista.get(i).jar().equals(listaOriginal.get(i).jar())){
                hayDiferencias = true;
                break;
            }
        }
        if (hayDiferencias){
            String jsonConTodo = "[";
            for (int i = 0 ; i < lista.size() ; i++){
                jsonConTodo += lista.get(i).jar();
                if (i+1 == lista.size() ){
                    jsonConTodo+= "]";
                }
                else {
                    jsonConTodo+= ",";
                }
            }
            System.out.println(jsonConTodo);
            String[] responseStr = new String[1];
            String finalJsonConTodo = jsonConTodo;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/addhorario");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Accept", "application/json");
                        OutputStream os = connection.getOutputStream();
                        System.out.println("TETica");
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        osw.write(finalJsonConTodo);
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
                            System.out.println(responseStr[0]);
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
        }
        else {android.app.AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Advertencia")
                .setMessage("No hay diferencias")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).create();
            alertDialog.show();
        }
    }

    public RecyclerView getRvHorario() {
        return rvHorario;
    }
    //826
}