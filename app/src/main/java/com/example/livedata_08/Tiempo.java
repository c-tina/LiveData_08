package com.example.livedata_08;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Tiempo {

    interface TiempoListener {
        void cuandoDeLaOrden(String s);
    }

    Random random = new Random();
    // Permite ejecutar una tarea en segundo plano cada cierto tiempo predefinido
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> cambiandoTiempo;
    private int tiempo = -1;

    void iniciarCambioTiempo(TiempoListener tiempoListener) {
        if (cambiandoTiempo == null || cambiandoTiempo.isCancelled()) {
            cambiandoTiempo = scheduler.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    if (tiempo < 0) {
                        tiempo = random.nextInt(3) + 3;
                    }
                    // Seleccionar un estado del tiempo aleatorio
                    String estadoClima;
                    switch (random.nextInt(4)) {
                        case 0:
                            estadoClima = "VIENTO";
                            break;
                        case 1:
                            estadoClima = "NUBES";
                            break;
                        case 2:
                            estadoClima = "LLUVIA";
                            break;
                        case 3:
                        default:
                            estadoClima = "SOL";
                            break;
                    }
                    tiempoListener.cuandoDeLaOrden(estadoClima);
                }
            }, 0, 1, SECONDS); // Intervalo fijo de 2 segundos
        }
    }

    void pararTiempo() {
        if (cambiandoTiempo != null) {
            cambiandoTiempo.cancel(true);
        }
    }

    // LiveData puede cambiar el valor de sí mismo usando setValue() postValue()
    LiveData<String> ordenLiveData = new LiveData<String>() {
        // Se ejecuta cuando hay un observador activo
        @Override
        protected void onActive() {
            super.onActive();

            iniciarCambioTiempo(new TiempoListener() {
                @Override
                public void cuandoDeLaOrden(String s) {
                    postValue(s); // Cambia el valor
                }
            });
        }
        // Se ejecuta cuando ya no hay ningún observador activo
        @Override
        protected void onInactive() {
            super.onInactive();

            pararTiempo();
        }
    };
}
