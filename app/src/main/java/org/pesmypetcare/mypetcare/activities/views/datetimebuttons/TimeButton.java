package org.pesmypetcare.mypetcare.activities.views.datetimebuttons;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.Calendar;

/**
 * @author Albert Pinto
 */
public class TimeButton extends DateTimeButton {
    public static final char ZERO_CHARACTER = '0';
    public static final char TIME_SEPARATOR = ':';
    private static final int FIRST_TWO_DIGITS = 10;
    private static final String DEFAULT_SECONDS = "00";
    private static final String SAMPLE_DATE = "2001-01-01";

    public TimeButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void addComponent() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute) ->
                initializeTimePickerDialog(hourOfDay, minute), hour, min, true);
            timePickerDialog.show();
        });
    }

    @Override
    public void setButtonText(String text) {
        setDateTime(DateTime.Builder.buildDateTimeString(SAMPLE_DATE, text));
        setText(text);
    }

    /**
     * Initialize the time picker dialog.
     * @param selectedHour The hour to display
     * @param selectedMin The minute to display
     */
    private void initializeTimePickerDialog(int selectedHour, int selectedMin) {
        StringBuilder time = new StringBuilder();
        if (selectedHour < FIRST_TWO_DIGITS) {
            time.append(ZERO_CHARACTER);
        }
        time.append(selectedHour).append(TIME_SEPARATOR);
        if (selectedMin < FIRST_TWO_DIGITS) {
            time.append(ZERO_CHARACTER);
        }
        time.append(selectedMin).append(TIME_SEPARATOR).append(DEFAULT_SECONDS);

        String strTime = time.toString();
        setText(strTime);
        setValueChanged(true);
        setDateTime(DateTime.Builder.buildDateTimeString(SAMPLE_DATE, strTime));
    }
}
