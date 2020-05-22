package org.pesmypetcare.mypetcare.activities.views.chart.statisticdata;

import org.pesmypetcare.mypetcare.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Albert Pinto
 */
public class StubStatisticData extends StatisticData {
    private static final String[] X_VALUES_STUB = {
        "2020-04-1", "2020-04-2", "2020-04-3", "2020-04-4", "2020-04-5", "2020-04-6", "2020-04-7"
    };

    private static final Double[] Y_VALUES_STUB = { 4.0, 7.5, 16.0, 5.0, 17.0, 11.0, 10.0 };
    private static final String UNIT = "unit";

    public StubStatisticData() {
        setxAxisValues(new ArrayList<>(Arrays.asList(X_VALUES_STUB)));
        setyAxisValues(new ArrayList<>(Arrays.asList(Y_VALUES_STUB)));
    }

    @Override
    public String getUnit() {
        return UNIT;
    }

    @Override
    public int getMessageIdentifier() {
        return R.string.stub_statistic;
    }

    @Override
    public boolean getFocusableState() {
        return true;
    }
}
