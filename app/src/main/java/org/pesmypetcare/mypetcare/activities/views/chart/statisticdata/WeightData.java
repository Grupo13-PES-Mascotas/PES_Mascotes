package org.pesmypetcare.mypetcare.activities.views.chart.statisticdata;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeightData extends StatisticData {
    private static final String UNIT = "kg";

    public WeightData(Pet pet) {
        Map<DateTime, Double> weights = pet.getHealthInfo().getWeight();
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
        return R.string.add_weight;
    }

    @Override
    public boolean getFocusableState() {
        return true;
    }
}
