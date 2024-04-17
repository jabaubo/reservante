package com.jabaubo.proyecto_reservas.ui.horario;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jabaubo.proyecto_reservas.BaseDeDatos;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentHomeBinding;
import com.jabaubo.proyecto_reservas.databinding.FragmentHorarioBinding;
import com.jabaubo.proyecto_reservas.ui.home.HomeViewModel;

import java.util.ArrayList;


public class HorarioFragment extends Fragment {
    private FragmentHorarioBinding binding;
    private BaseDeDatos baseDeDatos;
    //ELEMENTOS
    private Switch sLunes;
    private EditText etInicioMLunes;
    private EditText etFinMLunes;
    private EditText etInicioTLunes;
    private EditText etFinTLunes;

    private Switch sMartes;
    private EditText etInicioMMartes;
    private EditText etFinMMartes;
    private EditText etInicioTMartes;
    private EditText etFinTMartes;

    private Switch sMiercoles;
    private EditText etInicioMMiercoles;
    private EditText etFinMMiercoles;
    private EditText etInicioTMiercoles;
    private EditText etFinTMiercoles;

    private Switch sJueves;
    private EditText etInicioMJueves;
    private EditText etFinMJueves;
    private EditText etInicioTJueves;
    private EditText etFinTJueves;

    private Switch sViernes;
    private EditText etInicioMViernes;
    private EditText etFinMViernes;
    private EditText etInicioTViernes;
    private EditText etFinTViernes;

    private Switch sSabado;
    private EditText etInicioMSabado;
    private EditText etFinMSabado;
    private EditText etInicioTSabado;
    private EditText etFinTSabado;

    private Switch sDomingo;
    private EditText etInicioMDomingo;
    private EditText etFinMDomingo;
    private EditText etInicioTDomingo;
    private EditText etFinTDomingo;

    private Button btGuardarHorario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHorarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        baseDeDatos = new BaseDeDatos(this.getContext());
        sLunes = root.findViewById(R.id.sCerrado);
        etInicioMLunes = root.findViewById(R.id.etInicioM);
        etFinMLunes = root.findViewById(R.id.etFinM);
        etInicioTLunes = root.findViewById(R.id.etInicioT);
        etFinTLunes = root.findViewById(R.id.etFinT);

        sMartes = root.findViewById(R.id.sCerradoMartes);
        etInicioMMartes = root.findViewById(R.id.etInicioMMartes);
        etFinMMartes = root.findViewById(R.id.etFinMMartes);
        etInicioTMartes = root.findViewById(R.id.etInicioTMartes);
        etFinTMartes = root.findViewById(R.id.etFinTMartes);

        sMiercoles = root.findViewById(R.id.sCerradoMiercoles);
        etInicioMMiercoles = root.findViewById(R.id.etInicioMMiercoles);
        etFinMMiercoles = root.findViewById(R.id.etFinMMiercoles);
        etInicioTMiercoles = root.findViewById(R.id.etInicioTMiercoles);
        etFinTMiercoles = root.findViewById(R.id.etFinTMiercoles);

        sJueves = root.findViewById(R.id.sCerradoJueves);
        etInicioMJueves = root.findViewById(R.id.etInicioMJueves);
        etFinMJueves = root.findViewById(R.id.etFinMJueves);
        etInicioTJueves = root.findViewById(R.id.etInicioTJueves);
        etFinTJueves = root.findViewById(R.id.etFinTJueves);

        sViernes = root.findViewById(R.id.sCerradoViernes);
        etInicioMViernes = root.findViewById(R.id.etInicioMViernes);
        etFinMViernes = root.findViewById(R.id.etFinMViernes);
        etInicioTViernes = root.findViewById(R.id.etInicioTViernes);
        etFinTViernes = root.findViewById(R.id.etFinTViernes);

        sSabado = root.findViewById(R.id.sCerradoSabado);
        etInicioMSabado = root.findViewById(R.id.etInicioMSabado);
        etFinMSabado = root.findViewById(R.id.etFinMSabado);
        etInicioTSabado = root.findViewById(R.id.etInicioTSabado);
        etFinTSabado = root.findViewById(R.id.etFinTSabado);

        sDomingo = root.findViewById(R.id.sCerradoDomingo);
        etInicioMDomingo = root.findViewById(R.id.etInicioMDomingo);
        etFinMDomingo = root.findViewById(R.id.etFinMDomingo);
        etInicioTDomingo = root.findViewById(R.id.etInicioTDomingo);
        etFinTDomingo = root.findViewById(R.id.etFinTDomingo);

