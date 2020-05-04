package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetWashingBinding;

public class InfoPetWashing extends Fragment {
    private FragmentInfoPetWashingBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetWashingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
