package com.jabaubo.proyecto_reservas.ui.reservas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabaubo.proyecto_reservas.MainActivity;
import com.jabaubo.proyecto_reservas.Objetos.Reserva;
import com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter;
import com.jabaubo.proyecto_reservas.Objetos.ReservaEnListaAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentReservasBinding;
import com.jabaubo.proyecto_reservas.ui.BuscarDialog;

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
import java.util.List;

public class ReservasFragment  extends Fragment {
    private FragmentReservasBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<Reserva> lista;
    private ArrayList<Reserva> listaCompleta;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReservasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Carga de componentes
        recyclerView = root.findViewById(R.id.listaReservas);
        Button btRestaurar = root.findViewById(R.id.btRestaurar);
        //Lectura y carga de listado de reservas
        listaCompleta = leerReservas();
        lista = listaCompleta;
        ReservaEnListaAdapter adapter = new ReservaEnListaAdapter(lista,getChildFragmentManager(),this,recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btRestaurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReservaEnListaAdapter)recyclerView.getAdapter()).setDataList(listaCompleta);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
        //Gestión del menú de búsqueda
        Menu menu = ((MainActivity)getActivity()).getMenu();
        MenuItem buscarNombre = menu.findItem(R.id.buscarClienteNombre);
        buscarNombre.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                BuscarDialog buscarDialog = new BuscarDialog((ArrayList<Reserva>) ((ReservaEnListaAdapter)recyclerView.getAdapter()).getDataList(),BuscarDialog.NOMBRE , recyclerView);
                buscarDialog.show(getChildFragmentManager(),null);
                return false;
            }
        });
        buscarNombre.setVisible(true);
        MenuItem buscarTlf = menu.findItem(R.id.buscarClienteTlf);
        buscarTlf.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                BuscarDialog buscarDialog = new BuscarDialog((ArrayList<Reserva>) ((ReservaEnListaAdapter)recyclerView.getAdapter()).getDataList(),BuscarDialog.TELEFONO     , recyclerView);
                buscarDialog.show(getChildFragmentManager(),null);
                return false;
            }
        });
        buscarTlf.setVisible(true);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<Reserva> leerReservas() {
        final String[] responseStr = new String[1];
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getreservas");
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
                    } catch (ProtocolException e) {
                        throw new RuntimeException(e);
                    } catch (MalformedURLException e) {
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
            //Recorremos el array y lo vamos cargando en la lista
            jsonArray = new JSONObject(responseStr[0]).getJSONArray("horarios");
            ArrayList<Reserva> lista = new ArrayList<>();
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
            return lista;
        } catch (JSONException e) {
            android.app.AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Advertencia")
                    .setMessage("No hay reservas registradas a futuro")
                    .setPositiveButton(android.R.string.yes, null)
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert).create();
            alertDialog.show();
            return new ArrayList<>();
        }
    }

    public RecyclerView getRvOcupacion() {
        return recyclerView;
    }

    @Override
    public void onStop() {
        Menu menu = ((MainActivity) getActivity()).getMenu();
        MenuItem buscarNombre = menu.findItem(R.id.buscarClienteNombre);
        buscarNombre.setVisible(false);
        MenuItem buscarTlf = menu.findItem(R.id.buscarClienteTlf);
        buscarTlf.setVisible(false);
        super.onStop();
    }
}
