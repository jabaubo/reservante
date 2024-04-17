package com.jabaubo.proyecto_reservas.ui.vacaciones;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jabaubo.proyecto_reservas.BaseDeDatos;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentHomeBinding;
import com.jabaubo.proyecto_reservas.databinding.FragmentVacacionesBinding;
import com.jabaubo.proyecto_reservas.ui.home.HomeViewModel;

import java.time.LocalDate;

public class VacacionesFragment extends Fragment {
    private FragmentVacacionesBinding binding;
    private BaseDeDatos baseDeDatos;
    private Button btFechaInicio;
    private Button btFechaFin;
    private TextView tvFechaInicio;
    private TextView tvFechaFin;
    private Button btGuardar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        baseDeDatos = new BaseDeDatos(this.getContext());
        binding = FragmentVacacionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btFechaInicio = root.findViewById(R.id.btFechaInicio);
        btFechaFin = root.findViewById(R.id.btFechaFin);
        tvFechaInicio = root.findViewById(R.id.tvFechaInicio);
        tvFechaFin = root.findViewById(R.id.tvFechaFin);
        btGuardar = root.findViewById(R.id.btGuardarVacaciones);

        btFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFecha(tvFechaInicio);
            }
        });
        tvFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFecha(tvFechaInicio);
            }
        });
        btFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFecha(tvFechaFin);
            }
        });
        tvFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFecha(tvFechaFin);
            }
        });
        btGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                guardarFecha();
            }
        });
        String[] fechas = baseDeDatos.leerVacaciones();
        tvFechaInicio.setText(fechas[0]);
        tvFechaFin.setText(fechas[1]);
        return root;

    }
    public void clickFecha(TextView tvFecha){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month < 10){
                            tvFecha.setText(String.format("%d/0%d/%d",dayOfMonth,month,year));
                        }
                        else{
                            tvFecha.setText(String.format("%d/%d/%d",dayOfMonth,month,year));
                        }

                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void guardarFecha(){
        String[] datos = {tvFechaInicio.getText().toString(),tvFechaFin.getText().toString()};
        baseDeDatos.guardarVacaciones(datos);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
