package com.jabaubo.proyecto_reservas.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.internal.EdgeToEdgeUtils;
import com.jabaubo.proyecto_reservas.Objetos.Reserva;
import com.jabaubo.proyecto_reservas.Objetos.ReservaEnListaAdapter;
import com.jabaubo.proyecto_reservas.R;

import java.util.ArrayList;

public class BuscarDialog extends DialogFragment {
    private ArrayList<Reserva> listaOriginal;
    private int modo;
    private RecyclerView recyclerView;
    private TextView titulo ;
    private EditText etValor ;
    public static final int NOMBRE = 1;
    public static final int TELEFONO = 2;

    public BuscarDialog(ArrayList<Reserva> listaOriginal, int modo, RecyclerView recyclerView) {
        this.listaOriginal = listaOriginal;
        this.modo = modo;
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_dialogo_buscar, null);
        titulo = view.findViewById(R.id.tvBuscar);
        etValor = view.findViewById(R.id.etValor);
        //Depende del modo se buscará un campo u otro
        switch (modo){
            case 1:
                titulo.setText("Nombre");
                break;
            case 2:
                titulo.setText("Teléfono");
                break;
        }
        alertDialogBuilder.setView(view)
                .setPositiveButton(R.string.dialogo_guardar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!etValor.getText().toString().equals("")){
                            buscar();
                        }
                    }
                })
                .setNegativeButton(R.string.dialogo_cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return alertDialogBuilder.create();
    }
    public void buscar(){
        ArrayList<Reserva> listaFiltrada = new ArrayList<>();
        switch (modo){
            //1 -> Buscar por nombre similar
            case 1:
                for (int i = 0 ; i < listaOriginal.size() ; i++){
                    if (listaOriginal.get(i).getNombre_apellidos().contains(etValor.getText())){
                        listaFiltrada.add(listaOriginal.get(i));
                    }
                }
                ((ReservaEnListaAdapter)recyclerView.getAdapter()).setDataList(listaFiltrada);
                recyclerView.getAdapter().notifyDataSetChanged();
                this.dismiss();
                break;
            //1 -> Buscar por teléfono similar
            case 2:
                for (int i = 0 ; i < listaOriginal.size() ; i++){
                    if (listaOriginal.get(i).getTelefono().contains(etValor.getText())){
                        listaFiltrada.add(listaOriginal.get(i));
                    }
                }
                ((ReservaEnListaAdapter)recyclerView.getAdapter()).setDataList(listaFiltrada);
                recyclerView.getAdapter().notifyDataSetChanged();
                this.dismiss();
                break;
        }

    }
}
