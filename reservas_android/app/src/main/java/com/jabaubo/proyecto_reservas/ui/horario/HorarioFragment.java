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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jabaubo.proyecto_reservas.BaseDeDatos;
import com.jabaubo.proyecto_reservas.Objetos.Horario;
import com.jabaubo.proyecto_reservas.Objetos.HorarioAdapter;
import com.jabaubo.proyecto_reservas.R;
import com.jabaubo.proyecto_reservas.databinding.FragmentHomeBinding;
import com.jabaubo.proyecto_reservas.databinding.FragmentHorarioBinding;
import com.jabaubo.proyecto_reservas.ui.home.HomeViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


public class HorarioFragment extends Fragment {
    private FragmentHorarioBinding binding;
    private BaseDeDatos baseDeDatos;
    //ELEMENTOS
    private RecyclerView rvHorario;
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
        JSONArray jsonArray = cargarHorarioAPI();
        ArrayList<Horario> lista = new ArrayList<>();
        for (int i = 0 ; i < jsonArray.length() ; i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String dia = jsonObject.getString("dia");
                Boolean cerrado = jsonObject.getInt("cerrado")==1;
                String hora_inicio_m = jsonObject.getString("hora_inicio_m");
                String hora_fin_m = jsonObject.getString("hora_fin_m");
                String hora_inicio_t = jsonObject.getString("hora_inicio_t");
                String hora_fin_t = jsonObject.getString("hora_fin_t");
                Horario h = new Horario(dia,cerrado,hora_inicio_m,hora_fin_m,hora_inicio_t,hora_fin_t);
                lista.add(h);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        rvHorario = root.findViewById(R.id.rvHorario);
        btGuardarHorario = root.findViewById(R.id.btGuardarHorario);
        btGuardarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarHorarioEnAPI();
            }
        });
        HorarioAdapter adapter = new HorarioAdapter(lista);
        rvHorario.setAdapter(adapter);
        rvHorario.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return root;

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
    public JSONArray cargarHorarioAPI(){
        String[] responseStr = new String[1];
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/gethorario");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
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
                        responseStr[0] = response.toString();
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
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        try {
            JSONArray jsonArray = new JSONObject(responseStr[0]).getJSONArray("horarios");
            return jsonArray;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void  guardarHorarioEnAPI(){
        ArrayList<Horario> lista = ((HorarioAdapter)rvHorario.getAdapter()).getDatalist();
        for (int i = 0 ; i < lista.size() ; i++){
            System.out.println(lista.get(i));
        }
    }
    //826
}