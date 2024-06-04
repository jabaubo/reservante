package com.jabaubo.proyecto_reservas.Objetos;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.ui.ReservaDialog;
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
import java.util.ArrayList;
import java.util.List;

public class ReservaEnListaAdapter extends RecyclerView.Adapter<ReservaEnListaAdapter.MyViewHolder>{

    private List<Reserva> fullList;
    private List<Reserva> dataList;
    private FragmentManager fragmentManager;
    private Button btBorrar;
    private Button btLlamar;
    private ReservasFragmentFechas reservasFragmentFechas;
    private HomeFragment homeFragment;
    private RecyclerView recyclerView;

    public ReservaEnListaAdapter(List<Reserva> dataList, FragmentManager fragmentManager, RecyclerView recyclerView) {
        this.fullList = dataList;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;
    }

    public ReservaEnListaAdapter(List<Reserva> dataList, FragmentManager fragmentManager, ReservasFragmentFechas reservasFragmentFechas, RecyclerView  recyclerView) {
        this.fullList = dataList;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;
        this.reservasFragmentFechas = reservasFragmentFechas;
    }

    public ReservaEnListaAdapter(List<Reserva> dataList, FragmentManager fragmentManager, HomeFragment homeFragment, RecyclerView  recyclerView) {
        this.fullList = dataList;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;
        for (int i = 0 ; i < dataList.size() ; i++){
            System.out.println(dataList.get(i).getId());
        }
        this.homeFragment = homeFragment;
    }

    public List<Reserva> getDataList() {
        return dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Reserva data = dataList.get(position);
        holder.textViewTitle.setText(String.format("%s\nComensales: %d\nSalon:%s",data.getNombre_apellidos(),data.getN_personas(),data.getId_salon()));
        holder.textViewDescription.setText("Teléfono " + data.getTelefono());
        holder.itemView.setOnClickListener(view -> {});
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0 ; i < dataList.size(); i++){
                    System.out.println(dataList.get(i));
                }
                System.out.println("Position boom; " + position + " " + dataList.size());
                System.out.println("FEcha data  "  + data.getFecha());
                ReservaDialog reservaDialog;
                if (reservasFragmentFechas != null){
                    reservaDialog = new ReservaDialog(true
                            ,v
                            ,data.getNombre_apellidos()
                            ,data.getTelefono()
                            ,data.getEmail()
                            ,String.valueOf(data.getN_personas())
                            ,String.valueOf(data.getId_salon())
                            ,data.getObservaciones()
                            ,String.valueOf(data.getId())
                            ,data.getFecha()
                            ,data.getHora()
                            ,holder.getAdapterPosition()
                            ,reservasFragmentFechas);
                }
                else{
                    reservaDialog = new ReservaDialog(true
                            ,v
                            ,data.getNombre_apellidos()
                            ,data.getTelefono()
                            ,data.getEmail()
                            ,String.valueOf(data.getN_personas())
                            ,String.valueOf(data.getId_salon())
                            ,data.getObservaciones()
                            ,String.valueOf(data.getId())
                            ,data.getFecha()
                            ,data.getHora()
                            ,holder.getAdapterPosition()
                            ,homeFragment);

                }
                if (reservasFragmentFechas == null){
                    System.out.println("RF NULL");
                }
                if (homeFragment == null){
                    System.out.println("HF NULL");
                }
                reservaDialog.show(fragmentManager,"A");
                System.out.println(data.getId());
            }
        });
        btBorrar = holder.itemView.findViewById(R.id.btBorrarReserva);
        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsonStr = "{\n" +
                        "            \"id_reserva\": \"#PARAMRESERVA#\"\n" +
                        "}";
                jsonStr = jsonStr.replace("#PARAMRESERVA#",String.valueOf(data.getId()));
                String finalJsonStr = jsonStr;
                AlertDialog alertDialog = new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Advertencia")
                        .setMessage("Vas a borrar una reserva, esta acción no se puede deshacer")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Runnable runnable= new Runnable() {
                                    @Override
                                    public void run() {
                                        // Conectamos a la pagina con el método que queramos
                                        try {
                                            URL url = new URL("https://reservante.mjhudesings.com/slim/deletereserva");
                                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                            connection.setRequestMethod("DELETE");
                                            OutputStream os = connection.getOutputStream();
                                            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                                            osw.write(finalJsonStr);
                                            System.out.println("JSON EN LA PETICIÓN: " + finalJsonStr);
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
                                                System.out.println(response);
                                                reader.close();
                                                connection.disconnect();
                                                //actualizarLista();
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
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                if (reservasFragmentFechas != null){
                                    reservasFragmentFechas.avisarBorradoRecyclerView(holder.getAdapterPosition());
                                    dataList.remove(data);
                                }
                                if (homeFragment != null){
                                    homeFragment.avisarBorradoRecyclerView(holder.getAdapterPosition());
                                    dataList.remove(data);

                                }
                                Snackbar.make(holder.itemView, "Reserva borrada", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar.make(holder.itemView, "Intento de reserva cancelado", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                alertDialog.show();
            }
        });
        btLlamar = holder.itemView.findViewById(R.id.btLlamarReserva);
        btLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+data.getTelefono()));
                if (reservasFragmentFechas != null){
                    reservasFragmentFechas.getActivity().startActivity(intent);
                }
                else {
                    homeFragment.getActivity().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;

        MyViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tvPrincipal);
            textViewDescription = itemView.findViewById(R.id.tvTlfReserva);

        }


    }
    public void actualizarLista(){
        final JSONArray[] jsonArray = new JSONArray[1];
        try {
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
                        jsonRequest = jsonRequest.replace("#PARAMFECHA#",lista.get(0).getFecha());
                        jsonRequest = jsonRequest.replace("#PARAMHORA#",lista.get(0).getHora());
                        osw.write(jsonRequest);
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
        }ArrayList<Reserva> lista = new ArrayList<>();
        if (jsonArray[0] != null){
            for (int i  = 0 ; i < jsonArray[0].length() ; i++){
                try {
                    JSONObject jsonObject = (JSONObject) jsonArray[0].get(i);
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
        lista = new ArrayList<>(null);
        recyclerView.setAdapter(new ReservaAdapter(lista,this.getFragmentManager(),getReservasFragmentFechas(),recyclerView));
        recyclerView.refreshDrawableState();
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public ReservasFragmentFechas getReservasFragmentFechas() {
        return reservasFragmentFechas;
    }

    public void filtrarId(int id){
        this.dataList = new ArrayList<>();
        for (int i = 0 ; i < fullList.size() ; i++){
            if (fullList.get(i).getId_salon()==id){
                dataList.add(fullList.get(i));
            }
        }
        notifyDataSetChanged();
    }
    public void restaurarDatos(){
        this.dataList = fullList;
        notifyDataSetChanged();
    }

    public void setDataList(List<Reserva> dataList) {
        this.dataList = dataList;
    }
}