package com.funny.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import static java.lang.Long.parseLong;


public class DateUtil {
    public static final String DEFAULT_TIME_ZONE = "GMT+9";

    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = 24 * HOUR;
    public static final String TIME_BEGIN = " 00:00:00";
    public static final String TIME_END = " 23:59:59";

    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String DEFAULT_PATTERN = "yyyyMMdd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:00";
    public static final String DATE_TIME_FORMAT_MINUTES = "yyyy-MM-dd HH:mm";
    public static final String DOT_PATTERN = "yyyy.MM.dd";
    public static final String FULL_PATTERN = "yyyyMMddHHmmss";
    public static final String FULL_STANDARD_PATTERN = "yyyyMMdd HH:mm:ss";
    public static final String KOREAN_PATTERN = "yyyy-MM-dd";
    public static final String FULL_KOREAN_PATTERN = "yyyy-MM-dd HH:mm:ss";




    public static Calendar getCurrentCalendar() {
        return Calendar.getInstance();
    }


    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }


    public static Date getCurrentDatetime() {
        return new Date(System.currentTimeMillis());
    }


    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return new Date(calendar.getTimeInMillis());
    }


    public static String getYear() {
        return formatDate("yyyy");
    }


    public static String getMonth() {
        return formatDate("MM");
    }


    public static String getDay() {
        return formatDate("dd");
    }


    public static int getYear(Date date) {
        if (date == null) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }


    public static int getYear(long millis) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }


    public static int getMonth(Date date) {
        if (date == null) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }


    public static int getMonth(long millis) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.MONTH) + 1;
    }


    public static int getDay(Date date) {
        if (date == null) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }


    public static int getDay(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.DATE);
    }


    public static int getHour(Date date) {
        if (date == null) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    public static int getHour(long millis) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    public static String formatDate(final Date date) {
        return formatDate(date,DEFAULT_PATTERN);
    }


    public static String formatDate(final Date date,String format) {
        if (null == date || StringUtils.isBlank(format)) {
            return null;
        }

        return new SimpleDateFormat(format).format(date);
    }


    public static String formatDate(String format) {
        return formatDate(new Date(),format);
    }


    public static Date parseDate(String sDate) {
        return parseDate(sDate,DEFAULT_PATTERN,null);
    }


    public static Date parseDate(String sDate,String format) {
        return parseDate(sDate,format,null);
    }


    public static Date parseDate(String sDate,String format,Date defaultValue) {
        if (StringUtils.isBlank(sDate) || StringUtils.isBlank(format)) {
            return defaultValue;
        }

        DateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(sDate);
        } catch (ParseException e) {
            return defaultValue;
        }
    }


    public static Date addMonths(Date date,int months) {
        if (months == 0) {
            return date;
        }

        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,months);
        return cal.getTime();
    }


    public static Date addDays(final Date date,int days) {
        if (days == 0) {
            return date;
        }

        if (date == null) {
            return null;
        }

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH,days);
        return cal.getTime();
    }


    public static Date addMins(final Date date,int mins) {
        if (mins == 0) {
            return date;
        }

        if (date == null) {
            return null;
        }

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE,mins);
        return cal.getTime();
    }


    public static int compareDate(Date first,Date second) {
        if (first == null && second == null) {
            return 0;
        }

        if (first == null) {
            return -1;
        }

        if (second == null) {
            return 1;
        }

        if (first.before(second)) {
            return -1;
        }

        if (first.after(second)) {
            return 1;
        }

        return 0;
    }


    public static Date getSmaller(Date first,Date second) {
        if (first == null && second == null) {
            return null;
        }

        if (first == null) {
            return second;
        }

        if (second == null) {
            return first;
        }

        if (first.before(second)) {
            return first;
        }

        if (first.after(second)) {
            return second;
        }

        return first;
    }


    public static Date getLarger(Date first,Date second) {
        if (first == null && second == null) {
            return null;
        }

        if (first == null) {
            return second;
        }

        if (second == null) {
            return first;
        }

        if (first.before(second)) {
            return second;
        }

        if (first.after(second)) {
            return first;
        }

        return first;
    }


    public static boolean isDateBetween(Date date,Date begin,Date end) {
        int c1 = compareDate(begin,date);
        int c2 = compareDate(date,end);

        return (((c1 == -1) && (c2 == -1)) || (c1 == 0) || (c2 == 0));
    }


    public static boolean isSameMonth(Date date1,Date date2) {
        if (date1 == null && date2 == null) {
            return true;
        }

        if (date1 == null || date2 == null) {
            return false;
        }

        Calendar cal1 = GregorianCalendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = GregorianCalendar.getInstance();
        cal2.setTime(date2);

        return isSameMonth(cal1,cal2);
    }


    public static boolean isSameDay(Date date1,Date date2) {
        if (date1 == null && date2 == null) {
            return true;
        }

        if (date1 == null || date2 == null) {
            return false;
        }

        Calendar cal1 = GregorianCalendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = GregorianCalendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE);
    }


    public static boolean isSameMonth(Calendar cal1,Calendar cal2) {
        if (cal1 == null && cal2 == null) {
            return true;
        }

        if (cal1 == null || cal2 == null) {
            return false;
        }

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }


    public static Date getStartOfDate(final Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }


    public static Date getPreviousMonday() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;

        Date date;
        if (dayOfWeek == 1) {
            date = addDays(cd.getTime(),-7);
        } else {
            date = addDays(cd.getTime(),-6 - dayOfWeek);
        }

        return getStartOfDate(date);
    }


    public static Date getMondayBefore4Week() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;

        Date date;
        if (dayOfWeek == 1) {
            date = addDays(cd.getTime(),-28);
        } else {
            date = addDays(cd.getTime(),-27 - dayOfWeek);
        }

        return getStartOfDate(date);
    }


    public static Date getCurrentMonday() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;

        Date date;
        if (dayOfWeek == 1) {
            date = cd.getTime();
        } else {
            date = addDays(cd.getTime(),1 - dayOfWeek);
        }

        return getStartOfDate(date);
    }


    public static Date getEndOfMonth(final Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DATE,0);

        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return new Date(calendar.getTimeInMillis());
    }


    public static Date getFirstOfMonth(final Date date) {
        Date lastMonth = addMonths(date,-1);
        lastMonth = getEndOfMonth(lastMonth);
        return addDays(lastMonth,1);
    }


    public static Date getWeekBegin(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        int dw = cal.get(Calendar.DAY_OF_WEEK);
        while (dw != Calendar.MONDAY) {
            cal.add(Calendar.DATE,-1);
            dw = cal.get(Calendar.DAY_OF_WEEK);
        }

        return cal.getTime();
    }


    public static Date getWeekEnd(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        int dw = cal.get(Calendar.DAY_OF_WEEK);
        while (dw != Calendar.SUNDAY) {
            cal.add(Calendar.DATE,1);
            dw = cal.get(Calendar.DAY_OF_WEEK);
        }

        return cal.getTime();
    }


    public static boolean inFormat(String sourceDate,String format) {
        if (sourceDate == null || StringUtils.isBlank(format)) {
            return false;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(false);
            dateFormat.parse(sourceDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static int getNumberOfSecondsBetween(final double d1,final double d2) {
        if (d1 == 0 || d2 == 0) {
            return -1;
        }

        return ConverterUtil.toIntValue(Math.abs(d1 - d2) / SECOND);
    }


    public static int getNumberOfMonthsBetween(final Date begin,final Date end) {
        if (begin == null || end == null) {
            return -1;
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(begin);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);

        return (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12 +
                (cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH));
    }


    public static long getNumberOfMinuteBetween(final Date begin,final Date end) {
        if (begin == null || end == null) {
            return -1;
        }

        long millisec = end.getTime() - begin.getTime();
        return millisec / (60 * 1000);
    }


    public static long getNumberOfHoursBetween(final Date begin,final Date end) {
        if (begin == null || end == null) {
            return -1;
        }

        long millisec = end.getTime() - begin.getTime() + 1;
        return millisec / (60 * 60 * 1000);
    }


    public static long getNumberOfDaysBetween(final Date begin,final Date end) {
        if (begin == null || end == null) {
            return -1;
        }

        long millisec = end.getTime() - begin.getTime();
        return millisec / (60 * 60 * 1000 * 24);
    }


    public static long getNumberOfDaysBetween(Calendar before,Calendar after) {
        if (before == null || after == null) {
            return -1;
        }

        before.clear(Calendar.MILLISECOND);
        before.clear(Calendar.SECOND);
        before.clear(Calendar.MINUTE);
        before.clear(Calendar.HOUR_OF_DAY);

        after.clear(Calendar.MILLISECOND);
        after.clear(Calendar.SECOND);
        after.clear(Calendar.MINUTE);
        after.clear(Calendar.HOUR_OF_DAY);

        long elapsed = after.getTime().getTime() - before.getTime().getTime();
        return elapsed / DAY;
    }


    public static Date getRemoteDate(String url) {
        if (url == null) {
            return null;
        }

        URLConnection uc;
        try {
            uc = new URL(url).openConnection();
            uc.connect();
            return new Date(uc.getDate());
        } catch (IOException e) {
            return new Date();
        }
    }


    public static Calendar getCurDateCeil() {
        Calendar cal = new GregorianCalendar();
        return new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
    }


    public static Calendar getCurDateFloor() {
        Calendar cal = new GregorianCalendar();
        return new GregorianCalendar(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH),
                    23,
                    0
        );
    }


    public static Date getWeekBegin(Calendar tmp) {
        if (tmp == null) {
            return null;
        }

        Calendar ctmp = new GregorianCalendar(
                            tmp.get(Calendar.YEAR),
                            tmp.get(Calendar.MONTH),
                            tmp.get(Calendar.DAY_OF_MONTH)
        );

        int dw = ctmp.get(Calendar.DAY_OF_WEEK);
        while (dw != Calendar.MONDAY) {
            ctmp.add(Calendar.DATE,-1);
            dw = ctmp.get(Calendar.DAY_OF_WEEK);
        }

        return ctmp.getTime();
    }


    public static Date getWeekEnd(Calendar tmp) {
        if (tmp == null) {
            return null;
        }

        Calendar ctmp = new GregorianCalendar(
                            tmp.get(Calendar.YEAR),
                            tmp.get(Calendar.MONTH),
                            tmp.get(Calendar.DAY_OF_MONTH),
                            23,
                            0
        );

        int dw = ctmp.get(Calendar.DAY_OF_WEEK);
        while (dw != Calendar.SUNDAY) {
            ctmp.add(Calendar.DATE,1);
            dw = ctmp.get(Calendar.DAY_OF_WEEK);
        }

        return ctmp.getTime();
    }


    public static Date getQuarterBegin(Calendar tmp) {
        if (tmp == null) {
            return null;
        }

        Calendar ctmp = new GregorianCalendar(
                            tmp.get(Calendar.YEAR),
                            tmp.get(Calendar.MONTH),
                            tmp.get(Calendar.DAY_OF_MONTH)
        );

        int month = ctmp.get(Calendar.MONTH);
        int offset = -(month % 3);
        ctmp.add(Calendar.MONTH,offset);

        return getFirstOfMonth(ctmp.getTime());
    }


    public static Date getQuarterEnd(Calendar tmp) {
        if (tmp == null) {
            return null;
        }

        Calendar ctmp = new GregorianCalendar(
                            tmp.get(Calendar.YEAR),
                            tmp.get(Calendar.MONTH),
                            tmp.get(Calendar.DAY_OF_MONTH),
                            23,
                            0
        );

        int month = ctmp.get(Calendar.MONTH);
        int offset = 2 - (month % 3);
        ctmp.add(Calendar.MONTH,offset);

        return getEndOfMonth(ctmp.getTime());
    }


    public static Date getQuarterBegin(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);
        int offset = -(month % 3);
        cal.add(Calendar.MONTH,offset);

        return getFirstOfMonth(cal.getTime());
    }


    public static Date getQuarterEnd(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);
        int offset = 2 - (month % 3);
        cal.add(Calendar.MONTH,offset);

        return getEndOfMonth(cal.getTime());
    }


    public static Date getYearBegin(final Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH,0);
        cal.set(Calendar.DATE,1);

        return cal.getTime();
    }


    public static Date getYearEnd(final Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH,11);

        return getEndOfMonth(cal.getTime());
    }


    public static Date getDate(int year,int month,int day) {
        GregorianCalendar d = new GregorianCalendar(year,month-1,day);
        return d.getTime();
    }


    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant,zone);
    }


    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = LocalDateTime.now().atZone(zone).toInstant();
        return Date.from(instant);
    }


    public enum TimeUnit {
        DAYS,HOURS,MINUTES,SECONDS
    }


    public static LocalDate localDateNow() {
        return LocalDate.now();
    }


    public static LocalTime timeNow(){
        return LocalTime.now();
    }


    public static LocalDateTime dateAndTimeNow(){
        return LocalDateTime.now();
    }


    public static String localDateTimetoString(LocalDateTime dateTime,String pattern) {
        if (dateTime == null || pattern == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }


    public static String localDatetoString(LocalDate date,String pattern) {
        if (date == null || pattern == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }


    public static String localTimetoString(LocalTime time,String pattern) {
        if (time == null || pattern == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return time.format(formatter);
    }


    public static LocalDateTime stringToLocalDateTime(String dateTimeString,String pattern) {
        if (dateTimeString == null || pattern == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeString,formatter);
    }


    public static LocalDate stringToLocalDate(String dateString,String pattern) {
        if (dateString == null || pattern == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateString,formatter);
    }


    public static LocalTime stringToLocalTime(String timeString,String pattern) {
        if (timeString == null || pattern == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalTime.parse(timeString,formatter);
    }


    public static long localDateTimeDiff(LocalDateTime firstDateTime,LocalDateTime secondDateTime,TimeUnit returnAs) {
        if (firstDateTime == null || secondDateTime == null) {
            throw new IllegalArgumentException("ARGUMENTS PASSED TO METHOD CANNOT BE NULL!!");
        }

        Duration diff = Duration.between(firstDateTime,secondDateTime).abs();

        long result = 0 ;
        switch(returnAs){
            case DAYS		:
                result = diff.toDays();
            break;

            case HOURS		:
                result = diff.toHours();
            break;

            case MINUTES	:
                result = diff.toMinutes();
            break;

            case SECONDS	:
                result = diff.getSeconds();
            break;
        }

        return result;
    }


    public static LocalDateTime localDateTimeAdd(LocalDateTime dateTime,long valueToAdd,TimeUnit unit) {
        if (dateTime == null || valueToAdd == 0) {
            throw new IllegalArgumentException("ARGUMENTS PASSED TO METHOD CANNOT BE NULL!!");
        }

        LocalDateTime result = null ;
        switch(unit) {
            case DAYS :
                result = dateTime.plus(valueToAdd,ChronoUnit.DAYS);
            break;

            case HOURS:
                result = dateTime.plus(valueToAdd,ChronoUnit.HOURS);
            break;

            case MINUTES:
                result = dateTime.plus(valueToAdd,ChronoUnit.MINUTES);
            break;

            case SECONDS:
                result = dateTime.plus(valueToAdd,ChronoUnit.SECONDS);
            break;
        }

        return result ;
    }


    public static String jodaDateToString(Date date,String pattern) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(DateTimeFormat.forPattern(pattern));
    }


    public static boolean isDateBefore(final String dt1,final String dt2) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(KOREAN_PATTERN);
        final LocalDate date1 = LocalDate.parse(dt1,formatter);
        final LocalDate date2 = LocalDate.parse(dt2,formatter);
        return date2.isBefore(date1);
    }


    public static boolean isDateTimeBefore(final String dt1,final String dt2) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        final LocalDateTime dateTime1 = LocalDateTime.parse(dt1,formatter);
        final LocalDateTime dateTime2 = LocalDateTime.parse(dt2,formatter);
        return dateTime2.isBefore(dateTime1);
    }


    public static boolean isDateTimeBefore(final String dt1,final String dt2,final String format) {
        return isDateTimeBefore(dt1,format,dt2,format);
    }


    public static boolean isDateTimeBefore(final String dt1,final String format1,final String dt2,final String format2) {
        final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern(format1);
        final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(format2);
        final LocalDateTime dateTime1 = LocalDateTime.parse(dt1,formatter1);
        final LocalDateTime dateTime2 = LocalDateTime.parse(dt2,formatter2);
        return dateTime2.isBefore(dateTime1);
    }


    public static boolean isCurrentDateBefore(String dt) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        final LocalDateTime currentDate = LocalDateTime.now();
        final LocalDateTime dateTime = LocalDateTime.parse(dt,formatter);
        return currentDate.toLocalDate().isAfter(dateTime.toLocalDate());
    }


    public static boolean isCurrentDateBefore(final String dt1,final String format1) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format1);
        final LocalDateTime currentDate = LocalDateTime.now();
        final LocalDateTime dateTime = LocalDateTime.parse(dt1,formatter);
        return currentDate.toLocalDate().isAfter(dateTime.toLocalDate());
    }


    public static boolean isCurrentDateTimeBefore(final String dt1,final String format1) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format1);
        final LocalDateTime currentDate = LocalDateTime.now();
        final LocalDateTime dateTime = LocalDateTime.parse(dt1,formatter);
        return currentDate.isAfter(dateTime);
    }


    public static boolean isCurrentTimeAfter(String dt) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        final LocalDateTime currentDate = LocalDateTime.parse(LocalDateTime.now().format(formatter),formatter);
        final LocalDateTime dateTime = LocalDateTime.parse(dt,formatter);
        return dateTime.isAfter(currentDate);
    }


    public static boolean isCurrentTimeBeforeHour(final String dt,final String format,final int minusHour) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        final LocalDateTime currentTime = LocalDateTime.parse(LocalDateTime.now().format(formatter),formatter);
        final LocalDateTime dateTime = LocalDateTime.parse(dt,formatter);
        return dateTime.isBefore(currentTime.minusHours(minusHour));
    }


    public static String setCurrentDateBefore(String dt) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        final LocalDateTime currentDate = LocalDateTime.now();
        final LocalDateTime dateTime = LocalDateTime.parse(dt,formatter);
        if (currentDate.toLocalDate().isAfter(dateTime.toLocalDate())) {
            return currentDate.format(formatter);
        } else {
            return dt;
        }
    }


    public static Boolean isBeforeMonth(String startDt,String endDt,int plusMonth) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(KOREAN_PATTERN);
        final LocalDate endDtLocal = LocalDate.parse(endDt,formatter);
        final LocalDate startDtLocal = LocalDate.parse(startDt,formatter);
        final LocalDate startDtPlus = startDtLocal.plusMonths(plusMonth);
        return startDtPlus.isBefore(endDtLocal);
    }


    public static boolean isValidParseDateStrictly(String dateStr,String parsePattern) throws ParseException {
        DateUtils.parseDateStrictly(dateStr,parsePattern);
        return true;
    }


    public static LocalDate getParseLocalDateStrictly(String dateStr,String parsePattern) throws ParseException {
        DateUtils.parseDateStrictly(dateStr,parsePattern);
        return LocalDate.parse(dateStr,DateTimeFormatter.ofPattern(parsePattern));
    }


    public static LocalDateTime getParseLocalDateTimeStrictly(String dateStr,String parsePattern) throws ParseException {
        DateUtils.parseDateStrictly(dateStr,parsePattern);
        return LocalDateTime.parse(dateStr,DateTimeFormatter.ofPattern(parsePattern));
    }


    public static String getDayOfWeekKorNm(String dateStr,String parsePattern) throws ParseException {
        DateUtils.parseDateStrictly(dateStr,parsePattern);
        LocalDate localDate = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern(parsePattern));
        return localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.KOREAN);
    }


    public static String convertUnixTimeStampToDate(String time,String format) {
        return convertUnixTimeStampToDate(parseLong(time),format);
    }


    public static String convertUnixTimeStampToDate(long time,String format) {
        Date date = new java.util.Date(time*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        return sdf.format(date);
    }


    public static int calculateAge( String birthday ){
        if (StringUtils.isEmpty(birthday) || birthday.length() != 8){
            return 0;
        }

        LocalDateTime nowDateTime = LocalDateTime.now();
        String today = nowDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));

        int todayYear = Integer.parseInt(today.substring(0,4));
        int todayMonth = Integer.parseInt(today.substring(4,8));

        int memberYear = Integer.parseInt(birthday.substring(0,4));
        int memberMonth = Integer.parseInt(birthday.substring(4,8));

        int age = todayYear -  memberYear;
        if( todayMonth < memberMonth ){
            age--;
        }

        return age;
    }
}
