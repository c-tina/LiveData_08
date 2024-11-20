package com.example.livedata_08;

import static java.util.concurrent.TimeUnit.SECONDS;

import android.graphics.Color;

import androidx.lifecycle.LiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Heroina {

    interface HeroinaListener {
        void onNuevaHeroina(HeroinaProvider heroinaProvider);
    }

    // Clase que nos proporciona los datos de cada heroína
    public static class HeroinaProvider {
        public final int imagen;
        public final String nombre;
        public final String videojuego;
        public final int color;

        public HeroinaProvider(int imagen, String nombre, String videojuego, int color) {
            this.imagen = imagen;
            this.nombre = nombre;
            this.videojuego = videojuego;
            this.color = color;
        }
    }

    // Llenamos en un array todas las heroínas
    private final HeroinaProvider[] heroinas = {
           new HeroinaProvider(R.drawable.jinxed, "Jinx", "League Of Legends", Color.parseColor("#C4FD39ED")),
           new HeroinaProvider(R.drawable.aloy, "Aloy", "Horizon Zero Down",Color.parseColor("#DAFF9800")),
           new HeroinaProvider(R.drawable.eiovor, "Eivor", "Assassin's Creed Valhalla",Color.parseColor("#AE3F51B5")),
           new HeroinaProvider(R.drawable.lara, "Lara Croft", "Tomb Raider",Color.parseColor("#A9502D20")),
           new HeroinaProvider(R.drawable.sylvanas, "Sylvanas", "World Of Warcraft",Color.parseColor("#9AFF0000")),
           new HeroinaProvider(R.drawable.mercyow, "Mercy", "Overwatch",Color.parseColor("#D5FFC107"))
    };

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> siguienteHeroina;

    // Cambia de heroína cada 2 segundos de manera aleatoria
    void iniciar(HeroinaListener heroinaListener) {
        if (siguienteHeroina == null || siguienteHeroina.isCancelled()) {
            siguienteHeroina = scheduler.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    int i = (int) (Math.random() * heroinas.length);
                    heroinaListener.onNuevaHeroina(heroinas[i]);
                }
            }, 0, 2, SECONDS);
        }
    }

    // Detiene el cambio
    void parar() {
        if (siguienteHeroina != null) {
            siguienteHeroina.cancel(true);
        }
    }

    LiveData<HeroinaProvider> heroinaModeloLiveData = new LiveData<HeroinaProvider>() {
        // Se ejecuta cuando hay un observador activo
        @Override
        protected void onActive() {
            super.onActive();
            iniciar(this::postValue);
        }

        // Se ejecuta cuando ya no hay ningún observador activo
        @Override
        protected void onInactive() {
            super.onInactive();
            parar();
        }
    };
}
