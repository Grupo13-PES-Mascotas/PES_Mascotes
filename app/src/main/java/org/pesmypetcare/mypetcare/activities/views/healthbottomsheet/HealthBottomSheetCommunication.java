package org.pesmypetcare.mypetcare.activities.views.healthbottomsheet;

public interface HealthBottomSheetCommunication {
    /**
     * Select the statistic to display in the barchart.
     * @param statisticId The identifier of the statistic to display
     * @param statisticName The name of the statistic to display
     */
    void selectStatistic(int statisticId, String statisticName);
}
