package org.pesmypetcare.mypetcare.activities.views.chart.statisticdata;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Albert Pinto
 */
public class WeeklyKilocaloriesData extends StatisticData {
    private static final String UNIT = "kcal";

    public WeeklyKilocaloriesData(Pet pet) {
        Map<DateTime, Double> weights = pet.getHealthInfo().getWeeklyKiloCaloriesAverage();
        List<String> xAxisValues = new ArrayList<>();
        List<Double> yAxisValues = new ArrayList<>();

        for (Map.Entry<DateTime, Double> entry : weights.entrySet()) {
            xAxisValues.add(getDate(entry.getKey()));
            yAxisValues.add(entry.getValue());
        }

        setxAxisValues(xAxisValues);
        setyAxisValues(yAxisValues);
    }

    @Override
    public String getUnit() {
        return UNIT;
    }

    @Override
    public int getMessageIdentifier() {
        return R.string.add_not_implemented;
    }

    @Override
    public boolean getFocusableState() {
        return false;
    }
}
