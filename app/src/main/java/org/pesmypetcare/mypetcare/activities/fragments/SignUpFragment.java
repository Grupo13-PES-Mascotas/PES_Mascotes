package org.pesmypetcare.mypetcare.activities.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.User;
import org.pesmypetcare.mypetcare.databinding.FragmentSignUpBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private final int MIN_PASS_LENTGH = 6;
    private TextInputEditText[] editTexts;
    private TextInputLayout[] textInputLayouts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSignUpBinding binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        editTexts = new TextInputEditText[]{binding.signUpUsernameText, binding.signUpMailText,
            binding.signUpPasswordText, binding.signUpRepPasswordText};
        textInputLayouts = new TextInputLayout[]{binding.signUpUsernameLayout, binding.signUpMailLayout,
            binding.signUpPasswordLayout, binding.signUpRepPasswordLayout};
        binding.signupButton.setOnClickListener(v ->    {
            if (validateSignUp()) {
                testToast();
            }
        });
        return view;
    }

    /**
     * Method used to test to functionality of the components of the interface
     */
    private void testToast() {
        String username = editTexts[0].getText().toString();
        String mail = editTexts[1].getText().toString();
        String passwd = editTexts[2].getText().toString();
        User test = new User(username, mail, passwd);
        Toast toast1 = Toast.makeText(getActivity(), "Username " + test.getUsername(), Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
        toast1 = Toast.makeText(getActivity(), "Mail " + test.getMail(), Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
        toast1 = Toast.makeText(getActivity(), "Password " + test.getPasswd(), Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }

    /**
     * Method responsible of checking if the sign up is correct
     * @return True if the sign up was successful or false otherwise
     */
    private boolean validateSignUp() {
        boolean returnValue = true;
        for (TextInputEditText eT:editTexts) {
            eT.setHintTextColor(getResources().getColor(R.color.colorPrimary));
            eT.setTextColor(getResources().getColor(R.color.colorPrimary));
            String text = eT.getText().toString();
            if (text.equals("")) {
                returnValue = emptyFieldHandler(eT);
            } else if (eT == editTexts[2]) {
                if (text.length() < MIN_PASS_LENTGH) {
                    returnValue = shortPassHandler(eT, "Password is too short");
                } else {
                    returnValue = checkPassStrength(returnValue, eT, text);
                }
            } else if (eT == editTexts[3]) {
                returnValue = diffPassHandler(returnValue, text);
            }
        }
        return returnValue;
    }

    private boolean diffPassHandler(boolean returnValue, String text) {
        if (!text.equals(editTexts[2].getText().toString())) {
            returnValue = false;
            editTexts[2].setText("");
            editTexts[3].setText("");
            textInputLayouts[2].setHelperText("Passwords do not match");
            textInputLayouts[2].setHelperTextColor(ColorStateList.valueOf(Color.RED));
            textInputLayouts[3].setHelperText("Passwords do not match");
            textInputLayouts[3].setHelperTextColor(ColorStateList.valueOf(Color.RED));
        }
        return returnValue;
    }

    private boolean checkPassStrength(boolean returnValue, TextInputEditText eT, String text) {
        boolean uppercase, lowercase, number, specialChar;
        uppercase = lowercase = number = specialChar = false;
        for (int i = 0; i < text.length(); ++i) {
            char aux = text.charAt(i);
            if (Character.isLowerCase(aux)) {
                lowercase = true;
            } else if (Character.isUpperCase(aux)) {
                uppercase = true;
            } else if (Character.isDigit(aux)) {
                number = true;
            } else if (String.valueOf(aux).matches("[^a-zA-Z0-9]")) {
                specialChar = true;
            }
        }
        returnValue = weakPassHandler(returnValue, eT, uppercase, lowercase, number, specialChar);
        return returnValue;
    }

    private boolean weakPassHandler(boolean returnValue, TextInputEditText eT, boolean uppercase, boolean lowercase, boolean number, boolean specialChar) {
        if (!(uppercase && lowercase && number && specialChar)) {
            eT.setTextColor(Color.RED);
            textInputLayouts[2].setHelperText("Password is too weak");
            textInputLayouts[2].setHelperTextColor(ColorStateList.valueOf(Color.RED));
            returnValue = false;
        }
        return returnValue;
    }

    private boolean shortPassHandler(TextInputEditText eT, String s) {
        eT.setTextColor(Color.RED);
        textInputLayouts[2].setHelperText(s);
        textInputLayouts[2].setHelperTextColor(ColorStateList.valueOf(Color.RED));
        return false;
    }

    private boolean emptyFieldHandler(TextInputEditText eT) {
        eT.setHintTextColor(Color.RED);
        eT.setTextColor(Color.RED);
        return false;
    }
}
