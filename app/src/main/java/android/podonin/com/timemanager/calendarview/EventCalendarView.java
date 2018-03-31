package android.podonin.com.timemanager.calendarview;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class EventCalendarView extends ViewPager {


    private MonthViewPagerAdapter mPagerAdapter;
    private final MonthView.OnDateChangeListener mDateChangeListener =
            new MonthView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(long dayMillis) {
                    mPagerAdapter.setSelectedDay(getCurrentItem(), dayMillis, false);
                    notifyDayChange(dayMillis);
                }
            };
    private OnChangeListener mListener;
    private CalendarAdapter mCalendarAdapter;

    public interface OnChangeListener{
        void onSelectedDayChange(long dayMillis);
    }


    public static abstract class CalendarAdapter {


        private EventCalendarView mCalendarView;

        /**
         * Loads events for given month. Should call {@link #bindEvents(long, EventCursor)} on complete
         * @param monthMillis    month in milliseconds
         * @see {@link #bindEvents(long, EventCursor)}
         */
        protected void loadEvents(long monthMillis) {
            // override to load events
        }

        /**
         * Binds events for given month that have been loaded via {@link #loadEvents(long)}
         * @param monthMillis    month in milliseconds
         * @param cursor         {@link android.provider.CalendarContract.Events} cursor wrapper
         */
        public final void bindEvents(long monthMillis, EventCursor cursor){
            mCalendarView.swapCursor(monthMillis, cursor);
        }
    }

    public EventCalendarView(@NonNull Context context) {
        this(context, null);
    }

    public EventCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPagerAdapter = new MonthViewPagerAdapter(mDateChangeListener);
        setAdapter(mPagerAdapter);
        setCurrentItem(mPagerAdapter.getCount() / 2);
        addOnPageChangeListener(new SimpleOnPageChangeListener(){
            public boolean mDragging = false; //indicate if page change is from user

            @Override
            public void onPageSelected(int position) {
                if (mDragging){
                    // sequence: IDLE -> (DRAGGING) -> SETTLING -> onPageSelected -> IDLE
                    // ensures that this will always be triggered before syncPages() for position
                    toFirstDay(position);
                    notifyDayChange(mPagerAdapter.getMonth(position));
                }
                mDragging = false;
                // trigger same scroll state changed logic, which would be fired if not visible
                if (getVisibility() != VISIBLE){
                    onPageScrollStateChanged(SCROLL_STATE_IDLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    syncPages(getCurrentItem());
                    loadEvents(getCurrentItem());
                } else if (state == SCROLL_STATE_DRAGGING){
                    mDragging = true;
                }
            }
        });
    }

    private void toFirstDay(int position) {
        mPagerAdapter.setSelectedDay(position,
                CalendarUtils.monthFirstDay(mPagerAdapter.getMonth(position)), true);
    }


    private void notifyDayChange(long dayMillis) {
        if (mListener != null){
            mListener.onSelectedDayChange(dayMillis);
        }
    }

    private void syncPages(int position) {
        int first = 0, last = mPagerAdapter.getCount() - 1;
        if (position == last){
            mPagerAdapter.shiftLeft();
            setCurrentItem(first + 1, false);
        } else if (position == 0){
            mPagerAdapter.shiftRight();
            setCurrentItem(last - 1, false);
        } else {
            // rebind neighbours due to shifting
            if(position > 0){
                mPagerAdapter.bind(position - 1);
            }
            if (position < mPagerAdapter.getCount() - 1){
                mPagerAdapter.bind(position + 1);
            }
        }
    }

    private void loadEvents(int position) {
        if (mCalendarAdapter != null && mPagerAdapter.getCursor(position) == null){
            mCalendarAdapter.loadEvents(mPagerAdapter.getMonth(position));
        }
    }

    private void swapCursor(long monthMillis, EventCursor cursor) {
        mPagerAdapter.swapCursor(monthMillis, cursor, new PagerContentObserver(monthMillis));
    }

    private class PagerContentObserver extends ContentObserver{
        private final long mMonthMillis;

        public PagerContentObserver(long monthMillis) {
            super(new Handler());
            mMonthMillis = monthMillis;
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            // invalidate previous cursor for given month
            mPagerAdapter.swapCursor(mMonthMillis, null, null);
            // reload events if given month is active month
            // hidden month will ve reloaded upon being swiped to
            if(CalendarUtils.sameMonth(mMonthMillis, mPagerAdapter.getMonth(getCurrentItem()))){
                loadEvents(getCurrentItem());
            }
        }
    }
}
