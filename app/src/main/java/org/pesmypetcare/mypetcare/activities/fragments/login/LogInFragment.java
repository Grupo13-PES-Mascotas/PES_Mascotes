package org.pesmypetcare.mypetcare.activities.fragments.login;

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
        //mAuth = FirebaseAuth.getInstance();
        mAuth = MainActivity.getmAuth();
        System.out.println("MAUTH " + mAuth.getApp().getName());
        FragmentLogInBinding binding = FragmentLogInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.loginButton.setOnClickListener(v -> {
            email = Objects.requireNonNull(binding.loginEmailText.getText()).toString();
            password = Objects.requireNonNull(binding.loginPasswordText.getText()).toString();
            if (notEmptyFields()) {
                loginUser();
            } else {
                testToast("Incorrect entry");
            }
        });
        return view;
    }

    /**
     * Verify if the fields aren't empty.
     * @return True if the fields aren't empty, false otherwise
     */
    private boolean notEmptyFields() {
        return !email.isEmpty() && !password.isEmpty();
    }

    /**
     * Creates a new toast.
     * @param s The toast content
     */
    private void testToast(String s) {
        Toast toast1 = Toast.makeText(getActivity(), s, Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }

    /**
     * Tries to initialize the current user.
     */
    private void loginUser() {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    Objects.requireNonNull(getActivity()).finish();
                } else {
                    testToast("User not verified, check your email");
                }
            } else {
                testToast("Incorrect email or password");
            }
        });
    }
}
