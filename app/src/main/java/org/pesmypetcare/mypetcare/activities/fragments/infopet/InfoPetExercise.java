package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.Manifest;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
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
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.List;
import java.util.Objects;

public class InfoPetExercise extends Fragment {
    private static final int MIN_SPACE_SIZE = 20;
    private static final int HOUR_IN_MINUTES = 60;
    private static final int DESCRIPTION = 0;
    private static final int DATE = 1;
    private static final int START_TIME = 2;
    private static final int END_TIME = 3;
    private static FragmentInfoPetExerciseBinding binding;
    private static Context context;
    private static LayoutInflater inflater;
    private static String[] labels;
    private static AlertDialog dialog;
    private static Resources resources;
    private static TextInputLayout exerciseName;
    private static TextInputLayout exerciseDescription;
    private static DateButton exerciseDate;
    private static TimeButton exerciseStartTime;
    private static TimeButton exerciseEndTime;
    private static MaterialButton editExerciseButton;
    private static MaterialButton deleteExerciseButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetExerciseBinding.inflate(inflater, container, false);

        binding.addExerciseButton.setOnClickListener(v -> setAddExerciseButtonListener());

        labels = new String[] {
            getString(R.string.entry_view_exercise_description), getString(R.string.entry_view_exercise_date),
            getString(R.string.entry_view_exercise_start_hour), getString(R.string.entry_view_exercise_end_hour),
            getString(R.string.entry_view_exercise_duration)
        };

        context = getContext();
        InfoPetExercise.inflater = inflater;
        resources = getResources();
        showExercises();

        binding.walkingButton.setOnClickListener(v -> {
            InfoPetFragment.getCommunication().askForPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            InfoPetFragment.getCommunication().askForPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            AlertDialog addWalkDialog = createAddWalkDialog();
            addWalkDialog.show();
        });

