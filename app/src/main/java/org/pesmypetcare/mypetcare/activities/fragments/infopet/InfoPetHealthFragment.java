package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.activities.views.HealthBottomSheet;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetHealthBinding;

import java.util.Objects;

public class InfoPetHealthFragment extends Fragment {
    private FragmentInfoPetHealthBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetHealthBinding.inflate(inflater, container, false);

        binding.testButton.setOnClickListener(v -> {
            HealthBottomSheet healthBottomSheet = new HealthBottomSheet();
            healthBottomSheet.show(Objects.requireNonNull(getFragmentManager()), "Bottom sheet");
        });

        return binding.getRoot();
    }
}
