package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetMedicalProfileBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;

public class InfoPetMedicalProfile extends Fragment {
    private FragmentInfoPetMedicalProfileBinding binding;
    private static final String EOL = "\n";
    private static final int FIRST_TWO_DIGITS = 10;
    private static final String DEFAULT_SECONDS = "00";
    private static final int STROKE_WIDTH = 5;
    private static final String SPACE = " ";
    private static final String DATESEPARATOR = "-";
    private static final String TIMESEPARATOR = ":";
    private static boolean editing;
    private static Wash wash;

    private Pet pet;
    private LinearLayout vaccinationDisplay;
    private Button addVaccinationButton;
    private MaterialButton vaccinationDate;
    private MaterialDatePicker materialDatePicker;
    private boolean isVaccinationDateSelected;
    private boolean updatesDate;
    private int selectedHour;
    private int selectedMin;
    private MaterialButton vaccinationTime;
    private MaterialButton editVaccinationButton;
    private TextInputEditText inputVaccinationName;
    private TextInputEditText inputVaccinationDescription;
    private MaterialButton deleteVaccinationButton;
    private AlertDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetMedicalProfileBinding.inflate(inflater, container, false);
        pet = InfoPetFragment.getPet();

        vaccinationDisplay = binding.vaccinationDisplayLayout;
        addVaccinationButton = binding.addVaccinationsButton;
        View editVaccinationLayout = prepareDialog();
        dialog = getBasicVaccinationDialog();
        dialog.setView(editVaccinationLayout);

        initializeEditVaccinationButton();
        initializeRemoveVaccinationButton();
        initializeAddVaccinationButton();

        return binding.getRoot();
    }

    /**
     * Prepare the Vaccination dialog.
     * @return The layout of the main dialog
     */
    private View prepareDialog() {
        View editVaccinationLayout = getLayoutInflater().inflate(R.layout.edit_vaccination, null);
        inputVaccinationName = editVaccinationLayout.findViewById(R.id.inputVaccinationName);
        inputVaccinationDescription = editVaccinationLayout.findViewById(R.id.inputVaccinationDescription);
        editVaccinationButton = editVaccinationLayout.findViewById(R.id.editVaccinationButton);
        deleteVaccinationButton = editVaccinationLayout.findViewById(R.id.deleteVaccinationButton);
        vaccinationDate = editVaccinationLayout.findViewById(R.id.inputVaccinationDate);
        vaccinationTime = editVaccinationLayout.findViewById(R.id.inputVaccinationTime);

        setCalendarPicker();
        setTimePicker();
        return editVaccinationLayout;
    }
}
