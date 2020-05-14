package org.pesmypetcare.mypetcare.utilities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDateTime {
    private DateTime dateTime;
    private DateTime dateTime2;
    private DateTime dateTime3;
    private DateTime dateTime4;
    private DateTime dateTime5;
    private DateTime dateTime6;
    private DateTime dateTime7;

    @Before
    public void setUp() throws InvalidFormatException {
        dateTime = DateTime.Builder.build(2020, 10, 23, 15, 2, 11);
        dateTime2 = DateTime.Builder.build(2030, 10, 23, 15, 2, 11);
        dateTime3 = DateTime.Builder.build(1914, 5, 22, 17, 21, 33);
        dateTime4 = DateTime.Builder.build(2020, 10, 5, 15, 2, 11);
        dateTime5 = DateTime.Builder.build(2020, 10, 23);
        dateTime6 = DateTime.Builder.build(2020, 10, 5);
        dateTime7 = DateTime.Builder.build(2020, 10, 23, 16, 2, 11);
    }

    @Test
    public void shouldBeEquals() {
        assertEquals("Should be equals", 0, dateTime.compareTo(dateTime));
    }

    @Test
    public void shouldBeNegative() {
        assertTrue("Should be negative", dateTime.compareTo(dateTime2) < 0);
    }

    @Test
    public void shouldBePositive() {
        assertTrue("Should be positive", dateTime.compareTo(dateTime3) > 0);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldYearBePositive() throws InvalidFormatException {
        DateTime.Builder.build(-2020, 10, 23, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldMonthBePositive() throws InvalidFormatException {
        DateTime.Builder.build(2020, -10, 23, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDayBePositive() throws InvalidFormatException {
        DateTime.Builder.build(2020, 10, -23, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldHourBePositive() throws InvalidFormatException {
        DateTime.Builder.build(2020, 10, 23, -15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldMinutesBePositive() throws InvalidFormatException {
        DateTime.Builder.build(2020, 10, 23, 15, -2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldSecondsBePositive() throws InvalidFormatException {
        DateTime.Builder.build(2020, 10, 23, 15, 2, -11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldMonthNotBeOutOfRange() throws InvalidFormatException {
        DateTime.Builder.build(2020, 22, 23, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldHourNotBeOutOfRange() throws InvalidFormatException {
        DateTime.Builder.build(2020, 10, 23, 28, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldMinutesNotBeOutOfRange() throws InvalidFormatException {
        DateTime.Builder.build(2020, 10, 23, 15, 82, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldSecondsNotBeOutOfRange() throws InvalidFormatException {
        DateTime.Builder.build(2020, 10, 23, 15, 2, 111);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDaysNotBeOutOfRangeNonFebruary31Days() throws InvalidFormatException {
        DateTime.Builder.build(2020, 1, 32, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDaysNotBeOutOfRangeNonFebruary30Days() throws InvalidFormatException {
        DateTime.Builder.build(2020, 4, 31, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDaysNotBeOutOfRangeFebruaryNonLeapYear() throws InvalidFormatException {
        DateTime.Builder.build(2019, 2, 29, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDaysNotBeOutOfRangeFebruaryLeapYearNotEndingIn00() throws InvalidFormatException {
        DateTime.Builder.build(2020, 2, 30, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDaysNotBeOutOfRangeFebruaryLeapYearEndingIn00() throws InvalidFormatException {
        DateTime.Builder.build(1900, 2, 29, 15, 2, 111);
    }

    @Test
    public void shouldTransformToString() {
        assertEquals("Should transform to string", "2020-10-23T15:02:11",
            dateTime.toString());
    }

    @Test
    public void shouldTransformFullStringWithTwoDigitDayToDateTime() {
        DateTime newDateTime = DateTime.Builder.buildFullString("2020-10-23T15:02:11");
        assertEquals("Should transform two digit full string to dateTime", 0, dateTime.compareTo(newDateTime));
    }

    @Test
    public void shouldTransformFullStringWithOneDigitDayToDateTime() {
        DateTime newDateTime = DateTime.Builder.buildFullString("2020-10-5T15:02:11");
        assertEquals("Should transform one digit full string to dateTime", 0, dateTime4.compareTo(newDateTime));
    }

    @Test
    public void shouldTransformDateStringWithTwoDigitDayToDateTime() {
        DateTime newDateTime = DateTime.Builder.buildDateString("2020-10-23");
        assertEquals("Should transform two digit date string to dateTime", 0, dateTime5.compareTo(newDateTime));
    }

    @Test
    public void shouldTransformDateStringWithOneDigitDayToDateTime() {
        DateTime newDateTime = DateTime.Builder.buildDateString("2020-10-5");
        assertEquals("Should transform one digit date string to dateTime", 0, dateTime6.compareTo(newDateTime));
    }

    @Test
    public void shouldTransformDateTimeStringWithTwoDigitDayToDateTime() {
        DateTime newDateTime = DateTime.Builder.buildDateTimeString("2020-10-23", "15:02:11");
        assertEquals("Should transform two digit date time string to dateTime", 0, dateTime.compareTo(newDateTime));
    }

    @Test
    public void shouldTransformDateTimeStringWithOneDigitDayToDateTime() {
        DateTime newDateTime = DateTime.Builder.buildDateTimeString("2020-10-5", "15:02:11");
        assertEquals("Should transform one digit date time string to dateTime", 0, dateTime4.compareTo(newDateTime));
    }

    @Test
    public void shouldDateCreationVersionHaveZeroAtTime() {
        boolean isTimeZero = dateTime5.getHour() == 0 && dateTime5.getMinutes() == 0 && dateTime5.getSeconds() == 0;
        assertTrue("Should date creation with version have zero at time", isTimeZero);
    }

    @Test
    public void shouldIncreaseDayNoMonthChange() throws InvalidFormatException {
        DateTime expectedDate = DateTime.Builder.build(2020, 10, 24, 15, 2, 11);
        dateTime.increaseDay();
        assertEquals("Should increase day", expectedDate.toString(), dateTime.toString());
    }

    @Test
    public void shouldIncreaseDayMonthChange() throws InvalidFormatException {
        DateTime expectedDate = DateTime.Builder.build(2020, 11, 1, 15, 2, 11);;
        dateTime.setDay(31);
        dateTime.increaseDay();
        assertEquals("Should increase day", expectedDate.toString(), dateTime.toString());
    }

    @Test
    public void shouldIncreaseDayYearChange() throws InvalidFormatException {
        DateTime expectedDate = DateTime.Builder.build(2021, 1, 1, 15, 2, 11);
        dateTime.setMonth(12);
        dateTime.setDay(31);
        dateTime.increaseDay();
        assertEquals("Should increase day", expectedDate.toString(), dateTime.toString());
    }

    @Test
    public void shouldConvertToDateString() {
        assertEquals("Should convert to date string", "2020-10-23", dateTime.toDateString());
    }

    @Test
    public void shouldConvertToReverseDateString() {
        assertEquals("Should convert to reverse date string", "23-10-2020", dateTime.toDateStringReverse());
    }

    @Test
    public void shouldConvertToTimeString() {
        assertEquals("Should convert to time string", "15:02:11", dateTime.toTimeString());
    }

    @Test(expected = DifferentDatesException.class)
    public void shouldNotDatesBeDifferent() throws DifferentDatesException, PreviousEndDateException {
        dateTime.getMinutesDuration(dateTime2);
    }

    @Test(expected = PreviousEndDateException.class)
    public void shouldNotEndDateBeAfterStartOne() throws DifferentDatesException, PreviousEndDateException {
        dateTime7.getMinutesDuration(dateTime);
    }

    @Test
    public void shouldCalculateDurationInMinutes() throws DifferentDatesException, PreviousEndDateException {
        int duration = dateTime.getMinutesDuration(dateTime7);
        assertEquals("Should calculate duration in minutes", 60, duration);
    }

    @Test
    public void shouldAddOneSecond() {
        dateTime.addSecond();
        assertEquals("Should add one second", "2020-10-23T15:02:12", dateTime.toString());
    }

    @Test
    public void shouldAddOneSecondAndChangeMinute() throws InvalidFormatException {
        DateTime dateTime = DateTime.Builder.build(2020, 10, 23, 15, 2, 59);
        dateTime.addSecond();
        assertEquals("Should add one second and change minute", "2020-10-23T15:03:00", dateTime.toString());
    }

    @Test
    public void shouldAddOneSecondAndChangeHour() throws InvalidFormatException {
        DateTime dateTime = DateTime.Builder.build(2020, 10, 23, 15, 59, 59);
        dateTime.addSecond();
        assertEquals("Should add one second and change hour", "2020-10-23T16:00:00", dateTime.toString());
    }

    @Test
    public void shouldAddOneSecondAndChangeDay() throws InvalidFormatException {
        DateTime dateTime = DateTime.Builder.build(2020, 10, 23, 23, 59, 59);
        dateTime.addSecond();
        assertEquals("Should add one second and change hour", "2020-10-24T00:00:00", dateTime.toString());
    }

    @Test
    public void shouldAddOneSecondAndChangeMonth() throws InvalidFormatException {
        DateTime dateTime = DateTime.Builder.build(2020, 10, 31, 23, 59, 59);
        dateTime.addSecond();
        assertEquals("Should add one second and change hour", "2020-11-01T00:00:00", dateTime.toString());
    }

    @Test
    public void shouldAddOneSecondAndChangeYear() throws InvalidFormatException {
        DateTime dateTime = DateTime.Builder.build(2020, 12, 31, 23, 59, 59);
        dateTime.addSecond();
        assertEquals("Should add one second and change hour", "2021-01-01T00:00:00", dateTime.toString());
    }
}
