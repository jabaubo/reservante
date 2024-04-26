package com.jabaubo.proyecto_reservas.Objetos;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.ui.home.HomeFragment;
import com.jabaubo.proyecto_reservas.ui.reservas_fechas.ReservasFragmentFechas;

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
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class ReservasFechaAdapter extends RecyclerView.Adapter<ReservasFechaAdapter.MyViewHolder>{

    private FragmentManager fragmentManager;
    private ReservasFragmentFechas reservasFragmentFechas;
    private HomeFragment homeFragment;
    private RecyclerView recyclerView;
    private TextView textView;
    private List<ReservaFechas> dataList;

    public ReservasFechaAdapter(FragmentManager fragmentManager, RecyclerView recyclerView, TextView textView, List<ReservaFechas> dataList, ReservasFragmentFechas reservasFragmentFechas) {
        this.fragmentManager = fragmentManager;
        this.recyclerView = recyclerView;
        this.textView = textView;
        this.dataList = dataList;
        this.reservasFragmentFechas = reservasFragmentFechas;
    }

    public ReservasFechaAdapter(FragmentManager fragmentManager, RecyclerView recyclerView, TextView textView, List<ReservaFechas> dataList , HomeFragment homeFragment) {
        this.fragmentManager = fragmentManager;
        this.recyclerView = recyclerView;
        this.textView = textView;
        this.dataList = dataList;
        this.homeFragment = homeFragment;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ocupacion_layout, parent, false);
        return new MyViewHolder(view);
    }

    public List<ReservaFechas> getDataList() {
        return dataList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ReservaFechas data = dataList.get(position);
        holder.tvHora.setText(data.getHora());
        holder.tvReservas.setText("Reservas: " + data.getnReservas());
        holder.tvAforo.setText(Html.fromHtml(formatearOcupacion(data.getOcupacion()),Html.FROM_HTML_MODE_LEGACY));
        holder.itemView.setOnClickListener(view -> {
            System.out.println(data.getFecha() + " " + data.getHora());
            JSONArray jsonArray = verReservas(data.getFecha(),data.getHora());
            ArrayList<Reserva> lista = new ArrayList<>();
            if (jsonArray != null){
                for (int i  = 0 ; i < jsonArray.length() ; i++){
                    try {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        System.out.println(jsonObject);
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
            }
            recyclerView.setAdapter(new ReservaAdapter(lista,fragmentManager,this.reservasFragmentFechas,recyclerView));
            textView.setText(data.getFecha() + " Tramo " + data.getHora());
            if (homeFragment!=null){
                homeFragment.comprobarBotones();
            }
            else if (reservasFragmentFechas != null){
                reservasFragmentFechas.comprobarBotones();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvHora;
        TextView tvReservas;
        TextView tvAforo;
        MyViewHolder(View itemView) {
            super(itemView);
            tvHora = itemView.findViewById(R.id.tvOcupacionHora);
            tvReservas = itemView.findViewById(R.id.tvOcupacionNReservas);
            tvAforo = itemView.findViewById(R.id.tvOcupacionAforo);

        }

    }
    public static JSONArray verReservas(String fecha , String hora){
        final JSONArray[] jsonArray = new JSONArray[1];
        try {
            System.out.println("Pa dentro");
            ArrayList<ReservaFechas> lista = new ArrayList<>();
            Runnable runnable= new Runnable() {
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
                        jsonRequest = jsonRequest.replace("#PARAMFECHA#",fecha);
                        jsonRequest = jsonRequest.replace("#PARAMHORA#",hora);
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
                            System.out.println("Resùesta " + response);
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
    public String formatearOcupacion(String texto){
        String base = texto;

        String[] baseArray = base.split("\n");
        String formateado = "";
        for (int i = 0 ; i < baseArray.length ; i++){
            System.out.println(baseArray[i]);
            String division = baseArray[i].substring(baseArray[i].lastIndexOf(" ")+1);
            String valores[] = division.split("/");
            float ratio = Float.valueOf(valores[0])/Float.valueOf(valores[1]);
            System.out.println(baseArray[i] + " RATIO : " + ratio);
            if (ratio<0.33f){
                baseArray[i] = baseArray[i].replace(division,"<font color='#008000'>"+valores[0]+"</font>/"+valores[1]+"<br></br>");
            } else if (ratio > 0.33f && ratio<0.66f ) {
                baseArray[i] = baseArray[i].replace(division,"<font color='#FFEB00'>"+valores[0]+"</font>/"+valores[1]+"<br></br>");
            } else if (ratio > 0.66f){
                baseArray[i] = baseArray[i].replace(division,"<font color='#8B0000'>"+valores[0]+"</font>/"+valores[1]+"<br></br>");
            }
            System.out.println();
            formateado += baseArray[i];
        }
        return formateado;
    }
}