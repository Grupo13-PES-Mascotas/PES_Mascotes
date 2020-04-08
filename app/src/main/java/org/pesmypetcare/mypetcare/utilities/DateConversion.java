package org.pesmypetcare.mypetcare.utilities;

public class DateConversion {
    private static final int APP_DAY = 0;
    private static final int APP_MONTH = 1;
    private static final int APP_YEAR = 2;
    private static final int SERVER_DAY = 2;
    private static final int SERVER_MONTH = 1;
    private static final int SERVER_YEAR = 0;
    private static final int FIRST_TWO_DIGIT_NUMBER = 10;

    private enum Months {
        Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
    }

    private DateConversion() {

    }

    /**
     * Convert a date to the server format.
     * @param date The date that has to be converted
     * @return The date in the format of the server
     */
    public static String convertToServer(String date) {
        String[] dateInfo = date.split(" ");
        int monthPosition = Months.valueOf(dateInfo[APP_MONTH]).ordinal() + 1;

        StringBuilder conversion = new StringBuilder(dateInfo[APP_YEAR]);
        conversion.append('-');

        if (monthPosition < FIRST_TWO_DIGIT_NUMBER) {
            conversion.append('0');
        }

        conversion.append(monthPosition).append('-').append(dateInfo[APP_DAY]);

        return conversion.toString();
    }

    /**
     * Convert to the format of the app.
     * @param date The date that has to be converted
     * @return The date in the app format
     */
    public static String convertToApp(String date) {
        String[] dateInfo = date.split(" ");
        StringBuilder conversion = new StringBuilder("");

        conversion.append(Integer.parseInt(dateInfo[SERVER_DAY])).append(' ').append(dateInfo[SERVER_MONTH])
            .append(' ').append(dateInfo[SERVER_YEAR]);

        return conversion.toString();
    }

    /**
     * Get a date from a given year, month and day.
     * @param year The year of the date
     * @param month The month of the date
     * @param day The day of the date
     * @return The date in the standard format
     */
    public static String getDate(int year, int month, int day) {
        StringBuilder date = new StringBuilder("");
        date.append(year).append('-');

        if (month + 1 < DateConversion.FIRST_TWO_DIGIT_NUMBER) {
            date.append('0');
        }

        date.append(month + 1).append('-');

        if (day < DateConversion.FIRST_TWO_DIGIT_NUMBER) {
            date.append('0');
        }

        date.append(day);

        return date.toString();
    }

    /**
     * Get the date from a given dateTime.
     * @param dateTime The dateTime to obtain the date
     * @return The date of the dateTime
     */
    public static String getDate(String dateTime) {
        return dateTime.substring(0, dateTime.lastIndexOf('T'));
    }

    /**
     * Get the hour from a given dateTime.
     * @param dateTime The dateTime to obtain the hour
     * @return The hour of the dateTime
     */
    public static String getHourMinutes(String dateTime) {
        return dateTime.substring(dateTime.lastIndexOf('T') + 1, dateTime.lastIndexOf(':'));
    }
}
