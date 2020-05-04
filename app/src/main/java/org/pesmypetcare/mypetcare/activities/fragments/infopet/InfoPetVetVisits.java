package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetVetVisitsBinding;

public class InfoPetVetVisits extends Fragment {
    private FragmentInfoPetVetVisitsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetVetVisitsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
