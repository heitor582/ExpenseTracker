package com.study.expensetracker.domain.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;

public final class InstantUtils {
    private InstantUtils() {}

    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    public static boolean isTheSameMonthAndYear(final Instant instant){
        final int month = getCalendar(instant).get(Calendar.MONTH);
        final int year = getCalendar(instant).get(Calendar.YEAR);

        final int thisYear = getCalendar().get(Calendar.YEAR);
        final int thisMonth = getCalendar().get(Calendar.MONTH);

        return month == thisMonth && year == thisYear;
    }

    public static Calendar getCalendar() {
        final ZonedDateTime zdt = ZonedDateTime.ofInstant(now(), ZoneId.systemDefault());
        return GregorianCalendar.from(zdt);
    }

    public static Calendar getCalendar(final Instant instant) {
        final ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        return GregorianCalendar.from(zdt);
    }
}
