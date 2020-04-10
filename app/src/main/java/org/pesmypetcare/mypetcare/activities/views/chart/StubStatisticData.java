package org.pesmypetcare.mypetcare.activities.views.chart;

import java.util.ArrayList;
import java.util.Arrays;

public class StubStatisticData extends StatisticData {
    private static final String[] xValuesStub = {
        "2020-04-1", "2020-04-2", "2020-04-3", "2020-04-4", "2020-04-5", "2020-04-6", "2020-04-7"
    };

    private static final Double[] yValuesStub = { 4.0, 7.5, 16.0, 5.0, 17.0, 11.0, 10.0 };

    public StubStatisticData() {
        super();

        setxAxisValues(new ArrayList<>(Arrays.asList(xValuesStub)));
        setyAxisValues(new ArrayList<>(Arrays.asList(yValuesStub)));
    }
}
