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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.chart.BarChart;
import org.pesmypetcare.mypetcare.activities.views.chart.statisticdata.StatisticData;
import org.pesmypetcare.mypetcare.activities.views.healthbottomsheet.HealthBottomSheet;
import org.pesmypetcare.mypetcare.activities.views.healthbottomsheet.HealthBottomSheetCommunication;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetHealthBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class InfoPetHealthFragment extends Fragment implements HealthBottomSheetCommunication {
    private static final String BOTTOM_SHEET_TAG = "Bottom sheet";
    public static final int CHART_SIZE = 500;
    private FragmentInfoPetHealthBinding binding;
    private TextView statisticTitle;
    private MaterialButton btnAddNewStatistic;
    private BarChart barChart;
    private HealthBottomSheet healthBottomSheet;
    private MaterialDatePicker materialDatePicker;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetHealthBinding.inflate(inflater, container, false);
        statisticTitle = binding.statisticTitle;
        btnAddNewStatistic = binding.btnAddNewStatistic;
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
        barChart.changeStatistic(statisticId);

        StatisticData statisticData = BarChart.getStatistic(statisticId);
        btnAddNewStatistic.setText(statisticData.getMessageIdentifier());
        btnAddNewStatistic.setFocusable(statisticData.getFocusableState());

        btnAddNewStatistic.setOnClickListener(v -> {
            switch (statisticId) {
                case StatisticData.WEIGHT_STATISTIC:
                    createWeightDialog();
                    break;
                case StatisticData.WASH_FREQUENCY_STATISTIC:

                    break;
                default:
            }
        });
    }

    private void createWeightDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        dialog.setTitle(R.string.add_weight);
        dialog.setMessage(R.string.add_weight_message);

        View weightLayout = getLayoutInflater().inflate(R.layout.new_weight_dialog, null);
        dialog.setView(weightLayout);

        TextInputEditText weight = weightLayout.findViewById(R.id.addWeight);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.select_birth_date));
        materialDatePicker = builder.build();

        MaterialButton btnAddWeightDate = weightLayout.findViewById(R.id.addWeightDate);
        btnAddWeightDate.setOnClickListener(v ->
            materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(selection.toString()));
            String formattedDate = simpleDateFormat.format(calendar.getTime());
            btnAddWeightDate.setText(formattedDate);
        });

        dialog.setNegativeButton(R.string.negative_response, (dialog1, which) -> {
            dialog1.cancel();
        });
        dialog.setPositiveButton(R.string.affirmative_response, (dialog1, which) -> {
            InfoPetFragment.getCommunication().addWeightForDate(InfoPetFragment.getPet(),
                Double.parseDouble(Objects.requireNonNull(weight.getText()).toString()),
                (String) btnAddWeightDate.getText());
            dialog1.dismiss();

            barChart.updatePet(InfoPetFragment.getPet());
        });

        dialog.show();
    }
}
