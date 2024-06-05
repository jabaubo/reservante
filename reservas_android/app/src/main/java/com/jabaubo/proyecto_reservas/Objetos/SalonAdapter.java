package com.jabaubo.proyecto_reservas.Objetos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.ui.SalonDialog;
import com.jabaubo.proyecto_reservas.ui.config.ConfigFragment;

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

public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.MyViewHolder> {
    private List<Salon> dataList;
    private ConfigFragment configFragment;

    public SalonAdapter(ArrayList<Salon> lista, ConfigFragment configFragment) {
        dataList = lista;
        this.configFragment = configFragment;
    }

    @NonNull
    @Override
    public SalonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salon_layout, parent, false);
        return new SalonAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonAdapter.MyViewHolder holder, int position) {
        Salon data = dataList.get(position);
        holder.tvIdSalon.setText(String.valueOf(data.getId()));
        holder.tvNombreSalon.setText(data.getNombre());
        holder.tvAforoSalon.setText("Aforo:" + data.getAforo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ocupado(data.getId())) {
                    Toast.makeText(configFragment.getContext(), "Tetica", Toast.LENGTH_SHORT);
                    Snackbar.make(configFragment.getView(), "Este salón tiene reservas a futuro.\nNo se permite edición", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                } else {
                    SalonDialog salonDialog = new SalonDialog(data.getId(), data.getAforo(), data.getNombre(), configFragment, holder.getAdapterPosition());
                    salonDialog.show(configFragment.getActivity().getSupportFragmentManager(), "a");
                }
            }
        });
        holder.btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ocupado(data.getId())) {
                    Snackbar.make(configFragment.getView(), "Este salón tiene reservas a futuro.\nNo se permite edición", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(configFragment.getContext())
                            .setTitle("Aviso")
                            .setMessage(String.format("¿Quieres borrar el salón %s?\nEsta acción no se puede deshacer.", data.getNombre()))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int codigo = borrarSalon(data.getId());
                                    if (codigo == 1){
                                        ((SalonAdapter) configFragment.getRvSalones().getAdapter()).getDataList().remove(((SalonAdapter) configFragment.getRvSalones().getAdapter()).getDataList().get(holder.getAdapterPosition()));
                                        configFragment.getRvSalones().getAdapter().notifyItemRemoved(holder.getAdapterPosition());
                                    }
                                    else{AlertDialog alertDialog = new AlertDialog.Builder(configFragment.getContext())
                                            .setTitle("Aviso")
                                            .setMessage("No se ha podido borrar el salon")
                                            .setPositiveButton(android.R.string.yes,null)
                                            .setNegativeButton(android.R.string.no,null)
                                            .setIcon(android.R.drawable.ic_dialog_alert).create();
                                        alertDialog.show();
                                    }


                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Snackbar.make(configFragment.getView(), "Borrado cancelado", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert).create();
                    alertDialog.show();
                }
            }
        });
        holder.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ocupado(data.getId())) {
                    Toast.makeText(configFragment.getContext(), "Tetica", Toast.LENGTH_SHORT);
                    Snackbar.make(configFragment.getView(), "Este salón tiene reservas a futuro.\nNo se permite edición", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                } else {
                    SalonDialog salonDialog = new SalonDialog(data.getId(), data.getAforo(), data.getNombre(), configFragment, holder.getAdapterPosition());
                    salonDialog.show(configFragment.getActivity().getSupportFragmentManager(), "a");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreSalon;
        TextView tvIdSalon;
        TextView tvAforoSalon;
        Button btBorrar;
        Button btEditar;

        MyViewHolder(View itemView) {
            super(itemView);
            tvNombreSalon = itemView.findViewById(R.id.tvNombreSalon);
            tvIdSalon = itemView.findViewById(R.id.tvIdSalonRV);
            tvAforoSalon = itemView.findViewById(R.id.tvAforoSalon);
            btEditar = itemView.findViewById(R.id.btEditarSD);
            btBorrar = itemView.findViewById(R.id.btBorrarSD);

        }
    }

    public boolean ocupado(int id) {
        String jsonStr = "{\n" +
                "    \"id\":\"#PARAMID#\"\n" +
                "}";
        jsonStr = jsonStr.replace("#PARAMID#", String.valueOf(id));

        String finalJsonStr = jsonStr;
        final int[] reservas = new int[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/getreservassalones");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(finalJsonStr);
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
                        reservas[0] = new JSONObject(response.toString()).getJSONArray("resultado").getJSONObject(0).getInt("total");
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
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return reservas[0] > 0;
    }

    public int borrarSalon(int id) {
        String json = "    {   \n" +
                "        \"id_salon\":\"#PARAMID#\"\n" +
                "    }";
        json = json.replace("#PARAMID#", String.valueOf(id));
        String[] responseStr = new String[1];
        String finalJson = json;
        System.out.println(json);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/deletesalon");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("DELETE");
                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    osw.write(finalJson);
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
                        System.out.println("Borrado:" + response);
                        responseStr[0] = response.toString();
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
        try {
            return new JSONObject(responseStr[0]).getInt("codigo");
        } catch (JSONException e) {
            return 0;
        }
    }

    public List<Salon> getDataList() {
        return dataList;
    }

}
