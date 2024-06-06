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
import com.jabaubo.proyecto_reservas.ui.reservas.ReservasFragment;
import com.jabaubo.proyecto_reservas.ui.reservas_fechas.ReservasFragmentFechas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.MyViewHolder>{

    private List<Reserva> fullList;
    private List<Reserva> dataList;
    private FragmentManager fragmentManager;
    private Button btBorrar;
    private Button btLlamar;
    private ReservasFragmentFechas reservasFragmentFechas;
    private HomeFragment homeFragment;
    private RecyclerView recyclerView;
    //Adapter general
    public ReservaAdapter(List<Reserva> dataList, FragmentManager fragmentManager, RecyclerView recyclerView) {
        this.fullList = dataList;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;
    }
    //Adapter en ReservasFragmentFechas
    public ReservaAdapter(List<Reserva> dataList, FragmentManager fragmentManager, ReservasFragmentFechas reservasFragmentFechas, RecyclerView  recyclerView) {
        this.fullList = dataList;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;
        this.reservasFragmentFechas = reservasFragmentFechas;
    }
    //Adapter en HomeFragment
    public ReservaAdapter(List<Reserva> dataList, FragmentManager fragmentManager, HomeFragment homeFragment, RecyclerView  recyclerView) {
        this.fullList = dataList;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;
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
        //Rellenamos los valores
        holder.textViewTitle.setText(String.format("%s\nComensales: %d\nSalon:%s",data.getNombre_apellidos(),data.getN_personas(),data.getId_salon()));
        holder.textViewDescription.setText("Teléfono " + data.getTelefono());
        holder.itemView.setOnClickListener(view -> {});
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostramos cuadro de dialogo con los datos de la reserva para actualizarla
                ReservaDialog reservaDialog;
                if (reservasFragmentFechas != null){
                    //Si estamos en reservaFragmentFechas , le pasamos ese fragment
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
                    //Si estamos en HomeFragment , le pasamos ese fragment
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
                reservaDialog.show(fragmentManager,"A");
            }
        });
        btBorrar = holder.itemView.findViewById(R.id.btBorrarReserva);
        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            //Gestión del borrado
            public void onClick(View v) {
                //JSON de la petición con la reserva a borrar
                String jsonStr = "{\n" +
                        "            \"id_reserva\": \"#PARAMRESERVA#\"\n" +
                        "}";
                //Sustituimos por la ID y lo pasamos
                jsonStr = jsonStr.replace("#PARAMRESERVA#",String.valueOf(data.getId()));
                String finalJsonStr = jsonStr;
                /*
                * ADVERTENCIA DE BORRADO
                * Aceptar -> Iniciar borrado
                * Cancelar -> Mostrar que se ha cancelado el borrado
                * */
                AlertDialog alertDialog = new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Advertencia")
                        .setMessage("Vas a borrar una reserva, esta acción no se puede deshacer")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Preparamos el hilo de la petición
                                Runnable runnable= new Runnable() {
                                    @Override
                                    public void run() {
                                        // Conectamos a la pagina con el método que queramos
                                        try {
                                            URL url = new URL("https://reservante.mjhudesings.com/slim/deletereserva");
                                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                            connection.setRequestMethod("DELETE");
                                            //Escribimos el json
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
                                //Arrancamos el hilo y lo sincronizamos para que la app espere la respueta
                                Thread thread = new Thread(runnable);
                                thread.start();
                                try {
                                    thread.join();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                //Depende de en que Fragment estemos , actualizamos el DataList del adapter
                                if (reservasFragmentFechas != null){
                                    reservasFragmentFechas.avisarBorradoRecyclerView(holder.getAdapterPosition());
                                    dataList.remove(data);
                                }
                                if (homeFragment != null){
                                    homeFragment.avisarBorradoRecyclerView(holder.getAdapterPosition());
                                    dataList.remove(data);

                                }
                                //Avisamos del borrado
                                Snackbar.make(holder.itemView, "Reserva borrada", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Avisamos de la cancelación
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
                //Preparamos el intent para abrir la app teléfono con el número elegido
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+data.getTelefono()));
                //Usamos el activity del Fragment en el que estemos
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
        public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public ReservasFragmentFechas getReservasFragmentFechas() {
        return reservasFragmentFechas;
    }

    //Filtrar id de las Reservas por salon
    public void filtrarId(int id){
        this.dataList = new ArrayList<>();
        //Recorremos la lista completa
        for (int i = 0 ; i < fullList.size() ; i++){
            //Si los id del salon coinciden con el seleccionado , los añadimos a la lista
            if (fullList.get(i).getId_salon()==id){
                dataList.add(fullList.get(i));
            }
        }
        //Avisamos del cambio al Adapter
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