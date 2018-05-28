package android.podonin.com.timemanager.widgets.calendarwidget;

import android.content.Context;
import android.podonin.com.timemanager.utilites.CalendarUtils;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class EventCalendarView extends ViewPager {

    public interface OnTopScrollListener {
        void onTopScroll(float distance);
    }

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

    public long getSelectedDay() {
        return mPagerAdapter.getSelectedDayMillis();
    }

    public interface OnChangeListener{
        void onSelectedDayChange(long dayMillis);
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mListener = listener;
    }

    public void setOnTopFlingListener(OnTopScrollListener listener) {
        mPagerAdapter.setOnTopScrollListener(listener);
    }

    public EventCalendarView(@NonNull Context context) {
        this(context, null);
    }

    public EventCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(CalendarUtils.today());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // make this ViewPager's height WRAP_CONTENT
        View child = mPagerAdapter.mViews.get(getCurrentItem());
        if(child != null){
            child.measure(widthMeasureSpec, heightMeasureSpec);
            int height = child.getMeasuredHeight();
            setMeasuredDimension(getMeasuredWidth(), height);
        }
    }

    private void init(long dayMillis) {
        mPagerAdapter = new MonthViewPagerAdapter(dayMillis, mDateChangeListener);
        setAdapter(mPagerAdapter);
        setCurrentItem(mPagerAdapter.getCount() / 2);
        addOnPageChangeListener(new SimpleOnPageChangeListener(){
            boolean mDragging = false; //indicate if page change is from user

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

    public void setSelectedDay(long dayMillis){
        init(dayMillis);
    }
}
