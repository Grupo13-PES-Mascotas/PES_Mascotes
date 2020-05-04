package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.Nullable;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.datetimebuttons.DateButton;
import org.pesmypetcare.mypetcare.activities.views.datetimebuttons.TimeButton;
import org.pesmypetcare.mypetcare.activities.views.entryview.EntryView;
import org.pesmypetcare.mypetcare.activities.views.entryview.InvalidBuildParameters;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetExerciseBinding;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.Objects;

public class InfoPetExercise extends Fragment {
    private static final int MIN_SPACE_SIZE = 20;
    private static FragmentInfoPetExerciseBinding binding;
    private static Context context;
    private static String[] labels;
    private AlertDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetExerciseBinding.inflate(inflater, container, false);

        binding.addExerciseButton.setOnClickListener(v -> {
            AlertDialog dialog = createEditExerciseDialog(R.string.add_exercise_title, R.string.add_exercice_message,
                true);
            dialog.show();
        });

        labels = new String[] {
            getString(R.string.entry_view_exercise_description), getString(R.string.entry_view_exercise_date),
            getString(R.string.entry_view_exercise_start_hour), getString(R.string.entry_view_exercise_end_hour),
            getString(R.string.entry_view_exercise_duration)
        };

        context = getContext();
        showExercises();

        return binding.getRoot();
    }

    public static void showExercises() {
        binding.exerciseDisplayLayout.removeAllViews();
        for (Event event : InfoPetFragment.getPet().getEventsByClass(Exercise.class)) {
            showEvent((Exercise) event);
        }
    }

    private static void showEvent(Exercise exercise) {
        EntryView.Builder builder = new EntryView.Builder(context);
        builder.setEntryLabels(labels);

        EntryView entryView = createEntryView(exercise, builder);

        if (entryView != null) {
            binding.exerciseDisplayLayout.addView(entryView);
            Space space = createSpace();
            binding.exerciseDisplayLayout.addView(space);
        }
    }

    @Nullable
    private static EntryView createEntryView(Exercise exercise, EntryView.Builder builder) {
        String startDateTime = exercise.getDateTime().toString();
        String endDateTime = exercise.getEndTime().toString();
        String date = startDateTime.substring(0, startDateTime.indexOf('T'));

        String[] entries = new String[]{
            exercise.getDescription(), date, startDateTime.substring(startDateTime.indexOf('T') + 1),
            endDateTime.substring(endDateTime.indexOf('T') + 1),
            getMinutes(exercise.getDateTime(), exercise.getEndTime()) + " min"
        };

        builder.setEntries(entries);
        builder.setName(exercise.getName());
        EntryView entryView = null;

        try {
            entryView = builder.build();
        } catch (InvalidBuildParameters invalidBuildParameters) {
            invalidBuildParameters.printStackTrace();
        }
        return entryView;
    }

    /**
     * Method responsible for initializing the spacers.
     * @return The initialized spacer;
     */
    private static Space createSpace() {
        Space space;
        space = new Space(context);
        space.setMinimumHeight(MIN_SPACE_SIZE);
        return space;
    }

    private static int getMinutes(DateTime startDateTime, DateTime endDateTime) {
        int startMinutes = startDateTime.getHour() * 60 + startDateTime.getMinutes();
        int endMinutes = endDateTime.getHour() * 60 + endDateTime.getMinutes();

        return endMinutes - startMinutes;
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

    @Override
    public void onResume() {
        super.onResume();
        showExercises();
    }
}
