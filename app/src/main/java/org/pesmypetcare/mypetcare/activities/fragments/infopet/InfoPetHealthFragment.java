package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private static final int CHART_SIZE = 500;
    private FragmentInfoPetHealthBinding binding;
    private TextView statisticTitle;
    private MaterialButton btnAddNewStatistic;
    private static BarChart barChart;
    private HealthBottomSheet healthBottomSheet;
    private MaterialDatePicker materialDatePicker;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetHealthBinding.inflate(inflater, container, false);
        statisticTitle = binding.statisticTitle;
        btnAddNewStatistic = binding.btnAddNewStatistic;
        healthBottomSheet = new HealthBottomSheet(this);
        statisticTitle.setText(R.string.health_weight);

        createBarChart();
        addButtonsListeners();
        addBarchartListener();

        return binding.getRoot();
    }

    /**
     * Add the barchart listener.
     */
    private void addBarchartListener() {
        barChart.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int pressedBar = ((BarChart) view).getPressedBar(event.getX(), event.getY());

                if (pressedBar != -1) {
                    showStatisticDialog(pressedBar);
                }

                view.performClick();
            }
            return true;
        });
    }

    /**
     * Show the statistic dialog.
     * @param pressedBar The bar that has been pressed
     */
    private void showStatisticDialog(int pressedBar) {
        double value = barChart.getYvalueAt(pressedBar - 1);
        int statisticId = barChart.getSelectedStatistic();

        switch (statisticId) {
            case StatisticData.WEIGHT_STATISTIC:
                createShowWeightDialog(barChart.getXvalueAt(pressedBar - 1), value);
                break;
            case StatisticData.WASH_FREQUENCY_STATISTIC:
                createShowWashFrequencyDialog(barChart.getXvalueAt(pressedBar - 1), (int) value);
                break;
            default:
        }
    }

    /**
     * Add the listeners to the buttons.
     */
    private void addButtonsListeners() {
        binding.btnChangeStatistic.setOnClickListener(v -> {
            healthBottomSheet.show(Objects.requireNonNull(getFragmentManager()), BOTTOM_SHEET_TAG);
        });

        binding.btnNext.setOnClickListener(v -> {
            barChart.nextRegion();
        });

        binding.btnPrevious.setOnClickListener(v -> {
            barChart.previousRegion();
        });

        btnAddNewStatistic.setOnClickListener(v -> {
            createAddWeightDialog();
        });
    }

    /**
     * Create the barchart.
     */
    private void createBarChart() {
        barChart = new BarChart(getContext(), null, InfoPetFragment.getPet());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CHART_SIZE,
                getResources().getDisplayMetrics()));
        binding.barChartLayout.addView(barChart, params);
    }

    @Override
    public void selectStatistic(int statisticId, String statisticName) {
        healthBottomSheet.dismiss();
        statisticTitle.setText(statisticName);
        barChart.changeStatistic(statisticId);

        StatisticData statisticData = BarChart.getStatistic(statisticId);
        btnAddNewStatistic.setText(statisticData.getMessageIdentifier());
        btnAddNewStatistic.setFocusable(statisticData.getFocusableState());

        addNewStatisticButtonListener(statisticId);
    }

    /**
     * Updates the bar chart.
     */
    public static void updateBarChart() {
        barChart.updatePet(InfoPetFragment.getPet());
    }

    /**
     * Add the listener for the new statistic button.
     * @param statisticId The statistic identifier
     */
    private void addNewStatisticButtonListener(int statisticId) {
        btnAddNewStatistic.setOnClickListener(v -> {
            switch (statisticId) {
                case StatisticData.WEIGHT_STATISTIC:
                    createAddWeightDialog();
                    break;
                case StatisticData.WASH_FREQUENCY_STATISTIC:
                    createAddWashFrequencyDialog();
                    break;
                default:
            }
        });
    }

    /**
     * Create the add wash frequency dialog.
     */
    private void createAddWashFrequencyDialog() {
        MaterialAlertDialogBuilder dialog = createBasicDialog(R.string.add_wash_frequency,
            R.string.add_wash_frequency_message);
        View washFrequencyLayout = addTheLayout(dialog, R.layout.new_wash_frequency_dialog);
        TextInputEditText washFrequency = washFrequencyLayout.findViewById(R.id.addWashFrequency);

        setMaterialDatePicker(R.string.add_wash_frequency_date_hint);
        MaterialButton btnAddWashFrequencyDate = getTheAddButton(washFrequencyLayout, R.id.addWashFrequencyDate);

        addTheListenerToDatePicker(btnAddWashFrequencyDate);
        addWashFrequencyPositiveButtonListener(dialog, washFrequency, btnAddWashFrequencyDate);

        dialog.show();
    }

    /**
     * Add the listener to the positive button of the add new wash frequency dialog.
     * @param dialog The dialog to add the listener to
     * @param washFrequency The wash frequency
     * @param btnAddWashFrequencyDate The button that interacts with the dialog
     */
    private void addWashFrequencyPositiveButtonListener(MaterialAlertDialogBuilder dialog,
                                                        TextInputEditText washFrequency,
                                                        MaterialButton btnAddWashFrequencyDate) {
        dialog.setPositiveButton(R.string.accept, (dialog1, which) -> {
            if (areAllFieldFilled(washFrequency, btnAddWashFrequencyDate)) {
                InfoPetFragment.getCommunication().addWashFrequencyForDate(InfoPetFragment.getPet(),
                    Integer.parseInt(Objects.requireNonNull(washFrequency.getText()).toString()),
                    (String) btnAddWashFrequencyDate.getText());
                dialog1.dismiss();

                barChart.updatePet(InfoPetFragment.getPet());
            }
        });
    }

    /**
     * Add the listener to date picker.
     * @param button The button that interacts with the diaog
     */
    private void addTheListenerToDatePicker(MaterialButton button) {
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(selection.toString()));
            String formattedDate = simpleDateFormat.format(calendar.getTime());
            button.setText(formattedDate);
        });
    }

    /**
     * Get the add button.
     * @param layout The statistic layout
     * @param id The identifier of the button
     * @return The add button
     */
    private MaterialButton getTheAddButton(View layout, int id) {
        MaterialButton btnAddWashFrequencyDate = layout.findViewById(id);
        btnAddWashFrequencyDate.setOnClickListener(v ->
            materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));
        return btnAddWashFrequencyDate;
    }

    /**
     * Set the material date picker.
     * @param titleId The identifier of the string to display as a title
     */
    private void setMaterialDatePicker(int titleId) {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(titleId));
        materialDatePicker = builder.build();
    }

    /**
     * Add the layout to the dialog.
     * @param dialog The dialog to add the layout to
     * @param layoutId The identifier of the layout
     * @return The layout with the view
     */
    private View addTheLayout(MaterialAlertDialogBuilder dialog, int layoutId) {
        View layout = getLayoutInflater().inflate(layoutId, null);
        dialog.setView(layout);
        return layout;
    }

    /**
     * Create the basic dialog.
     * @param titleId The title identifier
     * @param messageId The message identifier
     * @return The basic dialog
     */
    private MaterialAlertDialogBuilder createBasicDialog(int titleId, int messageId) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()),
            R.style.AlertDialogTheme);
        dialog.setTitle(titleId);
        dialog.setMessage(messageId);
        return dialog;
    }

    /**
     * Create the add weight dialog.
     */
    private void createAddWeightDialog() {
        MaterialAlertDialogBuilder dialog = createBasicDialog(R.string.add_weight, R.string.add_weight_message);
        View weightLayout = addTheLayout(dialog, R.layout.new_weight_dialog);
        TextInputEditText weight = weightLayout.findViewById(R.id.addWeight);

        setMaterialDatePicker(R.string.add_weight_date_hint);
        MaterialButton btnAddWeightDate = getTheAddButton(weightLayout, R.id.addWeightDate);
        addTheListenerToDatePicker(btnAddWeightDate);
        addWeightDialogPositiveButtonListener(dialog, weight, btnAddWeightDate);

        dialog.show();
    }

    /**
     * Add the listener to the positive button of the add new weight dialog.
     * @param dialog The dialog to add the listener to
     * @param weight The weight
     * @param btnAddWeightDate The button that interacts with the dialog
     */
    private void addWeightDialogPositiveButtonListener(MaterialAlertDialogBuilder dialog, TextInputEditText weight,
                                                       MaterialButton btnAddWeightDate) {
        dialog.setPositiveButton(R.string.accept, (dialog1, which) -> {
            if (areAllFieldFilled(weight, btnAddWeightDate)) {
                InfoPetFragment.getCommunication().addWeightForDate(InfoPetFragment.getPet(),
                    Double.parseDouble(Objects.requireNonNull(weight.getText()).toString()),
                    (String) btnAddWeightDate.getText());
                dialog1.dismiss();

                barChart.updatePet(InfoPetFragment.getPet());
            }
        });
    }

    /**
     * Check whether there is any empty field.
     * @param editText The edit text to check
     * @param button The button to check
     * @return True if there is any empty field
     */
    private boolean areAllFieldFilled(TextInputEditText editText, MaterialButton button) {
        return !Objects.requireNonNull(editText.getText()).toString().equals("") && !button.getText()
            .equals(getString(R.string.add_weight_date_hint));
    }

    /**
     * Create the show weight dialog.
     * @param date The date of the weight
     * @param value The weight value
     */
    private void createShowWeightDialog(String date, double value) {
        AlertDialog dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()), R.style.AlertDialogTheme)
            .create();
        dialog.setTitle(value + " kg");

        View showWeightLayout = getLayoutInflater().inflate(R.layout.show_weight_dialog, null);
        dialog.setView(showWeightLayout);

        MaterialButton btnDeleteWeightDate = showWeightLayout.findViewById(R.id.deleteWeightDate);
        btnDeleteWeightDate.setOnClickListener(v -> {
            InfoPetFragment.getCommunication().deleteWeightForDate(InfoPetFragment.getPet(), date);
            barChart.updatePet(InfoPetFragment.getPet());

            dialog.dismiss();
        });

        dialog.show();
    }

    /**
     * Create the show wash frequency dialog.
     * @param date The date of the wash frequency
     * @param value The wash frequency value
     */
    private void createShowWashFrequencyDialog(String date, int value) {
        AlertDialog dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()), R.style.AlertDialogTheme)
            .create();
        dialog.setTitle(value + " days");

        View showWashFrequencyLayout = getLayoutInflater().inflate(R.layout.show_wash_frequency_dialog, null);
        dialog.setView(showWashFrequencyLayout);

        MaterialButton btnDeleteWashFrequencyDate = showWashFrequencyLayout.findViewById(R.id.deleteWashFrequencyDate);
        btnDeleteWashFrequencyDate.setOnClickListener(v -> {
            InfoPetFragment.getCommunication().deleteWashFrequencyForDate(InfoPetFragment.getPet(), date);
            barChart.updatePet(InfoPetFragment.getPet());

            dialog.dismiss();
        });

        dialog.show();
    }
}
