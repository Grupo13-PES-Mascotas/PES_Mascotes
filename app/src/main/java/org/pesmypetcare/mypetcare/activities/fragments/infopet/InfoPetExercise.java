package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetExerciseBinding;

import java.util.Objects;

public class InfoPetExercise extends Fragment {
    private FragmentInfoPetExerciseBinding binding;

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
        AlertDialog dialog = builder.create();
        TextInputEditText exerciseName = editExerciseLayout.findViewById(R.id.inputExerciseName);
        TextInputEditText exerciseDescription = editExerciseLayout.findViewById(R.id.inputExerciseDescription);
        MaterialButton editExerciseButton = editExerciseLayout.findViewById(R.id.editExerciseButton);
        MaterialButton deleteExerciseButton = editExerciseLayout.findViewById(R.id.deleteExerciseButton);

        if (isAdding) {
            editExerciseButton.setOnClickListener(v -> {

            });
            deleteExerciseButton.setVisibility(View.GONE);
        }

        return dialog;
    }
}
