package org.pesmypetcare.mypetcare.activities.fragments.registerpet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentMainPetInfoBinding;

public class MainPetInfoFragment extends Fragment {
    private FragmentMainPetInfoBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainPetInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
