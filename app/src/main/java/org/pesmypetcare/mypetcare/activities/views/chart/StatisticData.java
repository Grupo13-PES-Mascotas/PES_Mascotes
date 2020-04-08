package org.pesmypetcare.mypetcare.activities.views.chart;

import java.util.List;

public abstract class StatisticData {
    private List<String> xAxisValues;
    private List<Double> yAxisValues;

    public List<String> getxAxisValues() {
        return xAxisValues;
    }

    public List<Double> getyAxisValues() {
        return yAxisValues;
    }
}
