package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.activities.views.chart.BarChart;
import org.pesmypetcare.mypetcare.activities.views.healthbottomsheet.HealthBottomSheet;
import org.pesmypetcare.mypetcare.activities.views.healthbottomsheet.HealthBottomSheetCommunication;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetHealthBinding;

import java.util.Objects;

public class InfoPetHealthFragment extends Fragment implements HealthBottomSheetCommunication {
    private static final String BOTTOM_SHEET_TAG = "Bottom sheet";
    private FragmentInfoPetHealthBinding binding;
    private TextView sectionTitle;
    private BarChart barChart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetHealthBinding.inflate(inflater, container, false);

        binding.btnChangeStatistic.setOnClickListener(v -> {
            HealthBottomSheet healthBottomSheet = new HealthBottomSheet();
            healthBottomSheet.show(Objects.requireNonNull(getFragmentManager()), BOTTOM_SHEET_TAG);
        });

        barChart = binding.barChart;

        return binding.getRoot();
    }

    @Override
    public void selectStatistic(int statisticId) {
        barChart.changeStatistic(statisticId);
    }
}
