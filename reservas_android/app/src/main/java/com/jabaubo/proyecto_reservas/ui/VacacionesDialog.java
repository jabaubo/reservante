package com.jabaubo.proyecto_reservas.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jabaubo.proyecto_reservas.Objetos.Vacaciones;
import com.jabaubo.proyecto_reservas.R;

public class VacacionesDialog extends DialogFragment {
    private Vacaciones vacacion;
    private EditText etNombre;
    private TextView tvInicio;
    private TextView tvFin;

    public VacacionesDialog() {
    }
    public VacacionesDialog(Vacaciones vacacion) {
        this.vacacion = vacacion;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_dialog_vacaciones, null);
        etNombre = view.findViewById(R.id.etNombreVacaciones);
        tvInicio = view.findViewById(R.id.tvInicioVacacionesDialog);
        tvFin = view.findViewById(R.id.tvFinVacacionesDialog);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view).setPositiveButton(R.string.dialogo_guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton(R.string.dialogo_cancelar,null);
        if (vacacion != null){
            etNombre.setText(vacacion.getNombre());
            tvFin.setText(vacacion.getFin().toString());
            tvInicio.setText(vacacion.getInicio().toString());
        }
        return alertDialogBuilder.create();
    }
}
