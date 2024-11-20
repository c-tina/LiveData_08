package com.example.livedata_08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.livedata_08.databinding.FragmentHeroinasBinding;

public class HeroinasFragment extends Fragment {
    private FragmentHeroinasBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentHeroinasBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HeroinaViewModel heroinaViewModel = new ViewModelProvider(this).get(HeroinaViewModel.class);

        heroinaViewModel.obtenerHeroina().observe(getViewLifecycleOwner(), new Observer<Heroina.HeroinaProvider>() {
            @Override
            public void onChanged(Heroina.HeroinaProvider heroinaProvider) {
                Glide.with(HeroinasFragment.this).load(heroinaProvider.imagen).into(binding.heroinaImg);
                binding.nombre.setText(heroinaProvider.nombre);
                binding.videojuego.setText(heroinaProvider.videojuego);
                binding.background.setBackgroundColor(heroinaProvider.color);
            }
        });
    }
}