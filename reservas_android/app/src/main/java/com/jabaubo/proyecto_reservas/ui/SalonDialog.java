package com.jabaubo.proyecto_reservas.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.MainActivity;
import com.jabaubo.proyecto_reservas.Objetos.Reserva;
import com.jabaubo.proyecto_reservas.Objetos.ReservaAdapter;
import com.jabaubo.proyecto_reservas.Objetos.Salon;
import com.jabaubo.proyecto_reservas.Objetos.SalonAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.ui.config.ConfigFragment;
import com.jabaubo.proyecto_reservas.ui.home.HomeFragment;

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

public class SalonDialog extends DialogFragment {
    private boolean editando = false;
    private int id;
    private int adapterPosition;
    private int aforo;
    private String nombre;

    private ConfigFragment configFragment;
    private HomeFragment homeFragment;

    private MainActivity activity;
    private EditText etNombre;
    private EditText etAforo;
    private Button btBorrar;

    public SalonDialog(ConfigFragment configFragment) {
        this.configFragment = configFragment;
        this.activity = (MainActivity) configFragment.getActivity();
    }

    public SalonDialog(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        this.activity = (MainActivity) homeFragment.getActivity();
    }

    public SalonDialog(int id, int aforo, String nombre, ConfigFragment configFragment, int adapterPosition) {
        editando = true;
        this.id = id;
        this.aforo = aforo;
        this.nombre = nombre;
        this.configFragment = configFragment;
        this.adapterPosition = adapterPosition;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_dialog_salon, null);
        etNombre = view.findViewById(R.id.etNombreSD);
        etAforo = view.findViewById(R.id.etAforoSD);
        btBorrar = view.findViewById(R.id.btBorrarS);
        if (aforo != 0) {
            etAforo.setText(String.valueOf(aforo));
            System.out.println(etAforo.getText());
        }
        if (nombre != null && !nombre.equals("")) {
            etNombre.setText(nombre);
        }
        btBorrar.setEnabled(editando);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialogo_guardar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editando) {
                            String json = jsonInsertar();
                            System.out.println(json);
                            final String[] responseStr = new String[1];
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    // Conectamos a la pagina con el método que queramos
                                    try {
                                        URL url = new URL("https://reservante.mjhudesings.com/slim/addsalon");
                                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                        connection.setRequestMethod("POST");
                                        connection.setDoOutput(true);
                                        connection.setRequestProperty("Content-Type", "application/json");
                                        connection.setRequestProperty("Accept", "application/json");
                                        OutputStream os = connection.getOutputStream();
                                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                                        String jsonStr = "{\n"
                                                + "    \"id_restaurante\":\"#PARAMID#\",\n"
                                                + "    \"nombre\":\"#PARAMNOMBRE#\",\n"
                                                + "    \"aforo\":\"#PARAMAFORO#\"\n"
                                                + "}";
                                        jsonStr = jsonStr.replace("#PARAMID#", String.valueOf(activity.getIdRestaurante()));
                                        jsonStr = jsonStr.replace("#PARAMNOMBRE#", etNombre.getText());
                                        jsonStr = jsonStr.replace("#PARAMAFORO#", etAforo.getText());
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

                            Snackbar.make(view, "Guardando", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            try {
                                JSONObject jsonObject = new JSONObject(responseStr[0]);
                                System.out.println(jsonObject);
                                Salon s = new Salon();
                                s.setNombre(jsonObject.getString("nombre"));
                                s.setId(jsonObject.getInt("id"));
                                s.setAforo(Integer.valueOf(jsonObject.getString("aforo")));
                                RecyclerView rvSalones = configFragment.getRvSalones();
                                ((SalonAdapter) rvSalones.getAdapter()).getDataList().add(s);
                                rvSalones.getAdapter().notifyItemInserted(rvSalones.getAdapter().getItemCount() - 1);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            String json = jsonActualizar();
                            System.out.println(json);
                            System.out.println("JSON actualizar: " + json);
                            if (json.equals("No hay diferencias")) {
                                Snackbar.make(configFragment.getView(), json, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                System.out.println(json);
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Conectamos a la pagina con el método que queramos
                                        try {
                                            URL url = new URL("https://reservante.mjhudesings.com/slim/updatesalon");
                                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                            connection.setRequestMethod("PUT");
                                            OutputStream os = connection.getOutputStream();
                                            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
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

                                Salon s = ((SalonAdapter) configFragment.getRvSalones().getAdapter()).getDataList().get(adapterPosition);
                                JSONObject jsonObj;
                                try {
                                    jsonObj = new JSONObject(json);
                                    System.out.println("JSONDERULO: " + json);
                                    s.setAforo(Integer.valueOf(jsonObj.getString("aforo")));
                                    s.setNombre(jsonObj.getString("nombre"));
                                    ((SalonAdapter) configFragment.getRvSalones().getAdapter()).getDataList().set(adapterPosition, s);
                                    configFragment.getRvSalones().getAdapter().notifyDataSetChanged();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                Snackbar.make(configFragment.getView(), "Actualización correcta", Snackbar.LENGTH_LONG);

                            }

                        }
                    }
                })
                .setNegativeButton(R.string.dialogo_cancelar, null);
        return alertDialogBuilder.create();
    }

    public String jsonInsertar() {
        String base = "{\n" +
                "    \"nombre\":\"#PARAMNOMBRE#\",\n" +
                "    \"aforo\":\"#PARAMAFORO#\"\n" +
                "}";
        String json = base.replace("#PARAMNOMBRE#", etNombre.getText().toString())
                .replace("#PARAMAFORO#", etAforo.getText().toString());
        return json;
    }

    public String jsonActualizar() {
        //Comprobar existencia de diferencias
        boolean diferente = false;
        if (!etNombre.getText().toString().equals(nombre) || !etAforo.getText().toString().equals(aforo)) {
            diferente = true;
        }
        if (diferente) {
            String base = "{\n" +
                    "    \"id\":\"#PARAMID#\",\n" +
                    "    \"nombre\":\"#PARAMNOMBRE#\",\n" +
                    "    \"aforo\":\"#PARAMAFORO#\"\n" +
                    "}";
            String json = base.replace("#PARAMID#", String.valueOf(id))
                    .replace("#PARAMNOMBRE#", etNombre.getText().toString())
                    .replace("#PARAMAFORO#", etAforo.getText().toString());
            return json;
        } else {
            return "No hay diferencias";
        }
    }
}
