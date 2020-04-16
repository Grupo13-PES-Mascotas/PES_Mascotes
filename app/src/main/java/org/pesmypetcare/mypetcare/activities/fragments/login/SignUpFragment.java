package org.pesmypetcare.mypetcare.activities.fragments.login;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.databinding.FragmentSignUpBinding;
import org.pesmypetcare.mypetcare.services.UserManagerAdapter;
import org.pesmypetcare.mypetcare.services.UserManagerService;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private final int MIN_PASS_LENTGH = 6;
    private final int PASS_POSITION = 2;
    private FragmentSignUpBinding binding;
    private TextInputEditText[] editText;
    private TextInputLayout [] inputLayout;
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private static UserManagerService userManagerService = new UserManagerAdapter();
    private String username;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        editTextAndInputLayoutDeclaration();
        binding.signupButton.setOnClickListener(v -> {
            if (validateSignUp()) {
                try {
                    userCreationAndValidation();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            resetFieldsStatus();
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso);
        binding.signupGoogleButton.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 9001);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account);
            } catch (ApiException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) throws ExecutionException, InterruptedException {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        if (!userManagerService.usernameExists(acct.getDisplayName())) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                        if (task.isSuccessful()) {
                            userManagerService.createUser(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(),
                                    acct.getDisplayName(), acct.getEmail(), "");
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            Objects.requireNonNull(getActivity()).finish();
                        }
                    });
        }
    }


    /**
     * This method declares the editText and InputLayout.
     */
    private void editTextAndInputLayoutDeclaration() {
        editText = new TextInputEditText[] {binding.signUpUsernameText,
            binding.signUpMailText, binding.signUpPasswordText, binding.signUpRepPasswordText};
        inputLayout = new TextInputLayout[] {binding.signUpUsernameLayout,
            binding.signUpMailLayout, binding.signUpPasswordLayout, binding.signUpRepPasswordLayout};
    }

    /**
     * This method is responsible for the creation and validation of the new user.
     */
    private void userCreationAndValidation() throws ExecutionException, InterruptedException {
        if (!userManagerService.usernameExists(username)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                        if (task.isSuccessful()) {
                            sendEmailVerification();
                            userManagerService.createUser(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(),
                                    username, email, password);
                            mAuth.signOut();
                        } else {
                            testToast(Objects.requireNonNull(task.getException()).toString());
                        }
                    });
        } else {
            testToast(getString(R.string.repeatedUsername));
        }
    }

    /**
     * This method is responsible for the validation of the new user.
     */
    private void sendEmailVerification() {
        Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification();
        if (mAuth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            Objects.requireNonNull(getActivity()).finish();
        }
        testToast("Verify the Email and login");
        binding.signUpUsernameText.setText("");
        binding.signUpMailText.setText("");
        binding.signUpPasswordText.setText("");
        binding.signUpRepPasswordText.setText("");
    }

    /**
     * Method responsible of checking if the sign up is correct.
     * @return True if the sign up was successful or false otherwise
     */
    private boolean validateSignUp() {
        username = Objects.requireNonNull(binding.signUpUsernameText.getText()).toString();
        email = Objects.requireNonNull(binding.signUpMailText.getText()).toString();
        password = Objects.requireNonNull(binding.signUpPasswordText.getText()).toString();
        boolean[] emptyFields = checkEmptyFields();
        if (emptyFields[PASS_POSITION]) {
            return false;
        }
        if (shortPass()) {
            return false;
        }
        if (weakPass()) {
            return false;
        }
        return !diffPass();
    }

    /**
     * Method responsible for checking which fields are empty.
     * @return Position i of the array is true if the field i is empty or false otherwise
     */
    private boolean[] checkEmptyFields() {
        boolean [] emptyFields = new boolean[editText.length];
        for (int i = 0; i < emptyFields.length; ++i) {
            if ("".equals(Objects.requireNonNull(editText[i].getText()).toString())) {
                emptyFields[i] = true;
                emptyFieldHandler(editText[i], inputLayout[i]);
            }
        }
        return emptyFields;
    }

    /**
     * Method responsible for resetting the status of the fields.
     */
    private void resetFieldsStatus() {
        for (int i = 0; i < editText.length; ++i) {
            inputLayout[i].setHelperText("");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            editText[PASS_POSITION].setTextColor(getResources().getColor(R.color.colorPrimary, null));
        } else {
            editText[PASS_POSITION].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    /**
     * Method responsible for handling the empty fields.
     * @param eT Edit Text of the empty field
     * @param iL Input Layout of the empty field
     */
    private void emptyFieldHandler(TextInputEditText eT, TextInputLayout iL) {
        iL.setHelperText(getResources().getString(R.string.emptyField));
        iL.setHelperTextColor(ColorStateList.valueOf(Color.RED));
        eT.setHintTextColor(Color.RED);
    }

    /**
     * Method responsible for checking if a password is too short.
     * @return True if the password is too short or false otherwise
     */
    private boolean shortPass() {
        if (password.length() < MIN_PASS_LENTGH) {
            testToast("Password is too short (<6)");
            weakPassHandler(binding.signUpPasswordText, binding.signUpPasswordLayout,
                getResources().getString(R.string.shortPassword));
            return true;
        }
        return false;
    }

    /**
     * Method responsible for handling weak passwords.
     * @param eT Edit Text of the password
     * @param iL Input Layout of the password
     * @param s String to set in the helper
     */
    private void weakPassHandler(TextInputEditText eT, TextInputLayout iL, String s) {
        eT.setTextColor(Color.RED);
        iL.setHelperText(s);
        iL.setHelperTextColor(ColorStateList.valueOf(Color.RED));
    }

    /**
     * Method responsible for checking whether a password is weak or not.
     * @return True if the password is weak or false otherwise
     */
    private boolean weakPass() {
        boolean uppercase = containsUppercase(password);
        boolean lowercase = containsLowercase(password);
        boolean number = containsNumber(password);
        boolean specialChar = containsSpecialChar(password);
        if (uppercase && lowercase && number && specialChar) {
            return false;
        }
        weakPassHandler(binding.signUpPasswordText, binding.signUpPasswordLayout,
            getResources().getString(R.string.weakPassword));
        return true;
    }

    /**
     * Method responsible for checking if the password contains an uppercase character.
     * @param pass The password
     * @return True if the password contains an uppercase letter or false otherwise
     */
    private boolean containsUppercase(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (Character.isUpperCase(pass.charAt(i))) {
                return true;
            }
        }
        testToast("Password doesn't contain a uppercase");
        return false;
    }

    /**
     * Method responsible for checking if the password contains an lowercase character.
     * @param pass The password
     * @return True if the password contains an lowercase letter or false otherwise
     */
    private boolean containsLowercase(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (Character.isLowerCase(pass.charAt(i))) {
                return true;
            }
        }
        testToast("Password doesn't contain a lowercase");
        return false;
    }

    /**
     * Method responsible for checking if the password contains a number.
     * @param pass The password
     * @return True if the password contains a number or false otherwise
     */
    private boolean containsNumber(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (Character.isDigit(pass.charAt(i))) {
                return true;
            }
        }
        testToast("Password doesn't contain a number");
        return false;
    }

    /**
     * Method responsible for checking if the password contains an special character.
     * @param pass The password
     * @return True if the password contains an special character or false otherwise
     */
    private boolean containsSpecialChar(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (String.valueOf(pass.charAt(i)).matches("[^a-zA-Z0-9]")) {
                return true;
            }
        }
        testToast("Password doesn't contain a special char");
        return false;
    }

    /**
     * The method responsible for checking if the passwords are different.
     * @return True if the passwords do not match or false otherwise
     */
    private boolean diffPass() {
        if (!password.equals(Objects.requireNonNull(binding.signUpRepPasswordText.getText()).toString())) {
            diffPassHandler(binding.signUpPasswordText, binding.signUpPasswordLayout,
                getResources().getString(R.string.differentPasswords));
            diffPassHandler(binding.signUpRepPasswordText, binding.signUpRepPasswordLayout,
                getResources().getString(R.string.differentPasswords));
            testToast("Passwords don't match");
            return true;
        }
        return false;
    }

    /**
     * Method responsible for handling the different passwords.
     * @param eT Edit Text of the password
     * @param iL Input Layout of the password
     * @param s String to set in the helper
     */
    private void diffPassHandler(TextInputEditText eT, TextInputLayout iL, String s) {
        eT.setText("");
        iL.setHelperText(s);
        iL.setHelperTextColor(ColorStateList.valueOf(Color.RED));
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
}
