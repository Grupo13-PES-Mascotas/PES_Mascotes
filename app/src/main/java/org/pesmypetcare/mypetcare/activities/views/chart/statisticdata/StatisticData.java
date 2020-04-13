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

    private List<String> xAxisValues;
    private List<Double> yAxisValues;

    public StatisticData() {
        xAxisValues = new ArrayList<>();
        yAxisValues = new ArrayList<>();
    }


    public List<String> getxAxisValues() {
        return xAxisValues;
    }

    public void setxAxisValues(List<String> xAxisValues) {
        this.xAxisValues = xAxisValues;
    }

    public List<Double> getyAxisValues() {
        return yAxisValues;
    }

    public void setyAxisValues(List<Double> yAxisValues) {
        this.yAxisValues = yAxisValues;
    }

    public double getyMaxValue() {
        return Collections.max(yAxisValues);
    }

    protected String getDate(DateTime dateTime) {
        int year = dateTime.getYear();
        int month = dateTime.getMonth();
        int day = dateTime.getDay();
        StringBuilder date = new StringBuilder(year);
        date.append('-');

        if (month < 10) {
            date.append('0');
        }

        date.append(month).append('-').append(day);
        return date.toString();
    }

    public abstract String getUnit();

    public abstract int getMessageIdentifier();

    public abstract boolean getFocusableState();
}
