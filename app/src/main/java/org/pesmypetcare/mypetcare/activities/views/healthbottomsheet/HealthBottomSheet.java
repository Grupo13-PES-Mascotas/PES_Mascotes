package org.pesmypetcare.mypetcare.activities.views.healthbottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.pesmypetcare.mypetcare.R;

/**
 * @author Albert Pinto
 */
public class HealthBottomSheet extends BottomSheetDialogFragment {
    private static final int[] STATISTICS_IDENTIFIERS = {
        R.id.healthWeight, R.id.healthDailyKilocalories, R.id.healthExerciseFrequency, R.id.healthWeeklyExercise,
        R.id.healthWeeklyKilocalories, R.id.healthWashFrequency
    };

    private static final int[] STATISTICS_NAMES = {
        R.string.health_weight, R.string.health_recommended_daily_kilocalories, R.string.health_exercise_frequency,
        R.string.health_weekly_exercice, R.string.health_weekly_kilocalories_average, R.string.health_wash_frequency
    };

    private HealthBottomSheetCommunication communication;

    public HealthBottomSheet(HealthBottomSheetCommunication communication) {
        this.communication = communication;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_bottom_sheet, container, false);

        for (int actualStatistic = 0; actualStatistic < STATISTICS_IDENTIFIERS.length; ++actualStatistic) {
            int selectedStatistic = actualStatistic;
            view.findViewById(STATISTICS_IDENTIFIERS[actualStatistic]).setOnClickListener(v -> {
                communication.selectStatistic(selectedStatistic, getString(STATISTICS_NAMES[selectedStatistic]));
            });
        }

        return view;
    }
}
