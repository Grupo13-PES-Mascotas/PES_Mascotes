package org.pesmypetcare.mypetcare.utilities;

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
    public static final int FIRST_TWO_DIGITS = 10;
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

    private boolean isOutOfRange(int month, int hour, int minutes, int seconds) {
        int nDays = numberOfDays(year, month);
        return month > DECEMBER || hour >= MAX_HOUR ||
            minutes >= MAX_MINUTES_SECONDS || seconds > MAX_MINUTES_SECONDS || day > nDays;
    }

    private boolean isNegative(int year, int month, int hour, int minutes, int seconds) {
        return year < 0 || month < 0 || hour < 0 || minutes < 0 || seconds < 0 || day < 0;
    }

    private int numberOfDays(int year, int month) {
        if(month <= JULY) {
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

    private boolean isLeapYear(int year) {
        return year % LEAP_YEAR_FREQ == 0 && (year % TWO_LAST_DIGITS != 0 || (year / TWO_LAST_DIGITS) % LEAP_YEAR_FREQ == 0);
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

    public DateTime(String dateTime) {
        this.year = Integer.parseInt(dateTime.substring(0, LEAP_YEAR_FREQ));
        this.month = Integer.parseInt(dateTime.substring(5, 7));
        this.day = Integer.parseInt(dateTime.substring(8, FIRST_TWO_DIGITS));
        this.hour = Integer.parseInt(dateTime.substring(11, 13));
        this.minutes = Integer.parseInt(dateTime.substring(14, 16));
        this.seconds = Integer.parseInt(dateTime.substring(17));
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
