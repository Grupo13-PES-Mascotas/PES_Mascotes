package org.pesmypetcare.mypetcare.utilities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDateTime {
    private DateTime dateTime;
    private DateTime dateTime2;
    private DateTime dateTime3;

    @Before
    public void setUp() throws InvalidFormatException {
        dateTime = new DateTime(2020, 10, 23, 15, 2, 11);
        dateTime2 = new DateTime(2030, 10, 23, 15, 2, 11);
        dateTime3 = new DateTime(1914, 05, 22, 17, 21, 33);
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
        new DateTime(-2020, 10, 23, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldMonthBePositive() throws InvalidFormatException {
        new DateTime(2020, -10, 23, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDayBePositive() throws InvalidFormatException {
        new DateTime(2020, 10, -23, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldHourBePositive() throws InvalidFormatException {
        new DateTime(2020, 10, 23, -15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldMinutesBePositive() throws InvalidFormatException {
        new DateTime(2020, 10, 23, 15, -2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldSecondsBePositive() throws InvalidFormatException {
        new DateTime(2020, 10, 23, 15, 2, -11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldMonthNotBeOutOfRange() throws InvalidFormatException {
        new DateTime(2020, 22, 23, 15, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldHourNotBeOutOfRange() throws InvalidFormatException {
        new DateTime(2020, 11, 23, 28, 2, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldMinutesNotBeOutOfRange() throws InvalidFormatException {
        new DateTime(2020, 2, 23, 15, 82, 11);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldSecondsNotBeOutOfRange() throws InvalidFormatException {
        new DateTime(2020, 4, 23, 15, 2, 111);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDaysNotBeOutOfRangeNonFebruary31Days() throws InvalidFormatException {
        new DateTime(2020, 1, 32, 15, 2, 111);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDaysNotBeOutOfRangeNonFebruary30Days() throws InvalidFormatException {
        new DateTime(2020, 4, 31, 15, 2, 111);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDaysNotBeOutOfRangeFebruaryNonLeapYear() throws InvalidFormatException {
        new DateTime(2019, 2, 29, 15, 2, 111);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDaysNotBeOutOfRangeFebruaryLeapYearNotEndingIn00() throws InvalidFormatException {
        new DateTime(2020, 2, 30, 15, 2, 111);
    }

    @Test(expected = InvalidFormatException.class)
    public void shouldDaysNotBeOutOfRangeFebruaryLeapYearEndingIn00() throws InvalidFormatException {
        new DateTime(1900, 2, 29, 15, 2, 111);
    }

    @Test
    public void shouldTransformToString() {
        assertEquals("Should transform to string", "2020-10-23T15:02:11",
            dateTime.toString());
    }

    @Test
    public void shouldTransformToDateTime() {
        assertEquals("Should transform to dateTime", 0, dateTime.compareTo(new DateTime("2020-10-23T15:02:11")));
    }

}
