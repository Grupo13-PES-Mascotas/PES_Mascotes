package org.pesmypetcare.mypetcare.activities.views.chart.statisticdata;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DailyKilocaloriesData extends StatisticData {
    private static final String UNIT = "kcal";

    public DailyKilocaloriesData(Pet pet) {
        Map<DateTime, Double> weights = pet.getHealthInfo().getDailyKiloCalories();
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
        return R.string.auto_calculated;
    }

    @Override
    public boolean getFocusableState() {
        return false;
    }
}
