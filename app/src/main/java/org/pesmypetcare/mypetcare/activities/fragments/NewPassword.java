package org.pesmypetcare.mypetcare.activities.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.pesmypetcare.mypetcare.activities.NewPasswordInterface;
import org.pesmypetcare.mypetcare.databinding.FragmentNewPasswordBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPassword extends Fragment {
    private FragmentNewPasswordBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewPasswordBinding.inflate(getLayoutInflater());
        settingsOptionsListeners();
        return binding.getRoot();
    }

    /**
     * Initializes the listeners of the fragment.
     */
    private void settingsOptionsListeners() {
        binding.confirmButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(),"Not implemented yet", Toast.LENGTH_LONG).show();
            Activity thisActivity = getActivity();
            assert thisActivity != null;
            ((NewPasswordInterface) thisActivity).changeFragmentPass(new SettingsMenuFragment());
        });
    }
}