        btGuardarHorario = root.findViewById(R.id.btGuardarHorario);
        sLunes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickSwitchLunes();
            }
        });

        sMartes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickSwitchMartes();
            }
        });

        sMiercoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickSwitchMiercoles();
            }
        });

        sJueves.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickSwitchJueves();
            }
        });

        sViernes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickSwitchViernes();
            }
        });

        sSabado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickSwitchSabado();
            }
        });

        sDomingo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clickSwitchDomingo();
            }
        });

        etInicioMLunes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioMLunes);
                }
                return false;
            }
        });
        etFinMLunes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinMLunes);
                }
                return false;
            }
        });
        etInicioTLunes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioTLunes);
                }
                return false;
            }
        });
        etFinTLunes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinTLunes);
                }
                return false;
            }
        });

// Para el martes
        etInicioMMartes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioMMartes);
                }
                return false;
            }
        });
        etFinMMartes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinMMartes);
                }
                return false;
            }
        });
        etInicioTMartes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioTMartes);
                }
                return false;
            }
        });
        etFinTMartes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinTMartes);
                }
                return false;
            }
        });

// Para el miércoles
        etInicioMMiercoles.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioMMiercoles);
                }
                return false;
            }
        });
        etFinMMiercoles.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinMMiercoles);
                }
                return false;
            }
        });
        etInicioTMiercoles.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioTMiercoles);
                }
                return false;
            }
        });
        etFinTMiercoles.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinTMiercoles);
                }
                return false;
            }
        });
// Para el jueves
        etInicioMJueves.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioMJueves);
                }
                return false;
            }
        });
        etFinMJueves.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinMJueves);
                }
                return false;
            }
        });
        etInicioTJueves.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioTJueves);
                }
                return false;
            }
        });
        etFinTJueves.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinTJueves);
                }
                return false;
            }
        });
// Para el viernes
        etInicioMViernes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioMViernes);
                }
                return false;
            }
        });
        etFinMViernes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinMViernes);
                }
                return false;
            }
        });
        etInicioTViernes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioTViernes);
                }
                return false;
            }
        });
        etFinTViernes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinTViernes);
                }
                return false;
            }
        });
// Para el sábado
        etInicioMSabado.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioMSabado);
                }
                return false;
            }
        });
        etFinMSabado.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinMSabado);
                }
                return false;
            }
        });
        etInicioTSabado.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioTSabado);
                }
                return false;
            }
        });
        etFinTSabado.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinTSabado);
                }
                return false;
            }
        });
