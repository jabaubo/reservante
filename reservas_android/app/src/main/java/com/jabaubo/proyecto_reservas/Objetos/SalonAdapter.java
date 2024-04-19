package com.jabaubo.proyecto_reservas.Objetos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.MyViewHolder>{
    private List<Salon> dataList;
    private ConfigFragment configFragment;
    public SalonAdapter(ArrayList<Salon> lista,ConfigFragment configFragment) {
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
        System.out.println("Le data: " + data.getId());
        holder.tvIdSalon.setText(String.valueOf(data.getId()));
        holder.tvNombreSalon.setText(data.getNombre());
        holder.tvAforoSalon.setText("Aforo:" + data.getAforo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ocupado(data.getId())){

                }else {
                    SalonDialog salonDialog = new SalonDialog(data.getAforo(),data.getNombre());
                    salonDialog.show(configFragment.getActivity().getSupportFragmentManager(),"a");
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

        MyViewHolder(View itemView) {
            super(itemView);
            tvNombreSalon = itemView.findViewById(R.id.tvNombreSalon);
            tvIdSalon = itemView.findViewById(R.id.tvIdSalonRV);
            tvAforoSalon = itemView.findViewById(R.id.tvAforoSalon);
        }
    }
    public boolean ocupado(int id){
        String jsonStr = "{\n" +
                "    \"id\":\"#PARAMID#\"\n" +
                "}";
        jsonStr = jsonStr.replace("#PARAMID#",String.valueOf(id));

        String finalJsonStr = jsonStr;
        final int[] reservas = new int[1];
        Runnable runnable= new Runnable() {
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
        return reservas[0]>0;
    }
}
