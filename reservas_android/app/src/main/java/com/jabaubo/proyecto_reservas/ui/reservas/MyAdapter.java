package com.jabaubo.proyecto_reservas.ui.reservas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.ui.ReservaDialog;
import com.jabaubo.proyecto_reservas.ui.reservas_fechas.ReservasFragmentFechas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private List<Reserva> dataList;
    private FragmentManager fragmentManager;

    public MyAdapter(List<Reserva> dataList, FragmentManager fragmentManager) {
        this.dataList = dataList;
        this.fragmentManager = fragmentManager;
        for (int i = 0 ; i < dataList.size() ; i++){
            System.out.println(dataList.get(i).getId());
        }
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
        holder.textViewTitle.setText(String.format("%s\nComensales: %d",data.getNombre_apellidos(),data.getN_personas(),data.getHora()));
        holder.textViewDescription.setText("Teléfono" + data.getTelefono());
        holder.itemView.setOnClickListener(view -> {});
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservaDialog reservaDialog = new ReservaDialog(true
                        ,v
                        ,data.getNombre_apellidos()
                        ,data.getTelefono()
                        ,data.getEmail()
                        ,String.valueOf(data.getN_personas())
                        ,String.valueOf(data.getId_salon())
                        ,data.getObservaciones()
                        ,String.valueOf(data.getId())
                        ,data.getFecha()
                        ,data.getHora() );
                reservaDialog.show(fragmentManager,"A");
                System.out.println(data.getId());
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
}