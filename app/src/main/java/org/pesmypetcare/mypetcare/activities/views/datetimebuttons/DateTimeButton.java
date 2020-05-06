package org.pesmypetcare.mypetcare.activities.views.datetimebuttons;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

/**
 * @author Albert Pinto
 */
public abstract class DateTimeButton extends MaterialButton {
    private static FragmentManager fragmentManager;
    private boolean isValueChanged;
    private DateTime dateTime;

    static {
        fragmentManager = MainActivity.getApplicationFragmentManager();
    }

    public DateTimeButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        isValueChanged = false;

        getAttributes(context, attrs);
        addComponent();
    }

    /**
     * Check whether the value has changed or not.
     * @return True if the value has changed
     */
    public boolean isValueChanged() {
        return isValueChanged;
    }

    /**
     * Change the state of the value.
     * @param valueChanged The state of the value to set
     */
    public void setValueChanged(boolean valueChanged) {
        isValueChanged = valueChanged;
    }

    /**
     * Get the fragment manager.
     * @return The fragment manager
     */
    public static FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    /**
     * Get the dateTime.
     * @return The dateTime
     */
    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * Set the dateTime.
     * @param dateTime The dateTime to set
     */
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Get the attributes if existing.
     * @param context The context of the application
     * @param attrs The attributes from the XML file
     */
    protected void getAttributes(Context context, AttributeSet attrs) {
        // Default behaviour
    }

    /**
     * Add the component.
     */
    protected abstract void addComponent();

    public abstract void setButtonText(String text);
}
