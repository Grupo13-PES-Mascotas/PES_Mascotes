package org.pesmypetcare.mypetcare.activities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.LoginActivity;
import org.pesmypetcare.mypetcare.activities.communication.NewPasswordInterface;
import org.pesmypetcare.mypetcare.activities.communication.SettingsCommunication;
import org.pesmypetcare.mypetcare.databinding.FragmentSettingsMenuBinding;
import org.pesmypetcare.mypetcare.features.users.NotValidUserException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsMenuFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentSettingsMenuBinding binding;
    private FirebaseAuth mAuth;
    private SettingsCommunication communication;
    private User user;
    private String oldMail;
    private String newEmail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsMenuBinding.inflate(getLayoutInflater());
        communication = (SettingsCommunication) getActivity();
        mAuth = FirebaseAuth.getInstance();
        settingsOptionsListeners();
        user = new User("johnDoe", "johndoe@gmail.com", "123456");
        setEmail();
        changeEmail();
        return binding.getRoot();
    }

    /**
     * Initializes the listeners of the fragment.
     */
    private void settingsOptionsListeners() {
        ArrayAdapter<CharSequence> languages;
        languages = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.array.Languages, android.R.layout.simple_spinner_item);
        languages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.languageSelector.setAdapter(languages);
        binding.languageSelector.setOnItemSelectedListener(this);
        logOutListener();
        deleteAccountListener();
        binding.changePasswordButton.setOnClickListener(v -> {
            Activity thisActivity = getActivity();
            assert thisActivity != null;
            ((NewPasswordInterface) thisActivity).changeFragmentPass(new NewPassword());
        });
    }

    /**
     * Sets the existent email.
     */
    private void setEmail() {
        oldMail = user.getMail();
        Objects.requireNonNull(binding.changeEmail.getEditText()).setText(oldMail);
    }

    /**
     * Changes the email.
     */
    private void changeEmail() {
        binding.changeEmailButton.setOnClickListener(v -> {
            binding.changeEmail.addOnEditTextAttachedListener(textInputLayout -> {
                oldMail = user.getMail();
                Objects.requireNonNull(binding.changeEmail.getEditText()).setText(oldMail);
                newEmail = Objects.requireNonNull(binding.changeEmail.getEditText()).getText().toString();
                if (!(oldMail.equals(newEmail))) {
                    communication.changeMail(newEmail);
                }
            });
        });
    }


    /**
     * Initializes the listeners of the Delete Account button.
     */
    private void deleteAccountListener() {
        binding.deleteAccountButton.setOnClickListener(v -> {
            AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity()).create();
            alertDialog1.setTitle("Delete Account of the Database");
            alertDialog1.setMessage("Are you sure?");
            alertDialog1.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                try {
                    deleteAccount();
                } catch (NotValidUserException e) {
                    e.printStackTrace();
                }
            });
            alertDialog1.show();
        });
    }

    /**
     * Delete the current user of the database.
     */
    private void deleteAccount() throws NotValidUserException {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        communication.deleteUser(new User(currentUser.getUid(), currentUser.getEmail(), ""));
        currentUser.reauthenticate(EmailAuthProvider.getCredential(Objects.requireNonNull(currentUser.getEmail()),
                "password1234")).addOnCompleteListener(task -> {
                    currentUser.delete();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    Objects.requireNonNull(getActivity()).finish();
                });
    }

    /**
     * Initializes the listeners of the logOut button.
     */
    private void logOutListener() {
        binding.logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            Objects.requireNonNull(getActivity()).finish();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if (parent.getSelectedItemPosition() != 0) {
            Toast.makeText(parent.getContext(), text + " is not implemented yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Unused method for our current tab
    }
}
