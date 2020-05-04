package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.datetimebuttons.DateButton;
import org.pesmypetcare.mypetcare.activities.views.datetimebuttons.TimeButton;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetExerciseBinding;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.Objects;

public class InfoPetExercise extends Fragment {
    private FragmentInfoPetExerciseBinding binding;
    private AlertDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetExerciseBinding.inflate(inflater, container, false);

        binding.addExerciseButton.setOnClickListener(v -> {
            AlertDialog dialog = createEditExerciseDialog(R.string.add_exercise_title, R.string.add_exercice_message,
                true);
            dialog.show();
        });

        return binding.getRoot();
    }

    private AlertDialog createEditExerciseDialog(int titleId, int messageId, boolean isAdding) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        builder.setTitle(titleId);
        builder.setMessage(messageId);

        View editExerciseLayout = getLayoutInflater().inflate(R.layout.edit_exercise, null);
        builder.setView(editExerciseLayout);
        dialog = builder.create();
        TextInputLayout exerciseName = editExerciseLayout.findViewById(R.id.inputExerciseName);
        TextInputLayout exerciseDescription = editExerciseLayout.findViewById(R.id.inputExerciseDescription);
        DateButton exerciseDate = editExerciseLayout.findViewById(R.id.inputExerciseDate);
        TimeButton exerciseStartTime = editExerciseLayout.findViewById(R.id.inputExerciseStartTime);
        TimeButton exerciseEndTime = editExerciseLayout.findViewById(R.id.inputExerciseEndTime);
        MaterialButton editExerciseButton = editExerciseLayout.findViewById(R.id.editExerciseButton);
        MaterialButton deleteExerciseButton = editExerciseLayout.findViewById(R.id.deleteExerciseButton);

        if (isAdding) {
            editExerciseButton.setOnClickListener(v -> {
                addExerciseListener(exerciseName, exerciseDescription, exerciseDate, exerciseStartTime,
                    exerciseEndTime);
            });
            deleteExerciseButton.setVisibility(View.GONE);
        }

        return dialog;
    }

    private void addExerciseListener(TextInputLayout exerciseName, TextInputLayout exerciseDescription,
                                     DateButton exerciseDate, TimeButton exerciseStartTime,
                                     TimeButton exerciseEndTime) {
        String txtExerciseName = Objects.requireNonNull(exerciseName.getEditText()).getText().toString();
        boolean isValid = isValid(exerciseName, exerciseDate, exerciseStartTime, exerciseEndTime, txtExerciseName);

        if (isValid) {
            addExercise(exerciseDescription, exerciseDate, exerciseStartTime, exerciseEndTime, txtExerciseName);
        }
    }

    private void addExercise(TextInputLayout exerciseDescription, DateButton exerciseDate, TimeButton exerciseStartTime,
                             TimeButton exerciseEndTime, String txtExerciseName) {
        String txtDescription = Objects.requireNonNull(exerciseDescription.getEditText())
            .getText().toString();
        String date = exerciseDate.getText().toString();
        String startHour = exerciseStartTime.getText().toString();
        String endHour = exerciseEndTime.getText().toString();
        DateTime startExerciseDateTime = DateTime.Builder.buildDateTimeString(date, startHour);
        DateTime endExerciseDateTime = DateTime.Builder.buildDateTimeString(date, endHour);

        InfoPetFragment.getCommunication().addExercise(InfoPetFragment.getPet(), txtExerciseName,
            txtDescription, startExerciseDateTime, endExerciseDateTime);
        dialog.dismiss();
    }

    private boolean isValid(TextInputLayout exerciseName, DateButton exerciseDate, TimeButton exerciseStartTime,
                            TimeButton exerciseEndTime, String txtExerciseName) {
        boolean isValid = true;

        if ("".equals(txtExerciseName)) {
            exerciseName.setErrorEnabled(true);
            exerciseName.setError(getString(R.string.error_empty_input_field));
            isValid = false;
        }

        if (exerciseDate.getText().toString().equals(getString(R.string.exercise_date))) {
            exerciseDate.setText(R.string.error_empty_input_field);
            isValid = false;
        }

        if (exerciseStartTime.getText().toString().equals(getString(R.string.exercise_start_hour))) {
            exerciseStartTime.setText(R.string.error_empty_input_field);
            isValid = false;
        }

        if (exerciseEndTime.getText().toString().equals(getString(R.string.exercise_end_hour))) {
            exerciseEndTime.setText(R.string.error_empty_input_field);
            isValid = false;
        }
        return isValid;
    }
}
