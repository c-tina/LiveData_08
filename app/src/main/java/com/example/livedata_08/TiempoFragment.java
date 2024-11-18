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
import com.example.livedata_08.databinding.FragmentTiempoBinding;

public class TiempoFragment extends Fragment {
    private FragmentTiempoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentTiempoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TiempoViewModel entrenadorViewModel = new ViewModelProvider(this).get(TiempoViewModel.class);

        // observe() sirve para para observar un objeto de tipo LiveData o MutableLiveData -> se necesita getViewLifecycleOwner()
        // parámetros: Activity o Fragment y el callback observer
        entrenadorViewModel.obtenerImagen().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer imagen) {
                Glide.with(TiempoFragment.this).load(imagen).into(binding.tiempoImg);
            }
        });

        entrenadorViewModel.obtenerTexto().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String tiempo) {
                binding.tiempoTV.setText(tiempo);
            }
        });
    }


}