        return binding.getRoot();
    }

    private AlertDialog createAddWalkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle(R.string.start_walking_title);
        builder.setMessage(R.string.start_walking_message);

        View startWalkingLayout = getLayoutInflater().inflate(R.layout.start_walking, null);
        LinearLayout checkBoxLayout = startWalkingLayout.findViewById(R.id.checkboxLayout);
        builder.setView(startWalkingLayout);

        List<Pet> userPets = InfoPetFragment.getCommunication().getUserPets();

        for (Pet pet : userPets) {
            MaterialCheckBox checkBox = new MaterialCheckBox(context, null);
            checkBox.setText(pet.getName());
            checkBox.setChecked(pet.equals(InfoPetFragment.getPet()));

            checkBoxLayout.addView(checkBox);
        }

        Space space = createSpace();
        checkBoxLayout.addView(space);

        AlertDialog dialog = builder.create();

        return dialog;
    }

    /**
     * Set the add exercise button listener.
     */
    private void setAddExerciseButtonListener() {
        createEditExerciseDialog(R.string.add_exercise_title, R.string.add_exercice_message,
            true, null, null);
        dialog.show();
    }

    /**
     * Show the exercise events of the pet.
     */
    public static void showExercises() {
        binding.exerciseDisplayLayout.removeAllViews();
        for (Event event : InfoPetFragment.getPet().getEventsByClass(Exercise.class)) {
            showEvent((Exercise) event);
        }
    }

    /**
     * Show the exercise event details.
     * @param exercise The exercise to show
     */
    private static void showEvent(Exercise exercise) {
        EntryView.Builder builder = new EntryView.Builder(context);
        builder.setEntryLabels(labels);

        EntryView entryView = createEntryView(exercise, builder);

        if (entryView != null) {
            binding.exerciseDisplayLayout.addView(entryView);
            Space space = createSpace();
            binding.exerciseDisplayLayout.addView(space);
        }

        Objects.requireNonNull(entryView).setOnClickListener(v -> {
            createEditExerciseDialog(R.string.update_exercise_title, R.string.update_exercice_message, false,
                builder.getName(), builder.getEntries());
            dialog.show();
        });
    }

    /**
     * Create an entry view for the exercise.
     * @param exercise The exercise
     * @param builder The builder for the entry view
     * @return The entry view for the exercise
     */
    @Nullable
    private static EntryView createEntryView(Exercise exercise, EntryView.Builder builder) {
        String[] entries = getEntries(exercise);

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
     * Get the entries to display.
     * @param exercise The exercise
     * @return The entries to display from the exercise
     */
    @NonNull
    private static String[] getEntries(Exercise exercise) {
        String startDateTime = exercise.getDateTime().toString();
        String endDateTime = exercise.getEndTime().toString();
        String date = startDateTime.substring(0, startDateTime.indexOf('T'));

        return new String[]{
            exercise.getDescription(), date, startDateTime.substring(startDateTime.indexOf('T') + 1),
            endDateTime.substring(endDateTime.indexOf('T') + 1),
            getMinutes(exercise.getDateTime(), exercise.getEndTime()) + " min"
        };
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

    /**
     * Get the minutes difference between two dates.
     * @param startDateTime The first date
     * @param endDateTime The second date
     * @return The minutes of difference
     */
    private static int getMinutes(DateTime startDateTime, DateTime endDateTime) {
        int startMinutes = startDateTime.getHour() * HOUR_IN_MINUTES + startDateTime.getMinutes();
        int endMinutes = endDateTime.getHour() * HOUR_IN_MINUTES + endDateTime.getMinutes();

        return endMinutes - startMinutes;
    }

    /**
     * Create the dialog to edit an exercise.
     * @param titleId The title ID of the exercise
     * @param messageId The message ID of the exercise
     * @param isAdding The boolean to choose between edit or create
     * @param name The name of the exercise
     * @param entries The different entries of the exercise
     */
    private static void createEditExerciseDialog(int titleId, int messageId, boolean isAdding, String name,
                                                 String[] entries) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(context),
            R.style.AlertDialogTheme);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        initializeDialog(builder);

        if (isAdding) {
            setAddExerciseListeners();
        } else {
            setEditExerciseListeners(name, entries);
        }
    }

    /**
     * Initialize the fialog.
     * @param builder The builder of the dialog
     */
    private static void initializeDialog(AlertDialog.Builder builder) {
        View editExerciseLayout = inflater.inflate(R.layout.edit_exercise, null);
        builder.setView(editExerciseLayout);
        dialog = builder.create();
        setLayoutViews(editExerciseLayout);
        editExerciseButton = editExerciseLayout.findViewById(R.id.editExerciseButton);
        deleteExerciseButton = editExerciseLayout.findViewById(R.id.deleteExerciseButton);
    }

    /**
     * Set the edit exercise listener.
     * @param name THe name of the exercise
     * @param entries The entries of the EntryView
     */
    private static void setEditExerciseListeners(String name, String[] entries) {
        setButtons(name, entries, editExerciseButton);
        DateTime originalDateTime = DateTime.Builder.buildDateTimeString(exerciseDate.getText().toString(),
            exerciseStartTime.getText().toString());

        setEditExerciseButtonListener(editExerciseButton, originalDateTime);
        setDeleteExerciseButton(exerciseDate, exerciseStartTime, deleteExerciseButton);
    }

    /**
     * Set the add exercise listener.
     */
    private static void setAddExerciseListeners() {
        editExerciseButton.setText(R.string.add_exercise_title);
        editExerciseButton.setOnClickListener(v -> {
            addExerciseListener(exerciseName, exerciseDescription, exerciseDate, exerciseStartTime,
                    exerciseEndTime);
        });
        deleteExerciseButton.setVisibility(View.GONE);
    }

    /**
     * Set the views in the layout.
     * @param editExerciseLayout The layout
     */
    private static void setLayoutViews(View editExerciseLayout) {
        exerciseName = editExerciseLayout.findViewById(R.id.inputExerciseName);
        exerciseDescription = editExerciseLayout.findViewById(R.id.inputExerciseDescription);
        exerciseDate = editExerciseLayout.findViewById(R.id.inputExerciseDate);
        exerciseStartTime = editExerciseLayout.findViewById(R.id.inputExerciseStartTime);
        exerciseEndTime = editExerciseLayout.findViewById(R.id.inputExerciseEndTime);
    }

    /**
     * Set the different buttons of the exercise view.
     * @param name The name of the exercise
     * @param entries The entries of the exercise
     * @param editExerciseButton The button to update the exercise parameters
     */
    private static void setButtons(String name, String[] entries, MaterialButton editExerciseButton) {
        Objects.requireNonNull(exerciseName.getEditText()).setText(name);
        Objects.requireNonNull(exerciseDescription.getEditText()).setText(entries[DESCRIPTION]);
        exerciseDate.setButtonText(entries[DATE]);
        exerciseStartTime.setButtonText(entries[START_TIME]);
        exerciseEndTime.setButtonText(entries[END_TIME]);
        editExerciseButton.setText(R.string.update_exercise);
    }

    /**
     * Set the listener if the user want to update an exercise.
     * @param editExerciseButton The button to edit the exercise
     * @param originalDateTime The original date and time of the exercise
     */
    private static void setEditExerciseButtonListener(MaterialButton editExerciseButton, DateTime originalDateTime) {
        editExerciseButton.setOnClickListener(v -> {
            String txtExerciseName = Objects.requireNonNull(exerciseName.getEditText()).getText().toString();
            boolean isValid = isValid(exerciseName, exerciseDate, exerciseStartTime, exerciseEndTime,
                    txtExerciseName);
            if (isValid) {
                updateExercise(originalDateTime, txtExerciseName);
            }
        });
    }

    /**
     * Update the exercise.
     * @param originalDateTime The original DateTime
     * @param txtExerciseName The name of the exercise
     */
    private static void updateExercise(DateTime originalDateTime, String txtExerciseName) {
        String txtDescription = Objects.requireNonNull(exerciseDescription.getEditText())
                .getText().toString();
        String date = exerciseDate.getText().toString();
        String startHour = exerciseStartTime.getText().toString();
        String endHour = exerciseEndTime.getText().toString();
        DateTime startExerciseDateTime = DateTime.Builder.buildDateTimeString(date, startHour);
        DateTime endExerciseDateTime = DateTime.Builder.buildDateTimeString(date, endHour);

        InfoPetFragment.getCommunication().updateExercise(InfoPetFragment.getPet(), txtExerciseName,
                txtDescription, originalDateTime, startExerciseDateTime, endExerciseDateTime);
        dialog.dismiss();
        showExercises();
    }

    /**
     * Set the button and the listener to delete an exercise.
     * @param exerciseDate The date of the exercise to delete
     * @param exerciseStartTime The start time of the exercise to delete
     * @param deleteExerciseButton The button to delete the exercise
     */
    private static void setDeleteExerciseButton(DateButton exerciseDate, TimeButton exerciseStartTime,
                                                MaterialButton deleteExerciseButton) {
        deleteExerciseButton.setVisibility(View.VISIBLE);
        deleteExerciseButton.setOnClickListener(v -> {
            deleteExercise(exerciseDate, exerciseStartTime);
        });
    }

    /**
     * Delete an exercise.
     * @param exerciseDate The date button
     * @param exerciseStartTime The time button
     */
    private static void deleteExercise(DateButton exerciseDate, TimeButton exerciseStartTime) {
        DateTime date = exerciseDate.getDateTime();
        DateTime time = exerciseStartTime.getDateTime();
        String strDate = date.toString().substring(0, date.toString().indexOf('T'));
        String strTime = time.toString().substring(time.toString().indexOf('T') + 1);
        DateTime dateTime = DateTime.Builder.buildDateTimeString(strDate, strTime);
        InfoPetFragment.getCommunication().removeExercise(InfoPetFragment.getPet(), dateTime);
        dialog.dismiss();
        showExercises();
    }

    /**
     * The listener to add a new exercise.
     * @param exerciseName The name of the exercise
     * @param exerciseDescription The description of the exercise
     * @param exerciseDate The date of the exercise
     * @param exerciseStartTime The start time of the exercise
     * @param exerciseEndTime The end time of the exercise
     */
    private static void addExerciseListener(TextInputLayout exerciseName, TextInputLayout exerciseDescription,
                                            DateButton exerciseDate, TimeButton exerciseStartTime,
                                            TimeButton exerciseEndTime) {
        String txtExerciseName = Objects.requireNonNull(exerciseName.getEditText()).getText().toString();
        boolean isValid = isValid(exerciseName, exerciseDate, exerciseStartTime, exerciseEndTime, txtExerciseName);

        if (isValid) {
            addExercise(exerciseDescription, exerciseDate, exerciseStartTime, exerciseEndTime, txtExerciseName);
        }
    }

    /**
     * The method that add a new exercise.
     * @param exerciseDescription The description of the exercise that user want to add
     * @param exerciseDate The date of the exercise that user want to add
     * @param exerciseStartTime The start time of the exercise that user want to add
     * @param exerciseEndTime The end time of the exercise that user want to add
     * @param txtExerciseName The name of the exercise
     */
    private static void addExercise(TextInputLayout exerciseDescription, DateButton exerciseDate,
                                    TimeButton exerciseStartTime, TimeButton exerciseEndTime, String txtExerciseName) {
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

    /**
     * Determines if the exercise parameters are valid.
     * @param exerciseName The name of the exercise
     * @param exerciseDate The date of the exercise
     * @param exerciseStartTime The start time of the exercise
     * @param exerciseEndTime The end time of the exercise
     * @param txtExerciseName The name of the exercise
     * @return True if the parameters are valid
     */
    private static boolean isValid(TextInputLayout exerciseName, DateButton exerciseDate, TimeButton exerciseStartTime,
                                   TimeButton exerciseEndTime, String txtExerciseName) {
        boolean isValid = true;

        if ("".equals(txtExerciseName)) {
            exerciseName.setErrorEnabled(true);
            exerciseName.setError(resources.getString(R.string.error_empty_input_field));
            isValid = false;
        }

        if (exerciseDate.getText().toString().equals(resources.getString(R.string.exercise_date))) {
            exerciseDate.setText(R.string.error_empty_input_field);
            isValid = false;
        }

        if (exerciseStartTime.getText().toString().equals(resources.getString(R.string.exercise_start_hour))) {
            exerciseStartTime.setText(R.string.error_empty_input_field);
            isValid = false;
        }

        if (exerciseEndTime.getText().toString().equals(resources.getString(R.string.exercise_end_hour))) {
            exerciseEndTime.setText(R.string.error_empty_input_field);
            isValid = false;
        }

        if (isValid) {
            isValid = checkDateTimePeriod(exerciseDate, exerciseStartTime, exerciseEndTime);
        }

        return isValid;
    }

    /**
     * Check whether the exercise period is valid.
     * @param exerciseDate The exercise date
     * @param exerciseStartTime The exercise start time
     * @param exerciseEndTime The exercise end time
     * @return True if the period is valid
     */
    private static boolean checkDateTimePeriod(DateButton exerciseDate, TimeButton exerciseStartTime,
                                               TimeButton exerciseEndTime) {
        String strDate = getDate(exerciseDate);
        String strStartHour = getTime(exerciseStartTime);
        String strEndHour = getTime(exerciseEndTime);

        DateTime startDateTime = DateTime.Builder.buildDateTimeString(strDate, strStartHour);
        DateTime endDateTime = DateTime.Builder.buildDateTimeString(strDate, strEndHour);
        if (startDateTime.compareTo(endDateTime) > 0) {
            Toast toast = Toast.makeText(context, R.string.error_invalid_period, Toast.LENGTH_LONG);
            toast.show();

            return false;
        }

        return true;
    }

    /**
     * Get the time.
     * @param exerciseTime The time button
     * @return The time
     */
    @NonNull
    private static String getTime(TimeButton exerciseTime) {
        String strStartHour = exerciseTime.getDateTime().toString();
        strStartHour = strStartHour.substring(strStartHour.indexOf('T') + 1);
        return strStartHour;
    }

    /**
     * Get the date.
     * @param exerciseDate The date button
     * @return The date
     */
    @NonNull
    private static String getDate(DateButton exerciseDate) {
        String strDate = exerciseDate.getDateTime().toString();
        strDate = strDate.substring(0, strDate.indexOf('T'));
        return strDate;
    }

    @Override
    public void onResume() {
        super.onResume();
        showExercises();
    }
}
