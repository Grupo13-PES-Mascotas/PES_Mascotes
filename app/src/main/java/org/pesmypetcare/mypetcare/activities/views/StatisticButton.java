package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

import org.pesmypetcare.mypetcare.R;

public class StatisticButton extends MaterialButton {
    public StatisticButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);

        if (focusable) {
            setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            setBackgroundColor(getResources().getColor(R.color.grey));
        }
    }
}
