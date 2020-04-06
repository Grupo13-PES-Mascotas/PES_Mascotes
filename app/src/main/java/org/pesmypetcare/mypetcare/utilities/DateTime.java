package org.pesmypetcare.mypetcare.utilities;

public class DateTime implements Comparable<DateTime> {
    public static final int HOUR_START_POS = 11;
    private static final int DAYS_30 = 30;
    private static final int DAYS_31 = 31;
    private static final int FEBRUARY = 2;
    private static final int JULY = 7;
    private static final int LEAP_YEAR_FREQ = 4;
    private static final int TWO_LAST_DIGITS = 100;
    private static final int DAYS_29 = 29;
    private static final int DAYS_28 = 28;
    private static final int DECEMBER = 12;
    private static final int MAX_HOUR = 24;
    private static final int MAX_MINUTES_SECONDS = 60;
    private static final int FIRST_TWO_DIGITS = 10;
    private static final int MONTH_START_POS = 5;
    private static final int DAY_START_POS = 8;
    private static final int HOUR_END_POS = 13;
    private static final int MIN_START_POS = 14;
    private static final int MIN_END_POS = 16;
    private static final int SEC_START_POS = 17;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes;
    private int seconds;

    public DateTime(int year, int month, int day, int hour, int minutes, int seconds) throws InvalidFormatException {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
        this.seconds = seconds;
        if (isNegative(year, month, hour, minutes, seconds) || isOutOfRange(month, hour, minutes, seconds)) {
            throw new InvalidFormatException();
        }
    }

    public DateTime(String dateTime) {
        this.year = Integer.parseInt(dateTime.substring(0, LEAP_YEAR_FREQ));
        this.month = Integer.parseInt(dateTime.substring(MONTH_START_POS, JULY));
        this.day = Integer.parseInt(dateTime.substring(DAY_START_POS, FIRST_TWO_DIGITS));
        this.hour = Integer.parseInt(dateTime.substring(HOUR_START_POS, HOUR_END_POS));
        this.minutes = Integer.parseInt(dateTime.substring(MIN_START_POS, MIN_END_POS));
        this.seconds = Integer.parseInt(dateTime.substring(SEC_START_POS));
    }


    /**
     * Method responsible for checking whether the date values are out of range or not.
     * @param month The month value that we want to check
     * @param hour The hour value that we want to check
     * @param minutes The minutes value that we want to check
     * @param seconds The seconds value that we want to check
     * @return True if any value is out of range or false otherwise
     */
    private boolean isOutOfRange(int month, int hour, int minutes, int seconds) {
        int nDays = numberOfDays(year, month);
        boolean dateOutOfRange = month > DECEMBER || day > nDays;
        boolean hourOutOfRange = hour >= MAX_HOUR || minutes >= MAX_MINUTES_SECONDS || seconds > MAX_MINUTES_SECONDS;
        return dateOutOfRange || hourOutOfRange;
    }

    /**
     * Method responsible for checking whether the date values are negative or not.
     * @param year The year value that we want to check
     * @param month The month value that we want to check
     * @param hour The hour value that we want to check
     * @param minutes The minutes value that we want to check
     * @param seconds The seconds value that we want to check
     * @return True if any value is negative or false otherwise
     */
    private boolean isNegative(int year, int month, int hour, int minutes, int seconds) {
        boolean isYearNegative = year < 0 || month < 0 || day < 0;
        boolean isDateNegative = hour < 0 || minutes < 0 || seconds < 0;
        return isYearNegative || isDateNegative;
    }

    /**
     * Method responsible for obtaining the number of days for a given month of a given year.
     * @param year The year value
     * @param month The month for which we want to obtain the number of days
     * @return The number of days of the month for that given year
     */
    private int numberOfDays(int year, int month) {
        if (month <= JULY) {
            if (month != FEBRUARY) {
                if (month % 2 == 0) {
                    return DAYS_30;
                } else {
                    return DAYS_31;
                }
            } else if (isLeapYear(year)) {
                return DAYS_29;
            } else {
                return DAYS_28;
            }
        } else if (month % 2 == 0) {
            return DAYS_31;
        }
        return DAYS_30;
    }

    /**
     * Method responsible for checking whether a year is a leap year or not.
     * @param year The year that we want to check
     * @return True if the given year is a leap year or false otherwise
     */
    private boolean isLeapYear(int year) {
        return year % LEAP_YEAR_FREQ == 0 && (year % TWO_LAST_DIGITS != 0
            || (year / TWO_LAST_DIGITS) % LEAP_YEAR_FREQ == 0);
    }

    @Override
    public String toString() {
        StringBuilder dateTime = new StringBuilder("");
        dateTime.append(year).append('-');
        if (month < FIRST_TWO_DIGITS) {
            dateTime.append('0');
        }
        dateTime.append(month).append('-');
        if (day < FIRST_TWO_DIGITS) {
            dateTime.append('0');
        }
        dateTime.append(day).append('T');
        if (hour < FIRST_TWO_DIGITS) {
            dateTime.append('0');
        }
        dateTime.append(hour).append(':');
        if (minutes < FIRST_TWO_DIGITS) {
            dateTime.append('0');
        }
        dateTime.append(minutes).append(':');
        if (seconds < FIRST_TWO_DIGITS) {
            dateTime.append('0');
        }
        dateTime.append(seconds);
        return dateTime.toString();
    }

    @Override
    public int compareTo(DateTime dateTime) {
        if (year != dateTime.year) {
            return year - dateTime.year;
        }
        if (month != dateTime.month) {
            return month - dateTime.month;
        }
        if (day != dateTime.day) {
            return day - dateTime.day;
        }
        if (hour != dateTime.hour) {
            return hour - dateTime.hour;
        }
        if (minutes != dateTime.minutes) {
            return minutes - dateTime.minutes;
        }
        return seconds - dateTime.seconds;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
