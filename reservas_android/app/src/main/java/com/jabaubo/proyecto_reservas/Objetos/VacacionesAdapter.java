package com.jabaubo.proyecto_reservas.Objetos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.ui.VacacionesDialog;
import com.jabaubo.proyecto_reservas.ui.config.ConfigFragment;

import java.util.List;

public class VacacionesAdapter extends RecyclerView.Adapter<VacacionesAdapter.MyViewHolder>{
    @NonNull
    private List<Vacaciones> datalist;
    private ConfigFragment configFragment;
    ViewGroup viewGroup;
    public VacacionesAdapter(@NonNull List<Vacaciones> datalist,ConfigFragment configFragment) {
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
                vacacionesDialog.show(configFragment.getChildFragmentManager(),null);
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
        MyViewHolder(View itemView) {
            super(itemView);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicioLayout);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFinLayout);
            tvNombre = itemView.findViewById(R.id.tvNombreVacacionesLayout);
        }
    }
}
