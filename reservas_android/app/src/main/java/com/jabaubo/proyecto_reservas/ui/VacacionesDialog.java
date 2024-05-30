package com.jabaubo.proyecto_reservas.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jabaubo.proyecto_reservas.MainActivity;
import com.jabaubo.proyecto_reservas.Objetos.Vacaciones;
import com.jabaubo.proyecto_reservas.Objetos.VacacionesAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.ui.config.ConfigFragment;

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

public class VacacionesDialog extends DialogFragment {
    private Vacaciones vacacion;
    private EditText etNombre;
    private TextView tvInicio;
    private TextView tvFin;
    private ConfigFragment configFragment;

    public VacacionesDialog(ConfigFragment configFragment) {
        this.configFragment = configFragment;
    }

    public VacacionesDialog(Vacaciones vacacion ,ConfigFragment configFragment) {
        this.vacacion = vacacion;
        this.configFragment = configFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_dialog_vacaciones, null);
        etNombre = view.findViewById(R.id.etNombreVacaciones);
        tvInicio = view.findViewById(R.id.tvInicioVacacionesDialog);
        tvFin = view.findViewById(R.id.tvFinVacacionesDialog);

        tvInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFecha(tvInicio);
            }
        });
        tvFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFecha(tvFin);
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view).setPositiveButton(R.string.dialogo_guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (vacacion != null) {
                    if (vacacion.getNombre().equals(etNombre.getText().toString()) &&
                            vacacion.getFin().toString().equals(tvFin.getText()) &&
                            vacacion.getInicio().toString().equals(tvInicio.getText())) {

                    } else {
                    }
                } else {
                    final String[] responseStr = new String[1];
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            // Conectamos a la pagina con el método que queramos
                            try {
                                URL url = new URL("https://reservante.mjhudesings.com/slim/addvacacion");
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
                                        + "    \"inicio\":\"#PARAMINICIO#\",\n"
                                        + "    \"fin\":\"#PARAMFIN#\"\n"
                                        + "}";
                                jsonStr = jsonStr.replace("#PARAMID#", String.valueOf(((MainActivity) getActivity()).getIdRestaurante()));
                                jsonStr = jsonStr.replace("#PARAMNOMBRE#", etNombre.getText());
                                jsonStr = jsonStr.replace("#PARAMINICIO#", tvInicio.getText());
                                jsonStr = jsonStr.replace("#PARAMFIN#", tvFin.getText());
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
                                    System.out.println(responseStr[0]);
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
                    try {
                        thread.join();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        JSONObject jsonRespuesta = new JSONObject(responseStr[0]);
                        if (jsonRespuesta.getInt("codigo") == 1) {
                            Vacaciones v = new Vacaciones(etNombre.getText().toString()
                                    ,tvInicio.getText().toString()
                                    ,tvFin.getText().toString()
                                    ,((MainActivity)configFragment.getActivity()).getIdRestaurante()
                                    ,jsonRespuesta.getInt("id"));
                            ((VacacionesAdapter)configFragment.getRvVacaciones().getAdapter()).getDatalist().add(v);
                            ((VacacionesAdapter)configFragment.getRvVacaciones().getAdapter()).notifyItemInserted(((VacacionesAdapter)configFragment.getRvVacaciones().getAdapter()).getItemCount()-1);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).setNegativeButton(R.string.dialogo_cancelar, null);
        if (vacacion != null) {
            etNombre.setText(vacacion.getNombre());
            tvFin.setText(vacacion.getFin().toString());
            tvInicio.setText(vacacion.getInicio().toString());
        }
        return alertDialogBuilder.create();
    }

    public void clickFecha(TextView tvFecha) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month < 10) {
                            tvFecha.setText(String.format("%d-0%d-%d", year, month, dayOfMonth));
                        } else {
                            tvFecha.setText(String.format("%d-%d-%d", year, month, dayOfMonth));
                        }

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
