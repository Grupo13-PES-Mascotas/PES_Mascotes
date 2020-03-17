package org.pesmypetcare.mypetcare.activities.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.databinding.FragmentLogInBinding;
import org.pesmypetcare.mypetcare.databinding.FragmentSignUpBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentLogInBinding binding = FragmentLogInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.loginButton.setOnClickListener(v -> {
            Toast toast1 = Toast.makeText(getActivity(), "Has premut el botó", Toast.LENGTH_LONG);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        });
        return view;
    }
}
