package com.example.livedata_08;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;


public class TiempoViewModel extends AndroidViewModel {
    Tiempo tiempo;

    LiveData<Integer> imagenLiveData;
    LiveData<String> textoLiveData;

    public TiempoViewModel(@NonNull Application application) {
        super(application);

        tiempo = new Tiempo();

        // Transformación 1: se observa el ordenLiveData y se transforma en imagenLiveData
        imagenLiveData = Transformations.map(tiempo.ordenLiveData, estado -> {
            switch (estado) {
                case "SOL":
                    return R.drawable.sun;
                case "NUBES":
                    return R.drawable.clouds;
                case "LLUVIA":
                    return R.drawable.rain;
                case "VIENTO":
                    return R.drawable.wind;
                default:
                    return R.drawable.sun;
            }
        });

        // Transformación 2: se observa el ordenLiveData y se transforma en textoLiveData
        textoLiveData = Transformations.map(tiempo.ordenLiveData, estado -> {
            switch (estado) {
                case "SOL":
                    return "sol";
                case "NUBES":
                    return "nubes";
                case "LLUVIA":
                    return "lluvia";
                case "VIENTO":
                    return "viento";
                default:
                    return "desconocido";
            }
        });
    }

    LiveData<Integer> obtenerImagen(){
        return imagenLiveData;
    }

    LiveData<String> obtenerTexto(){
        return textoLiveData;
    }
}
