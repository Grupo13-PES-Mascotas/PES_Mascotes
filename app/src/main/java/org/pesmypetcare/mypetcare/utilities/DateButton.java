package org.pesmypetcare.mypetcare.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Albert Pinto
 */
public class DateButton extends MaterialButton {
    public static final String DEFAULT_TITLE_TEXT = "Title text";
    private static FragmentManager fragmentManager;
    private String titleText;
    private MaterialDatePicker materialDatePicker;
    private boolean isDateSelected;

    static {
        fragmentManager = MainActivity.getApplicationFragmentManager();
    }

    public DateButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DateButton, 0, 0);

        try {
            titleText = attributes.getString(R.styleable.DateButton_titleText);
        } finally {
            attributes.recycle();
        }

        if (titleText == null) {
            titleText = DEFAULT_TITLE_TEXT;
        }

        isDateSelected = false;

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(titleText);
        materialDatePicker = builder.build();

        setOnClickListener(v ->
            materialDatePicker.show(Objects.requireNonNull(fragmentManager), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(selection.toString()));
            String formattedDate = simpleDateFormat.format(calendar.getTime());
            setText(formattedDate);
            isDateSelected = true;
        });
    }

    public boolean isDateSelected() {
        return isDateSelected;
    }
}
