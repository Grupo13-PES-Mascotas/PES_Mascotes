package org.pesmypetcare.mypetcare.activities.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentNewPasswordBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPassword extends Fragment {
    FragmentNewPasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewPasswordBinding.inflate(getLayoutInflater());
        settingsOptionsListeners(binding);
        return binding.getRoot();
    }

    private void settingsOptionsListeners(FragmentNewPasswordBinding binding) {
        if (binding.newPasswordText.getText() == binding.confirmNewPasswordText.getText()) {
            binding.confirmButton.setOnClickListener(v -> Toast.makeText(getActivity(),
                    "Confirm button clicked", Toast.LENGTH_LONG).show());
            replaceFragment(new SettingsMenuFragment());
        } else {
            binding.confirmButton.setOnClickListener(v -> Toast.makeText(getActivity(),
                    "Confirm button clicked, but not valid", Toast.LENGTH_LONG).show());
        }
    }

    /**
     * Replaces the current fragment of the view.
     * @param fragment The new fragment to display in the activity
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(binding.getRoot().getId(), fragment);
        ft.commit();
    }
}
