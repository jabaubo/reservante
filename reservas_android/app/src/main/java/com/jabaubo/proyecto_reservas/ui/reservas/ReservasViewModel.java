package com.jabaubo.proyecto_reservas.ui.reservas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReservasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ReservasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Reservas");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
