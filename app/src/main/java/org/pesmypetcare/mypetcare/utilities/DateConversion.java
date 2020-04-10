package org.pesmypetcare.mypetcare.utilities;

public class DateConversion {
    private static final int APP_DAY = 0;
    private static final int APP_MONTH = 1;
    private static final int APP_YEAR = 2;
    private static final int SERVER_DAY = 2;
    private static final int SERVER_MONTH = 1;
    private static final int SERVER_YEAR = 5;
    private static final int FIRST_TWO_DIGIT_NUMBER = 10;

    private enum Months {
        Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
    }

    private DateConversion() {

    }

    public static String convertToServer(String date) {
        /*String[] dateInfo = date.split(" ");
        int monthPosition = Months.valueOf(dateInfo[APP_MONTH]).ordinal() + 1;

        StringBuilder conversion = new StringBuilder(dateInfo[APP_YEAR]);
        conversion.append('-');

        if (monthPosition < FIRST_TWO_DIGIT_NUMBER) {
            conversion.append('0');
        }

        conversion.append(monthPosition).append('-').append(dateInfo[APP_DAY]);

        return conversion.toString();*/

        return "2020-04-10";
    }

    public static String convertToApp(String date) {
        /*String[] dateInfo = date.split(" ");
        StringBuilder conversion = new StringBuilder("");

        conversion.append(Integer.parseInt(dateInfo[SERVER_DAY])).append(' ').append(dateInfo[SERVER_MONTH])
            .append(' ').append(dateInfo[SERVER_YEAR]);

        return conversion.toString();*/

        return "2020-04-10";
    }
}
