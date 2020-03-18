package org.pesmypetcare.mypetcare.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.databinding.FragmentLogInBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment {
    private FirebaseAuth mAuth;

    private String email = "";
    private String password = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();

        FragmentLogInBinding binding = FragmentLogInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.loginButton.setOnClickListener(v -> {
            email = Objects.requireNonNull(binding.loginUsernameText.getText()).toString();
            password = Objects.requireNonNull(binding.loginPasswordText.getText()).toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                loginUser();
            } else {
                Toast toast1 = Toast.makeText(getActivity(), "Incorrect entry", Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
            }

        });
        return view;
    }

    /**
     * Tries to initialize the current user.
     */
    private void loginUser() {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            System.out.println(email);
            System.out.println(password);
            if (task.isSuccessful()) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            } else {
                Toast toast1 = Toast.makeText(getActivity(), "Incorrect username or password", Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
            }
        });
    }
}
