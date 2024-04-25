package com.jabaubo.proyecto_reservas.Objetos;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.ui.horario.HorarioFragment;

import java.util.ArrayList;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.MyViewHolder> {
    private ArrayList<Horario> datalist;
    private HorarioFragment horarioFragment;
    public HorarioAdapter(ArrayList<Horario> datalist, HorarioFragment horarioFragment) {
        this.datalist = datalist;
        this.horarioFragment = horarioFragment;
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
        holder.etInicioM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(holder.etInicioM);
                }
                return false;
            }
        });
        holder.etFinM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(holder.etFinM);
                }
                return false;
            }
        });
        holder.etInicioT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(holder.etInicioT);
                }
                return false;
            }
        });
        holder.etFinT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(holder.etFinT);
                }
                return false;
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
    public void clickHoras(EditText campo){
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(horarioFragment.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = String.valueOf(hourOfDay);
                String minuto = String.valueOf(minute);
                if (hora.length()==1){
                    hora= "0"+hora;
                }if (minuto.length()==1){
                    minuto="0"+minuto;
                }
                campo.setText(hora+":"+minuto);
            }
        },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false);
        timePickerDialog.show();
    }
    public ArrayList<Horario> getDatalist() {
        return datalist;
    }
}
