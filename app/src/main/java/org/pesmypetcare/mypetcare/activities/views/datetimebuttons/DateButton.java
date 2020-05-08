package org.pesmypetcare.mypetcare.activities.views.datetimebuttons;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.datepicker.MaterialDatePicker;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Albert Pinto
 */
public class DateButton extends DateTimeButton {
    public static final String DEFAULT_TITLE_TEXT = "Title text";
    private String titleText;
    private MaterialDatePicker materialDatePicker;

    public DateButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }

    @Override
    protected void getAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DateButton, 0, 0);

        try {
            titleText = attributes.getString(R.styleable.DateButton_titleText);
        } finally {
            attributes.recycle();
        }

        if (titleText == null) {
            titleText = DEFAULT_TITLE_TEXT;
        }
    }

    @Override
    protected void addComponent() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(titleText);
        materialDatePicker = builder.build();

        setOnClickListener(v -> materialDatePicker.show(getFragmentManager(), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(this::addPositiveListener);
    }

    @Override
    public void setButtonText(String text) {
        setDateTime(DateTime.Builder.buildDateString(text));
        setText(text);
    }

    /**
     * Add the listener of the positive button.
     * @param selection The object selected
     */
    private void addPositiveListener(Object selection) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(selection.toString()));
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        setText(formattedDate);
        setValueChanged(true);
        setDateTime(DateTime.Builder.buildDateString(formattedDate));
    }
}