// Para el domingo
        etInicioMDomingo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioMDomingo);
                }
                return false;
            }
        });
        etFinMDomingo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinMDomingo);
                }
                return false;
            }
        });
        etInicioTDomingo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etInicioTDomingo);
                }
                return false;
            }
        });
        etFinTDomingo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickHoras(etFinTDomingo);
                }
                return false;
            }
        });

        btGuardarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickGuardarHorario();
            }
        });

        cargarHorario();

        return root;

    }

    public void cargarHorario(){
        String[][] horario = baseDeDatos.leerHorario();
        for (int i = 0 ; i < horario.length ; i++){

            String dia = horario[i][0];
            System.out.println("Dia " + dia + " " +  i);
            boolean abierto = (horario[i][1].equals("1"));
            String hora_inicio_m  = horario[i][2];
            String hora_fin_m  = horario[i][3];
            String hora_inicio_t  = horario[i][4];
            String hora_fin_t  = horario[i][5];
            switch (dia) {
                case "Lunes":
                    sLunes.setChecked(abierto);
                    etInicioMLunes.setText(hora_inicio_m);
                    etFinMLunes.setText(hora_fin_m);
                    etInicioTLunes.setText(hora_inicio_t);
                    etFinTLunes.setText(hora_fin_t);
                    break;
                case "Martes":
                    sMartes.setChecked(abierto);
                    etInicioMMartes.setText(hora_inicio_m);
                    etFinMMartes.setText(hora_fin_m);
                    etInicioTMartes.setText(hora_inicio_t);
                    etFinTMartes.setText(hora_fin_t);
                    break;
                case "Miércoles":
                    sMiercoles.setChecked(abierto);
                    etInicioMMiercoles.setText(hora_inicio_m);
                    etFinMMiercoles.setText(hora_fin_m);
                    etInicioTMiercoles.setText(hora_inicio_t);
                    etFinTMiercoles.setText(hora_fin_t);
                    break;
                case "Jueves":
                    sJueves.setChecked(abierto);
                    etInicioMJueves.setText(hora_inicio_m);
                    etFinMJueves.setText(hora_fin_m);
                    etInicioTJueves.setText(hora_inicio_t);
                    etFinTJueves.setText(hora_fin_t);
                    break;
                case "Viernes":
                    sViernes.setChecked(abierto);
                    etInicioMViernes.setText(hora_inicio_m);
                    etFinMViernes.setText(hora_fin_m);
                    etInicioTViernes.setText(hora_inicio_t);
                    etFinTViernes.setText(hora_fin_t);
                    break;
                case "Sábado":
                    sSabado.setChecked(abierto);
                    etInicioMSabado.setText(hora_inicio_m);
                    etFinMSabado.setText(hora_fin_m);
                    etInicioTSabado.setText(hora_inicio_t);
                    etFinTSabado.setText(hora_fin_t);
                    break;
                case "Domingo":
                    sDomingo.setChecked(abierto);
                    etInicioMDomingo.setText(hora_inicio_m);
                    etFinMDomingo.setText(hora_fin_m);
                    etInicioTDomingo.setText(hora_inicio_t);
                    etFinTDomingo.setText(hora_fin_t);
                    break;
                default:
                    // Manejo de un caso no esperado
                    break;
            }

        }
    }
    public void clickSwitchLunes(){
        if (sLunes.isChecked()){
            System.out.println("Entro en el if");
            etInicioMLunes.setEnabled(false);
            etFinMLunes.setEnabled(false);
            etInicioTLunes.setEnabled(false);
            etFinTLunes.setEnabled(false);
        }else {
            System.out.println("Entro en el else");
            etInicioMLunes.setEnabled(true);
            etFinMLunes.setEnabled(true);
            etInicioTLunes.setEnabled(true);
            etFinTLunes.setEnabled(true);
        }
    }
    public void clickSwitchMartes() {
        if (sMartes.isChecked()) {
            etInicioMMartes.setEnabled(false);
            etFinMMartes.setEnabled(false);
            etInicioTMartes.setEnabled(false);
            etFinTMartes.setEnabled(false);
        } else {
            etInicioMMartes.setEnabled(true);
            etFinMMartes.setEnabled(true);
            etInicioTMartes.setEnabled(true);
            etFinTMartes.setEnabled(true);
        }
    }
    public void clickSwitchMiercoles() {
        if (sMiercoles.isChecked()) {
            etInicioMMiercoles.setEnabled(false);
            etFinMMiercoles.setEnabled(false);
            etInicioTMiercoles.setEnabled(false);
            etFinTMiercoles.setEnabled(false);
        } else {
            etInicioMMiercoles.setEnabled(true);
            etFinMMiercoles.setEnabled(true);
            etInicioTMiercoles.setEnabled(true);
            etFinTMiercoles.setEnabled(true);
        }
    }
    public void clickSwitchJueves() {
        if (sJueves.isChecked()) {
            etInicioMJueves.setEnabled(false);
            etFinMJueves.setEnabled(false);
            etInicioTJueves.setEnabled(false);
            etFinTJueves.setEnabled(false);
        } else {
            etInicioMJueves.setEnabled(true);
            etFinMJueves.setEnabled(true);
            etInicioTJueves.setEnabled(true);
            etFinTJueves.setEnabled(true);
        }
    }
    public void clickSwitchViernes() {
        if (sViernes.isChecked()) {
            etInicioMViernes.setEnabled(false);
            etFinMViernes.setEnabled(false);
            etInicioTViernes.setEnabled(false);
            etFinTViernes.setEnabled(false);
        } else {
            etInicioMViernes.setEnabled(true);
            etFinMViernes.setEnabled(true);
            etInicioTViernes.setEnabled(true);
            etFinTViernes.setEnabled(true);
        }
    }
    public void clickSwitchSabado() {
        if (sSabado.isChecked()) {
            etInicioMSabado.setEnabled(false);
            etFinMSabado.setEnabled(false);
            etInicioTSabado.setEnabled(false);
            etFinTSabado.setEnabled(false);
        } else {
            etInicioMSabado.setEnabled(true);
            etFinMSabado.setEnabled(true);
            etInicioTSabado.setEnabled(true);
            etFinTSabado.setEnabled(true);
        }
    }
    public void clickSwitchDomingo() {
        if (sDomingo.isChecked()) {
            etInicioMDomingo.setEnabled(false);
            etFinMDomingo.setEnabled(false);
            etInicioTDomingo.setEnabled(false);
            etFinTDomingo.setEnabled(false);
        } else {
            etInicioMDomingo.setEnabled(true);
            etFinMDomingo.setEnabled(true);
            etInicioTDomingo.setEnabled(true);
            etFinTDomingo.setEnabled(true);
        }
    }

    public void clickHoras(EditText campo){
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
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

    public void clickGuardarHorario(){
        String[][] horario = new String[7][6];

        horario[0][0] = "Lunes";
        if (sLunes.isChecked()){
            horario[0][1] = "1";
        }
        else {
            horario[0][1] = "0";
        }
        horario[0][2] = etInicioMLunes.getText().toString() ;
        horario[0][3] = etFinMLunes.getText().toString();
        horario[0][4] = etInicioTLunes.getText().toString();
        horario[0][5] = etFinTLunes.getText().toString();

        horario[1][0] = "Martes";
        if (sMartes.isChecked()){
            horario[1][1] = "1";
        }
        else {
            horario[1][1] = "0";
        }
        horario[1][2] = etInicioMMartes.getText().toString();
        horario[1][3] = etFinMMartes.getText().toString();
        horario[1][4] = etInicioTMartes.getText().toString();
        horario[1][5] = etFinTMartes.getText().toString();

        horario[2][0] = "Miércoles";
        if (sMiercoles.isChecked()){
            horario[2][1] = "1";
        }
        else {
            horario[2][1] = "0";
        }
        horario[2][2] = etInicioMMiercoles.getText().toString();
        horario[2][3] = etFinMMiercoles.getText().toString();
        horario[2][4] = etInicioTMiercoles.getText().toString();
        horario[2][5] = etFinTMiercoles.getText().toString();
// Para el Jueves (horario[3])
        horario[3][0] = "Jueves";
        if (sJueves.isChecked()){
            horario[3][1] = "1";
        }
        else {
            horario[3][1] = "0";
        }
        horario[3][2] = etInicioMJueves.getText().toString();
        horario[3][3] = etFinMJueves.getText().toString();
        horario[3][4] = etInicioTJueves.getText().toString();
        horario[3][5] = etFinTJueves.getText().toString();

// Para el Viernes (horario[4])
        horario[4][0] = "Viernes";
        if (sViernes.isChecked()){
            horario[4][1] = "1";
        }
        else {
            horario[4][1] = "0";
        }
        horario[4][2] = etInicioMViernes.getText().toString();
        horario[4][3] = etFinMViernes.getText().toString();
        horario[4][4] = etInicioTViernes.getText().toString();
        horario[4][5] = etFinTViernes.getText().toString();

// Para el Sábado (horario[5])
        horario[5][0] = "Sábado";
        if (sSabado.isChecked()){
            horario[5][1] = "1";
        }
        else {
            horario[5][1] = "0";
        }
        horario[5][2] = etInicioMSabado.getText().toString();
        horario[5][3] = etFinMSabado.getText().toString();
        horario[5][4] = etInicioTSabado.getText().toString();
        horario[5][5] = etFinTSabado.getText().toString();

// Para el Domingo (horario[6])
        horario[6][0] = "Domingo";
        if (sDomingo.isChecked()){
            horario[6][1] = "1";
        }
        else {
            horario[6][1] = "0";
        }
        horario[6][2] = etInicioMDomingo.getText().toString();
        horario[6][3] = etFinMDomingo.getText().toString();
        horario[6][4] = etInicioTDomingo.getText().toString();
        horario[6][5] = etFinTDomingo.getText().toString();

        System.out.println("##########HORARIO A GUARDAR##########");
        for (String[] datos:horario){
            System.out.println(datos[0] +  " ; "
                    + datos[1] +  " ; "
                    + datos[2] +  " ; "
                    + datos[3] +  " ; "
                    + datos[4] +  " ; "
                    + datos[5] );
        }
        baseDeDatos.guardarHorario(horario);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}