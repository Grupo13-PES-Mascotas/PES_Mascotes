package org.pesmypetcare.mypetcare.activities.views.chart.statisticdata;

import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class StatisticData {
    public static final int WEIGHT_STATISTIC = 0;
    public static final int DAILY_KILOCALORIES_STATISTIC = 1;
    public static final int EXERCISE_FREQUENCY_STATISTIC = 2;
    public static final int WEEKLY_EXERCISE_STATISTIC = 3;
    public static final int WEEKLY_KILOCALORIES_STATISTIC = 4;
    public static final int WASH_FREQUENCY_STATISTIC = 5;
    public static final int STUB_STATISTIC = 6;
    private static final int OCTOBER = 10;

    private List<String> xAxisValues;
    private List<Double> yAxisValues;

    public StatisticData() {
        xAxisValues = new ArrayList<>();
        yAxisValues = new ArrayList<>();
    }

    /**
     * Get the x axis values.
     * @return The x axis values
     */
    public List<String> getxAxisValues() {
        return xAxisValues;
    }

    /**
     * Set the x axis values.
     * @param xAxisValues The x axis values to set
     */
    public void setxAxisValues(List<String> xAxisValues) {
        this.xAxisValues = xAxisValues;
    }

    /**
     * Get the y axis values.
     * @return The y axis values
     */
    public List<Double> getyAxisValues() {
        return yAxisValues;
    }

    /**
     * Set the y axis values.
     * @param yAxisValues The y axis values to set
     */
    public void setyAxisValues(List<Double> yAxisValues) {
        this.yAxisValues = yAxisValues;
    }

    /**
     * Get the y axis max value.
     * @return The max value for the y axis
     */
    public double getyMaxValue() {
        return Collections.max(yAxisValues);
    }

    /**
     * Get the date from a DateTime.
     * @param dateTime The DateTime to get the date from
     * @return The date that has been read
     */
    protected String getDate(DateTime dateTime) {
        int year = dateTime.getYear();
        int month = dateTime.getMonth();
        int day = dateTime.getDay();
        StringBuilder date = new StringBuilder(String.valueOf(year));
        date.append('-');

        if (month < OCTOBER) {
            date.append('0');
        }

        date.append(month).append('-').append(day);
        return date.toString();
    }

    /**
     * Get the unit.
     * @return The unit
     */
    public abstract String getUnit();

    /**
     * Get the message identifier.
     * @return The message identifier
     */
    public abstract int getMessageIdentifier();

    /**
     * Get the focusable state.
     * @return The focusable state
     */
    public abstract boolean getFocusableState();
}
