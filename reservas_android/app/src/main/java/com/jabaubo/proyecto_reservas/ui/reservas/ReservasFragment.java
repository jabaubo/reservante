package com.jabaubo.proyecto_reservas.ui.reservas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabaubo.proyecto_reservas.Objetos.Reserva;
import com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentReservasBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReservasFragment  extends Fragment {
    /*
    * json fecha
    * {
            "fecha": "2024-04-08"
}
    * */
    private FragmentReservasBinding binding;
    private TextView tvPruebas;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReservasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.listaReservas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        cargarReservas(recyclerView);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void cargarReservas (RecyclerView recyclerView){
        final String[] responseStr = new String[1];
        try {
            Runnable runnable= new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getreservas");
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
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray jsonArray;
        try {
            jsonArray = new JSONObject(responseStr[0]).getJSONArray("horarios");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        List<Reserva> lista = new ArrayList<>();
        for (int i = 0 ; i < jsonArray.length() ; i++){
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

        // Add more items as needed
        ReservaAdapter adapter = new ReservaAdapter(lista,getActivity().getSupportFragmentManager(),recyclerView);
        recyclerView.setAdapter(adapter);

    }
    public String[] pruebas() {
        final String[] responseStr = new String[1];
        try {
            System.out.println("Pa dentro");
            Runnable runnable= new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getreservas");
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
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseStr;
    }
}
