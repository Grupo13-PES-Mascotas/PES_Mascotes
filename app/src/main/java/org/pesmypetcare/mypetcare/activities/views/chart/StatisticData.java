package org.pesmypetcare.mypetcare.activities.views.chart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class StatisticData {
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
}
