package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetBasicBinding;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import java.util.Objects;

/**
 * @author Albert Pinto
 */
public class InfoPetBasicFragment extends Fragment {
    private FragmentInfoPetBasicBinding binding;
    private boolean modified;
    private String newName;
    private String newBreed;
    private String newGender;
    private String newPathologies;
    private DateTime newBirthDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetBasicBinding.inflate(inflater, container, false);
        modified = false;

        initializePetComponents();
        //setCalendarPicker();
        setGenderDropdownMenu();
        addListeners();

        return binding.getRoot();
    }

    /**
     * Initialize pet components.
     */
    private void initializePetComponents() {
        newName = InfoPetFragment.getPet().getName();
        newBreed = InfoPetFragment.getPet().getBreed();
        newBirthDate = InfoPetFragment.getPet().getBirthDateInstance();

        Objects.requireNonNull(binding.breed.getEditText()).setText(newBreed);
        Objects.requireNonNull(binding.inputBirthMonth).setText(newBirthDate.toDateStringReverse());

        setPathologies();
        setGender();
    }

    /**
     * Get the gender of the pet.
     * @return The gender of the pet
     */
    private GenderType getGender() {
        if (newGender.equals(getString(R.string.male))) {
            return GenderType.Male;
        }

        if (newGender.equals(getString(R.string.female))) {
            return GenderType.Female;
        }

        return GenderType.Other;
    }

    /**
     * Set the gender of the pet.
     */
    private void setGender() {
        GenderType petGender = InfoPetFragment.getPet().getGender();

        if (petGender == GenderType.Male) {
            newGender = getString(R.string.male);
        } else if (petGender == GenderType.Female) {
            newGender = getString(R.string.female);
        } else {
            newGender = getString(R.string.other);
        }

        binding.inputGender.setText(newGender);
    }

    /**
     * Set the pathologies of the pet.
     */
    private void setPathologies() {
        newPathologies = InfoPetFragment.getPet().getPathologies();

        if (InfoPetFragment.getPet().getPathologies() != null) {
            Objects.requireNonNull(binding.pathologies.getEditText()).setText(newPathologies);
        }
    }

    /**
     * Configure the Dropdown menu of gender.
     */
    private void setGenderDropdownMenu() {
        AutoCompleteTextView gender = binding.inputGender;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
            R.layout.drop_down_menu_item, new String[] {getString(R.string.male), getString(R.string.female),
            getString(R.string.other)});
        gender.setAdapter(adapter);
    }

    /**
     * Configure the Calendar picker of birthday.
     */
    private void setCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.birth_random));
        MaterialDatePicker materialDatePicker = builder.build();
        binding.inputBirthMonth.setOnClickListener(v ->
            materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            String selectedDate = materialDatePicker.getHeaderText();
            binding.inputBirthMonth.setText(selectedDate);
            //newBirthDate = selectedDate;
        });
    }

    /**
     * Add the listeners to the edit texts and the buttons.
     */
    private void addListeners() {
        breedListener();
        genderListener();
        pathologiesListener();
        updateButtonListener();
        deleteButtonListener();
    }

    /**
     * Add the update button listener.
     */
    private void updateButtonListener() {
        binding.updatePet.setOnClickListener(v -> {
            if (modified) {
                modified = false;
                try {
                    updatePet();
                    //communication.changeToMainView();
                } catch (PetRepeatException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "No changes", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Update the pet.
     */
    private void updatePet() throws PetRepeatException {
        InfoPetFragment.getPet().setName(newName);
        InfoPetFragment.getPet().setGender(getGender());
        InfoPetFragment.getPet().setBreed(newBreed);

        try {
            InfoPetFragment.getCommunication().updatePet(InfoPetFragment.getPet());
        } catch (UserIsNotOwnerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add the listener for the delete button.
     */
    private void deleteButtonListener() {
        binding.deleteButton.setOnClickListener(view -> {
            MaterialAlertDialogBuilder dialogAlert = createDeleteDialogAlert();
            dialogAlert.show();
        });
    }

    /**
     * Create the dialog for deleting the pet.
     * @return The dialog for deleting the pet
     */
    private MaterialAlertDialogBuilder createDeleteDialogAlert() {
        MaterialAlertDialogBuilder dialogAlert = new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()));
        dialogAlert.setTitle(R.string.delete_pet);
        dialogAlert.setMessage(R.string.user_confirm);
        dialogAlert.setPositiveButton(R.string.affirmative_response, (dialog, which) -> deletePet());
        dialogAlert.setNegativeButton(R.string.negative_response, (dialog, which) -> dialog.cancel());

        return dialogAlert;
    }

    /**
     * Delete the pet.
     */
    private void deletePet() {
        try {
            InfoPetFragment.getCommunication().deletePet(InfoPetFragment.getPet());
            InfoPetFragment.setIsPetDeleted(true);
        } catch (UserIsNotOwnerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the listeners for the newBreed variable.
     */
    private void breedListener() {
        Objects.requireNonNull(binding.breed.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Unused
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modified = true;
                newBreed = Objects.requireNonNull(binding.breed.getEditText()).getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Unused
            }
        });
    }

    /**
     * Initialize the listeners for the newGender variable.
     */
    private void genderListener() {
        Objects.requireNonNull(binding.gender.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Unused
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modified = true;
                newGender = Objects.requireNonNull(binding.gender.getEditText()).getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Unused
            }
        });
    }

    /**
     * Initialize the listeners for the newPathologies variable.
     */
    private void pathologiesListener() {
        Objects.requireNonNull(binding.pathologies.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Unused
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modified = true;
                newPathologies = Objects.requireNonNull(binding.pathologies.getEditText()).getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Unused
            }
        });
    }
}
