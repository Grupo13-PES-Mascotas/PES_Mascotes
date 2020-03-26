package org.pesmypetcare.mypetcare.activities.fragments;

public class DateConversion {
    private static final int APP_DAY = 0;
    private static final int APP_MONTH = 1;
    private static final int APP_YEAR = 2;

    private enum Months {
        Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
    };

    public static String convertToServer(String date) {
        String[] dateInfo = date.split(" ");
        int monthPosition = Months.valueOf(dateInfo[APP_MONTH]).ordinal() + 1;

        StringBuilder conversion = new StringBuilder(dateInfo[APP_YEAR]);
        conversion.append("-");

        if (monthPosition < 10) {
            conversion.append("0");
        }

        conversion.append(monthPosition);
        conversion.append("-");
        conversion.append(dateInfo[APP_DAY]);

        return conversion.toString();
    }

    public static String convertToApp(String date) {
        String[] dateInfo = date.split(" ");
        StringBuilder conversion = new StringBuilder("");

        conversion.append(Integer.parseInt(dateInfo[2]));
        conversion.append(" ");
        conversion.append(dateInfo[1]);
        conversion.append(" ");
        conversion.append(dateInfo[5]);

        return conversion.toString();
    }
}
