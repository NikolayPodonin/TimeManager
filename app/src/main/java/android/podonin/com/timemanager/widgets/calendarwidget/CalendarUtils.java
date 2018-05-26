package android.podonin.com.timemanager.widgets.calendarwidget;

import android.content.Context;
import android.support.v4.util.Pools;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarUtils {

    /**
     * Constant value for a 'no time' timestamp
     */
    static final long NO_TIME_MILLIS = -1;

    private static final String TIMEZONE_UTC = "UTC";

    public static int sWeekStart = Calendar.getInstance(TimeZone.getDefault()).getFirstDayOfWeek();
    
    public static long today() {
        DateOnlyCalendar calendar = DateOnlyCalendar.today();
        long timeMillis = calendar.getTimeInMillis();
        calendar.recycle();
        return timeMillis;
    }

    public static boolean isNotTime(long monthMillis) {
        return monthMillis == NO_TIME_MILLIS;
    }

    public static boolean sameMonth(long first, long second) {
        if(isNotTime(first) || isNotTime(second)){
            return false; // not comparable
        }
        DateOnlyCalendar firstCalendar = DateOnlyCalendar.fromTime(first);
        DateOnlyCalendar secondCalendar = DateOnlyCalendar.fromTime(second);
        boolean same = firstCalendar.sameMonth(secondCalendar);
        firstCalendar.recycle();
        secondCalendar.recycle();
        return same;
    }

    public static long monthFirstDay(long monthMillis) {
        if(isNotTime(monthMillis)){
            return NO_TIME_MILLIS;
        }
        DateOnlyCalendar calendar = DateOnlyCalendar.fromTime(monthMillis);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long result = calendar.getTimeInMillis();
        calendar.recycle();
        return result;
    }

    public static long currentDay(long monthMillis) {
        if(isNotTime(monthMillis)){
            return NO_TIME_MILLIS;
        }
        DateOnlyCalendar calendar = DateOnlyCalendar.fromTime(monthMillis);
        long result = calendar.getTimeInMillis();
        calendar.recycle();
        return result;
    }

    public static int monthFirstDayOffset(long monthMillis) {
        if(isNotTime(monthMillis)){
            return 0;
        }
        DateOnlyCalendar calendar = DateOnlyCalendar.fromTime(monthMillis);
        int offset = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
        if(offset < 0){
            offset = 7 + offset;
        }
        calendar.recycle();
        return offset;
    }

    public static int monthSize(long monthMillis) {
        if(isNotTime(monthMillis)){
            return 0;
        }
        DateOnlyCalendar calendar = DateOnlyCalendar.fromTime(monthMillis);
        int size = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.recycle();
        return size;
    }

    public static long addMonths(long todayMillis, int months) {
        if(isNotTime(todayMillis)){
            return NO_TIME_MILLIS;
        }
        DateOnlyCalendar calendar = DateOnlyCalendar.fromTime(todayMillis);
        calendar.add(Calendar.MONTH, months);
        long result = calendar.getTimeInMillis();
        calendar.recycle();
        return result;
    }

    public static long toLocalTimeZone(long utsTimeMillis) {
        return convertTimeZone(TimeZone.getDefault(), TimeZone.getTimeZone(TIMEZONE_UTC), utsTimeMillis);
    }

    private static long convertTimeZone(TimeZone fromTimeZone, TimeZone toTimeZone, long timeMillis) {
        DateOnlyCalendar fromCalendar = DateOnlyCalendar.obtain();
        fromCalendar.setTimeZone(fromTimeZone);
        fromCalendar.setTimeInMillis(timeMillis);
        DateOnlyCalendar toCalendar = DateOnlyCalendar.obtain();
        toCalendar.setTimeZone(fromTimeZone);
        toCalendar.set(fromCalendar.get(Calendar.YEAR),
                fromCalendar.get(Calendar.MONTH),
                fromCalendar.get(Calendar.DAY_OF_MONTH),
                fromCalendar.get(Calendar.HOUR_OF_DAY),
                fromCalendar.get(Calendar.MINUTE),
                fromCalendar.get(Calendar.SECOND));
        long localTimeMillis = toCalendar.getTimeInMillis();
        fromCalendar.recycle();
        toCalendar.recycle();
        return localTimeMillis;
    }

    public static int dayOfMonth(long timeMillis) {
        if (isNotTime(timeMillis)){
            return -1;
        }
        DateOnlyCalendar calendar = DateOnlyCalendar.fromTime(timeMillis);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.recycle();
        return day;
    }

    public static String toMonthString(Context context, long timeMillis) {
        return DateUtils.formatDateRange(context, timeMillis, timeMillis,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_MONTH_DAY | DateUtils.FORMAT_SHOW_YEAR);
    }

    public static String toDateString(Context context, long dateMillis) {
        return DateUtils.formatDateRange(context, dateMillis, dateMillis,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
    }

    public static long toDateLong(String date) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        Date parseDate = new Date();
        try {
            parseDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDate.getTime();
    }

    private static class DateOnlyCalendar extends GregorianCalendar{

        private static Pools.SimplePool<DateOnlyCalendar> sPools = new Pools.SimplePool<>(5);
        
        private static DateOnlyCalendar obtain() {
            DateOnlyCalendar instance = sPools.acquire();
            return instance == null ? new DateOnlyCalendar() : instance;
        }
        
        public static DateOnlyCalendar today() {
            return fromTime(System.currentTimeMillis());
        }

        private static DateOnlyCalendar fromTime(long timeMillis) {
            if(timeMillis < 0){
                return null;
            }
            DateOnlyCalendar dateOnlyCalendar = DateOnlyCalendar.obtain();
            dateOnlyCalendar.setTimeInMillis(timeMillis);
            dateOnlyCalendar.stripTime();
            dateOnlyCalendar.setFirstDayOfWeek(sWeekStart);
            return dateOnlyCalendar;
        }

        private DateOnlyCalendar(){
            super();
        }

        public boolean sameMonth(DateOnlyCalendar other) {
            return get(YEAR) == other.get(YEAR) && get(MONTH) == other.get(MONTH);
        }

        private void stripTime() {
            set(Calendar.HOUR_OF_DAY, 0);
            set(Calendar.MINUTE, 0);
            set(Calendar.SECOND, 0);
            set(Calendar.MILLISECOND, 0);
        }


        public void recycle() {
            setTimeZone(TimeZone.getDefault());
            sPools.release(this);
        }


    }
}
