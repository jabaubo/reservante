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
        //Rellenamos el viewHolder con los datos correspondientes
        holder.tvDia.setText(data.getDia());
        holder.sCerrado.setChecked(data.getCerrado());
        holder.etInicioM.setText(data.getHora_inicio_m());
        holder.etFinM.setText(data.getHora_fin_m());
        holder.etInicioT.setText(data.getHora_inicio_t());
        holder.etFinT.setText(data.getHora_fin_t());
        //Si está cerrado deshabilitamos los EditText
        if (data.getCerrado()) {
            holder.etInicioM.setEnabled(false);
            holder.etFinM.setEnabled(false);
            holder.etInicioT.setEnabled(false);
            holder.etFinT.setEnabled(false);
        }
        // Gestión de click en el switch
        holder.sCerrado.setOnClickListener(new View.OnClickListener() {
            @Override
            /*
             * Seleccionado -> Deshabilitamos los EditText
             * Abierto -> Habilitamos los EditText*/
            public void onClick(View v) {
                boolean cerrado = holder.sCerrado.isChecked();
                holder.etInicioM.setEnabled(!cerrado);
                holder.etFinM.setEnabled(!cerrado);
                holder.etInicioT.setEnabled(!cerrado);
                holder.etFinT.setEnabled(!cerrado);
                data.setCerrado(cerrado);
            }
        });
        //Gestión de los EditText
        holder.etInicioM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            // Comprobamos si ha sido un click
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Si ha sido click, pasamos el EditText, el objeto a cambiar y qué campo del objeto debe cambiar
                    clickHoras((EditText) v, data, "inicioM");
                    // Avisamos al adaptador de que ha cambiado el DataSet
                    horarioFragment.getRvHorario().getAdapter().notifyDataSetChanged();
                }
                return false;
            }
        });

        holder.etFinM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            // Comprobamos si ha sido un click
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Si ha sido click, pasamos el EditText, el objeto a cambiar y qué campo del objeto debe cambiar
                    clickHoras((EditText) v, data, "finM");
                    // Avisamos al adaptador de que ha cambiado el DataSet
                    horarioFragment.getRvHorario().getAdapter().notifyDataSetChanged();
                }
                return false;
            }
        });

        holder.etInicioT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            // Comprobamos si ha sido un click
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Si ha sido click, pasamos el EditText, el objeto a cambiar y qué campo del objeto debe cambiar
                    clickHoras((EditText) v, data, "inicioT");
                    // Avisamos al adaptador de que ha cambiado el DataSet
                    horarioFragment.getRvHorario().getAdapter().notifyDataSetChanged();
                }
                return false;
            }
        });

        holder.etFinT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            // Comprobamos si ha sido un click
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Si ha sido click, pasamos el EditText, el objeto a cambiar y qué campo del objeto debe cambiar
                    clickHoras((EditText) v, data, "finT");
                    // Avisamos al adaptador de que ha cambiado el DataSet
                    horarioFragment.getRvHorario().getAdapter().notifyDataSetChanged();
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
        //Componentes del view holder
        TextView tvDia;
        Switch sCerrado;
        EditText etInicioM;
        EditText etFinM;
        EditText etInicioT;
        EditText etFinT;

        MyViewHolder(View itemView) {
            super(itemView);
            //Cargamos los componentes
            tvDia = itemView.findViewById(R.id.tvHorarioDia);
            sCerrado = itemView.findViewById(R.id.sCerrado);
            etInicioM = itemView.findViewById(R.id.etInicioM);
            etFinM = itemView.findViewById(R.id.etFinM);
            etInicioT = itemView.findViewById(R.id.etInicioT);
            etFinT = itemView.findViewById(R.id.etFinT);

        }
    }

    public void clickHoras(EditText campo, Horario horario, String aCambiar) {

        final Calendar calendar = Calendar.getInstance();
        //Mostramos timePicker para seleccionar la hora
        TimePickerDialog timePickerDialog = new TimePickerDialog(horarioFragment.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = String.valueOf(hourOfDay);
                String minuto = String.valueOf(minute);
                if (hora.length() == 1) {
                    hora = "0" + hora;
                }
                if (minuto.length() == 1) {
                    minuto = "0" + minuto;
                }
                //Cambiamos el texto del campo al formato deseado
                campo.setText(hora + ":" + minuto);
                switch (aCambiar) {
                    //Depende del campo a cambiar , se modifica el objeto
                    case "inicioM":
                        horario.setHora_inicio_m(campo.getText().toString());
                        break;
                    case "finM":
                        horario.setHora_fin_m(campo.getText().toString());
                        break;
                    case "inicioT":
                        horario.setHora_inicio_t(campo.getText().toString());
                        break;
                    case "finT":
                        horario.setHora_fin_t(campo.getText().toString());
                        break;
                }
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    public ArrayList<Horario> getDatalist() {
        return datalist;
    }
}
