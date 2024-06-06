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

import com.jabaubo.proyecto_reservas.MainActivity;
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

    //Adapter en ReservasFragmentFechas
    public ReservasFechaAdapter(FragmentManager fragmentManager, RecyclerView recyclerView, TextView textView, List<ReservaFechas> dataList, ReservasFragmentFechas reservasFragmentFechas) {
        this.fragmentManager = fragmentManager;
        this.recyclerView = recyclerView;
        this.textView = textView;
        this.dataList = dataList;
        this.reservasFragmentFechas = reservasFragmentFechas;
    }

    //Adapter en HomeFragment
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
        //Rellenamos los campos
        ReservaFechas data = dataList.get(position);
        holder.tvHora.setText(data.getHora());
        holder.tvReservas.setText("Reservas: " + data.getnReservas());
        holder.tvAforo.setText(Html.fromHtml((data.getOcupacion()),Html.FROM_HTML_MODE_LEGACY));
        holder.itemView.setOnClickListener(view -> {
            //Cargamos la lista de las reservas correspondiente a la hora y fecha del tramo
            ArrayList<Reserva> lista = verReservas(data.getFecha(),data.getHora());
            //Depende de en que Fragment estemos , le pasamos un Fragment u otro al Adapter que vamos a crear
            if (this.reservasFragmentFechas != null){
                recyclerView.setAdapter(new ReservaAdapter(lista,fragmentManager,this.reservasFragmentFechas,recyclerView));
            }
            else if (this.homeFragment != null){
                recyclerView.setAdapter(new ReservaAdapter(lista,fragmentManager,this.homeFragment,recyclerView));

            }
            //Actualizamos el TextView que muestra la fecha
            textView.setText(data.getFecha() + " Tramo " + data.getHora());
            //Comprobamos el estilo que deben tener los botones
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
    public ArrayList<Reserva> verReservas(String fecha, String hora) {
        //Json en el que se va a guardar el resultado de la petición
        final JSONArray[] jsonArray = new JSONArray[1];
        ArrayList<Reserva> lista = new ArrayList<>();
        try {
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
                        //Json del request de la petición
                        String jsonRequest = "{\"fecha\": \"#PARAMFECHA#\",\"hora\":\"#PARAMHORA#\",\"id\":\"#PARAMID#\"}";
                        jsonRequest = jsonRequest.replace("#PARAMFECHA#", fecha);
                        jsonRequest = jsonRequest.replace("#PARAMHORA#", hora);
                        //Obtenemos la Id del restaurante a comprobar
                        if (homeFragment!= null){
                            jsonRequest = jsonRequest.replace("#PARAMID#", String.valueOf(((MainActivity)homeFragment.getActivity()).getIdRestaurante()));
                        }
                        else if (reservasFragmentFechas != null){
                            jsonRequest = jsonRequest.replace("#PARAMID#", String.valueOf(((MainActivity)reservasFragmentFechas.getActivity()).getIdRestaurante()));
                        }
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
                            //Vamos recorriendo el JSONArray , leyendo sus datos y almacenando en el ArrayList
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
            //Arrancamos el hilo y lo sincronizamos para que la app espere la respuesta
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void setDataList(List<ReservaFechas> dataList) {
        this.dataList = dataList;
    }
}