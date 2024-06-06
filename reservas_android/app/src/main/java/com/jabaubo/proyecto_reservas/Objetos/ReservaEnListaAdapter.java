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

public class ReservaEnListaAdapter extends RecyclerView.Adapter<ReservaEnListaAdapter.MyViewHolder> {

    private List<Reserva> fullList;
    private List<Reserva> dataList;
    private FragmentManager fragmentManager;
    private Button btBorrar;
    private Button btLlamar;
    private ReservasFragment reservasFragment;
    private RecyclerView recyclerView;

    public ReservaEnListaAdapter(List<Reserva> dataList, FragmentManager fragmentManager, ReservasFragment reservasFragment, RecyclerView recyclerView) {
        this.fullList = dataList;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;
        this.reservasFragment = reservasFragment;
    }


    public List<Reserva> getDataList() {
        return dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_reservas_lista, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Rellenamos los campos
        Reserva data = dataList.get(position);
        holder.tvNombre.setText(data.getNombre_apellidos());
        holder.tvTelefono.setText(data.getTelefono());
        holder.tvComensales.setText(String.valueOf(data.getN_personas()));
        holder.tvHora.setText(data.getHora());
        holder.tvFecha.setText(data.getFecha());
        holder.itemView.setOnClickListener(view -> {
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //Mostramos cuadro de dialogo con los datos de la reserva para actualizarla
            @Override
            public void onClick(View v) {
                ReservaDialog reservaDialog = new ReservaDialog(true
                        , v
                        , data.getNombre_apellidos()
                        , data.getTelefono()
                        , data.getEmail()
                        , String.valueOf(data.getN_personas())
                        , String.valueOf(data.getId_salon())
                        , data.getObservaciones()
                        , String.valueOf(data.getId())
                        , data.getFecha()
                        , data.getHora()
                        , holder.getAdapterPosition()
                        , reservasFragment);
                reservaDialog.show(fragmentManager, "A");
            }
        });
        btBorrar = holder.itemView.findViewById(R.id.btBorrarReservaLista);
        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            //Gestión del borrado
            public void onClick(View v) {
                //Json de la petición
                String jsonStr = "{\n" +
                        "            \"id_reserva\": \"#PARAMRESERVA#\"\n" +
                        "}";
                //Sustituimos por la ID y lo pasamos
                jsonStr = jsonStr.replace("#PARAMRESERVA#", String.valueOf(data.getId()));
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
                                Runnable runnable = new Runnable() {
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
                                //Avisamos al adapter del borrado y lo sacamos de la datalist
                                reservasFragment.getRvOcupacion().getAdapter().notifyItemRemoved(holder.getAdapterPosition());
                                ((ReservaEnListaAdapter)reservasFragment.getRvOcupacion().getAdapter()).getDataList().remove(data);
                                //Mostramos que se ha borrado la reserva
                                Snackbar.make(holder.itemView, "Reserva borrada", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Mostramos que se ha cancelado el borrado de la reserva
                                Snackbar.make(holder.itemView, "Intento de reserva cancelado", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).create();
                alertDialog.show();
            }
        });
        btLlamar = holder.itemView.findViewById(R.id.btLlamarReservaLista);
        btLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Preparamos el intent para abrir la app teléfono con el número elegido
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + data.getTelefono()));
                //Se lo pasamos al activity
                reservasFragment.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvHora;
        TextView tvFecha;
        TextView tvNombre;
        TextView tvComensales;
        TextView tvTelefono;
        MyViewHolder(View itemView) {
            super(itemView);
            tvHora = itemView.findViewById(R.id.tvHora);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvComensales = itemView.findViewById(R.id.tvComensales);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);


        }
    }


    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }


    public void filtrarId(int id) {
        this.dataList = new ArrayList<>();
        for (int i = 0; i < fullList.size(); i++) {
            if (fullList.get(i).getId_salon() == id) {
                dataList.add(fullList.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public void restaurarDatos() {
        this.dataList = fullList;
        notifyDataSetChanged();
    }

    public void setDataList(List<Reserva> dataList) {
        this.dataList = dataList;
    }
}