package com.jabaubo.proyecto_reservas.Objetos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.ui.VacacionesDialog;
import com.jabaubo.proyecto_reservas.ui.config.ConfigFragment;

import org.json.JSONException;

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
import java.util.List;

public class VacacionesAdapter extends RecyclerView.Adapter<VacacionesAdapter.MyViewHolder> {
    @NonNull
    private List<Vacaciones> datalist;
    private ConfigFragment configFragment;
    ViewGroup viewGroup;

    public VacacionesAdapter(@NonNull List<Vacaciones> datalist, ConfigFragment configFragment) {
        this.datalist = datalist;
        this.configFragment = configFragment;
    }

    @NonNull
    public List<Vacaciones> getDatalist() {
        return datalist;
    }

    @Override

    public VacacionesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacaciones_layout, parent, false);
        return new VacacionesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VacacionesAdapter.MyViewHolder holder, int position) {
        Vacaciones data = datalist.get(position);
        holder.tvFechaFin.setText(data.getFin().toString());
        holder.tvFechaInicio.setText(data.getInicio().toString());
        holder.tvNombre.setText(data.getNombre());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VacacionesDialog vacacionesDialog = new VacacionesDialog(data, configFragment);
                vacacionesDialog.show(configFragment.getChildFragmentManager(), null);
            }
        });
        holder.btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog alertDialog = new AlertDialog.Builder(configFragment.getContext())
                        .setTitle("Advertencia")
                        .setMessage(String.format("¿Quieres borrar la vacación %s?\nESTA ACCIÓN NO SE PUEDE DESHACER", data.getNombre()))

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String[] responseStr = new String[1];
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        // Conectamos a la pagina con el método que queramos
                                        try {
                                            URL url = new URL("https://reservante.mjhudesings.com/slim/deletevacacion");
                                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                            connection.setRequestMethod("DELETE");
                                            connection.setDoOutput(true);
                                            connection.setRequestProperty("Content-Type", "application/json");
                                            connection.setRequestProperty("Accept", "application/json");
                                            OutputStream os = connection.getOutputStream();
                                            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                                            osw.write(data.toJson());
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
                                                System.out.println(response);
                                                connection.disconnect();
                                            } else {
                                                connection.disconnect();
                                            }
                                        } catch (ProtocolException e) {
                                            throw new RuntimeException(e);
                                        } catch (MalformedURLException e) {
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
                                try {
                                    thread.join();
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }
                                System.out.println(responseStr[0]);
                                if (responseStr[0].contains("correctamente")) {
                                    Snackbar.make(configFragment.getView(),"Vacación borrada",Snackbar.LENGTH_SHORT).show();
                                    configFragment.getRvVacaciones().getAdapter().notifyItemRemoved(holder.getAdapterPosition());
                                } else {
                                    Snackbar.make(configFragment.getView(),"Vacación borrada",Snackbar.LENGTH_SHORT).show();
                                }

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar.make(configFragment.getView(),"Borrado cancelado",Snackbar.LENGTH_SHORT).show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFechaInicio;
        TextView tvFechaFin;
        TextView tvNombre;
        Button btBorrar;

        MyViewHolder(View itemView) {
            super(itemView);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicioLayout);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFinLayout);
            tvNombre = itemView.findViewById(R.id.tvNombreVacacionesLayout);
            btBorrar = itemView.findViewById(R.id.btBorrarVacaciones);
        }
    }
}
