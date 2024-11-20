package com.example.livedata_08;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class HeroinaViewModel extends AndroidViewModel {
    Heroina heroina;

    public HeroinaViewModel(@NonNull Application application) {
        super(application);
        heroina = new Heroina();
    }

    LiveData<Heroina.HeroinaProvider> obtenerHeroina(){
        return heroina.heroinaModeloLiveData;
    }
}
