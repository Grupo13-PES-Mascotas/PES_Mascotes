package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetMedicalProfileBinding;

public class InfoPetMedicalProfile extends Fragment {
    private FragmentInfoPetMedicalProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetMedicalProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
