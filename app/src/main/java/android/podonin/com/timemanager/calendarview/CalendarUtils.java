package android.podonin.com.timemanager.calendarview;

import android.support.v4.util.Pools;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarUtils {

    /**
     * Constant value for a 'no time' timestamp
     */
    private static final long NO_TIME_MILLIS = -1;

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
