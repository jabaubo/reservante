package com.jabaubo.proyecto_reservas.Objetos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jabaubo.proyecto_reservas.R;

import java.util.ArrayList;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.MyViewHolder> {
    private ArrayList<Horario> datalist;

    public HorarioAdapter(ArrayList<Horario> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horario_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Horario data = datalist.get(position);
        holder.tvDia.setText(data.getDia());
        holder.sCerrado.setChecked(data.getCerrado());
        holder.etInicioM.setText(data.getHora_inicio_m());
        holder.etFinM.setText(data.getHora_fin_m());
        holder.etInicioT.setText(data.getHora_inicio_t());
        holder.etFinT.setText(data.getHora_fin_t());
        holder.sCerrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cerrado = holder.sCerrado.isChecked();
                holder.etInicioM.setEnabled(cerrado);
                holder.etFinM.setEnabled(cerrado);
                holder.etInicioT.setEnabled(cerrado);
                holder.etFinT.setEnabled(cerrado);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDia;
        Switch sCerrado;
        EditText etInicioM;
        EditText etFinM;
        EditText etInicioT;
        EditText etFinT;

        MyViewHolder(View itemView) {
            super(itemView);
            tvDia = itemView.findViewById(R.id.tvHorarioDia);
            sCerrado = itemView.findViewById(R.id.sCerrado);
            etInicioM = itemView.findViewById(R.id.etInicioM);
            etFinM = itemView.findViewById(R.id.etFinM);
            etInicioT = itemView.findViewById(R.id.etInicioT);
            etFinT = itemView.findViewById(R.id.etFinT);

        }
    }

    public ArrayList<Horario> getDatalist() {
        return datalist;
    }
}
