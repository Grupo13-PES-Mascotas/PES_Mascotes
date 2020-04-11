package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.chart.BarChart;
import org.pesmypetcare.mypetcare.activities.views.healthbottomsheet.HealthBottomSheet;
import org.pesmypetcare.mypetcare.activities.views.healthbottomsheet.HealthBottomSheetCommunication;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetHealthBinding;

import java.util.Objects;

public class InfoPetHealthFragment extends Fragment implements HealthBottomSheetCommunication {
    private static final String BOTTOM_SHEET_TAG = "Bottom sheet";
    public static final int CHART_SIZE = 500;
    private FragmentInfoPetHealthBinding binding;
    private TextView statisticTitle;
    private BarChart barChart;
    private HealthBottomSheet healthBottomSheet;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetHealthBinding.inflate(inflater, container, false);
        statisticTitle = binding.statisticTitle;
        healthBottomSheet = new HealthBottomSheet(this);

        barChart = new BarChart(getContext(), null, InfoPetFragment.getPet());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CHART_SIZE,
                getResources().getDisplayMetrics()));
        binding.barChartLayout.addView(barChart, params);

        statisticTitle.setText(R.string.health_weight);

        binding.btnChangeStatistic.setOnClickListener(v -> {
            healthBottomSheet.show(Objects.requireNonNull(getFragmentManager()), BOTTOM_SHEET_TAG);
        });

        binding.btnNext.setOnClickListener(v -> {
            barChart.nextRegion();
        });

        binding.btnPrevious.setOnClickListener(v -> {
            barChart.previousRegion();
        });

        return binding.getRoot();
    }

    @Override
    public void selectStatistic(int statisticId, String statisticName) {
        healthBottomSheet.dismiss();
        statisticTitle.setText(statisticName);
        barChart.changeStatistic(0);
    }
}
