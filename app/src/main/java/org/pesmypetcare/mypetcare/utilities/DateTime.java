package org.pesmypetcare.mypetcare.utilities;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime implements Comparable<DateTime> {
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
    private static final int WEEK_DAYS = 7;
    private static final String FULL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DATE_TIME_SEPARATOR = "T";
    private static final int DATE = 0;
    private static final int TIME = 1;
    private static final int YEAR = 0;
    private static final int MONTH = 1;
    private static final int DAY = 2;
    private static final int HOUR = 0;
    private static final int MINUTES = 1;
    private static final int SECONDS = 2;
    private static final String DATE_SEPARATOR = "-";
    private static final String TIME_SEPARATOR = ":";
    private static final char DATE_SEPARATOR_CHAR = '-';
    private static final char ZERO_DIGIT_CHAR = '0';
    private static final char DATE_TIME_SEPARATOR_CHAR = 'T';
    private static final char TIME_SEPARATOR_CHAR = ':';

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
        String[] dateTimeParts = dateTime.split(DATE_TIME_SEPARATOR);
        readDate(dateTimeParts[DATE]);
        readTime(dateTimeParts[TIME]);
    }

    public DateTime(String dateTime, boolean isFull) {
        if (isFull) {
            String[] dateTimeParts = dateTime.split(DATE_TIME_SEPARATOR);
            readDate(dateTimeParts[DATE]);
            readTime(dateTimeParts[TIME]);
        } else {
            readDate(dateTime);
            this.hour = this.minutes = this.seconds = 0;
        }
    }

    /**
     * Read the date from string.
     * @param date The date to read
     */
    private void readDate(String date) {
        String[] dateParts = date.split(DATE_SEPARATOR);
        this.year = Integer.parseInt(dateParts[YEAR]);
        this.month = Integer.parseInt(dateParts[MONTH]);
        this.day = Integer.parseInt(dateParts[DAY]);
    }

    /**
     * Read the time from string.
     * @param time The time to read
     */
    private void readTime(String time) {
        String[] timeParts = time.split(TIME_SEPARATOR);
        this.hour = Integer.parseInt(timeParts[HOUR]);
        this.minutes = Integer.parseInt(timeParts[MINUTES]);
        this.seconds = Integer.parseInt(timeParts[SECONDS]);
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

    /**
     * Get the year.
     * @return The year
     */
    public int getYear() {
        return year;
    }

    /**
     * Set the year.
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Get the month.
     * @return The month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Set the month.
     * @param month The month to set.
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Get the day.
     * @return The day
     */
    public int getDay() {
        return day;
    }

    /**
     * Set the day.
     * @param day The day to set.
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Get the hour.
     * @return The hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Set the hour.
     * @param hour The hour to set.
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * Get the minutes.
     * @return The minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Set the minutes.
     * @param minutes The minutes to set.
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Get the seconds.
     * @return The seconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Set the seconds.
     * @param seconds The seconds to set.
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Increases a day.
     */
    public void increaseDay() {
        day += 1;
        if (day > numberOfDays(year, month)) {
            day = 1;
            month += 1;
            if (isOutOfRange(month, hour, minutes, seconds)) {
                month = 1;
                year += 1;
            }
        }
    }

    /**
     * Decreases a day.
     */
    public void decreaseDay() {
        day -= 1;
        if (day < 1) {
            month -= 1;
            day = numberOfDays(year, month);
            if (month < 1) {
                year -= 1;
                month = DECEMBER;
            }
        }
    }


    /**
     * Check whether the date time is the last week.
     * @param dateTime The datetime to check
     * @return True if it is the last week
     */
    public static boolean isLastWeek(String dateTime) {
        boolean result = false;
        DateTime dateToCheck = new DateTime(dateTime);
        DateTime currentDate = DateTime.getCurrentDate();
      
        for (int i = 0; i <= WEEK_DAYS && !result; ++i) {
            if (isSameDay(dateToCheck, currentDate)) {
                result = true;
            } else {
                currentDate.decreaseDay();
            }
        }
        return result;
    }

    /**
     * Check whether two date time take place on the same day.
     * @param dateTime The first date time to check
     * @param currentDate The second date time to check
     * @return True if the date times take place on the same day
     */
    private static boolean isSameDay(DateTime dateTime, DateTime currentDate) {
        return dateTime.getDay() == currentDate.getDay() && dateTime.getMonth() == currentDate.getMonth() &&
            dateTime.getYear() == currentDate.getYear();
    }

    /**
     * Get the current date.
     * @return The current
     */
    private static DateTime getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date date = new Date();
        String strData = dateFormat.format(date);
        return new DateTime(strData);
    }

    /**
     * Get the duration in minutes between two DateTime in the same day.
     * @param endDateTime The end DateTime
     * @return The duration in minutes between the two DateTime
     * @throws DifferentDatesException The dates are different
     * @throws PreviousEndDateException The end date is previous to the start one
     */
    public int getMinutesDuration(DateTime endDateTime) throws DifferentDatesException, PreviousEndDateException {
        if (!toDateString().equals(endDateTime.toDateString())) {
            throw new DifferentDatesException();
        } else if (this.compareTo(endDateTime) > 0) {
            throw new PreviousEndDateException();
        }
        int startMinutes = hour * MAX_MINUTES_SECONDS + minutes;
        int endMinutes = endDateTime.getHour() * MAX_MINUTES_SECONDS + endDateTime.getMinutes();

        return endMinutes - startMinutes;
    }

    /**
     * Add a second to the DateTime.
     */
    public void addSecond() {
        ++seconds;

        updateTime();
        updateDate();
    }

    /**
     * Update the date after adding one second.
     */
    private void updateDate() {
        int numDays = numberOfDays(year, month);

        if (day > numDays) {
            day = 1;
            ++month;
        }

        if (month > DECEMBER) {
            month = 1;
            ++year;
        }
    }

    /**
     * Update the time after adding one second.
     */
    private void updateTime() {
        if (seconds == MAX_MINUTES_SECONDS) {
            seconds = 0;
            ++minutes;
        }

        if (minutes == MAX_MINUTES_SECONDS) {
            minutes = 0;
            ++hour;
        }

        if (hour == MAX_HOUR) {
            hour = 0;
            ++day;
        }
    }

    /**
     * Get the date string.
     * @return The date string
     */
    public String toDateString() {
        StringBuilder date = new StringBuilder("");
        date.append(year).append(DATE_SEPARATOR_CHAR);
        if (month < FIRST_TWO_DIGITS) {
            date.append(ZERO_DIGIT_CHAR);
        }
        date.append(month).append(DATE_SEPARATOR_CHAR);
        if (day < FIRST_TWO_DIGITS) {
            date.append(ZERO_DIGIT_CHAR);
        }
        date.append(day);

        return date.toString();
    }

    /**
     * Get the date string in the reverse order.
     * @return The date string in the reverse order
     */
    public String toDateStringReverse() {
        StringBuilder date = new StringBuilder("");
        if (day < FIRST_TWO_DIGITS) {
            date.append(ZERO_DIGIT_CHAR);
        }
        date.append(day).append(DATE_SEPARATOR_CHAR);

        if (month < FIRST_TWO_DIGITS) {
            date.append(ZERO_DIGIT_CHAR);
        }
        date.append(month).append(DATE_SEPARATOR_CHAR).append(year);
        return date.toString();
    }

    /**
     * Get the time string.
     * @return The time string
     */
    public String toTimeString() {
        StringBuilder time = new StringBuilder("");
        if (hour < FIRST_TWO_DIGITS) {
            time.append(ZERO_DIGIT_CHAR);
        }
        time.append(hour).append(TIME_SEPARATOR_CHAR);
        if (minutes < FIRST_TWO_DIGITS) {
            time.append(ZERO_DIGIT_CHAR);
        }
        time.append(minutes).append(TIME_SEPARATOR_CHAR);
        if (seconds < FIRST_TWO_DIGITS) {
            time.append(ZERO_DIGIT_CHAR);
        }
        time.append(seconds);

        return time.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return toDateString() + DATE_TIME_SEPARATOR + toTimeString();
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

    public static class Builder {

        /**
         * Build a DateTime with all parameters.
         * @param year The year
         * @param month The month
         * @param day The day
         * @param hour The hour
         * @param minutes The minutes
         * @param seconds The seconds
         * @return The built DateTime
         * @throws InvalidFormatException The format is not valid
         */
        public static DateTime build(int year, int month, int day, int hour, int minutes, int seconds)
            throws InvalidFormatException {
            return new DateTime(year, month, day, hour, minutes, seconds);
        }

        /**
         * Build a DateTime with just the date.
         * @param year The year
         * @param month The month
         * @param day The day
         * @return The built DateTime with just the date
         * @throws InvalidFormatException The format is not valid
         */
        public static DateTime build(int year, int month, int day)
            throws InvalidFormatException {
            return new DateTime(year, month, day, 0, 0, 0);
        }

        /**
         * Build a DateTime with the format yyyy-MM-d'T'hh:mm:ss (apostrophes not included).
         * @param date The date with full string format
         * @return The built DateTime with full string format
         */
        public static DateTime buildFullString(String date) {
            return new DateTime(date, true);
        }

        /**
         * Build a DateTime with the format yyyy-MM-d.
         * @param date The date with date string format
         * @return The built DateTime with date string format
         */
        public static DateTime buildDateString(String date) {
            return new DateTime(date, false);
        }

        /**
         * Build a DateTime with the date in the format yyyy-MM-d and the hour with the format hh:mm:ss
         * @param date The date with the specified format
         * @param hour The hour with the specified format
         * @return The built DateTime with full string format using the two strings
         */
        public static DateTime buildDateTimeString(String date, String hour) {
            return new DateTime(date + DATE_TIME_SEPARATOR + hour, true);
        }
    }
}
