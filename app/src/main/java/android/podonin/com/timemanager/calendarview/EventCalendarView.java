package android.podonin.com.timemanager.calendarview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class EventCalendarView extends ViewPager {


    private MonthViewPagerAdapter mPagerAdapter;

    public EventCalendarView(@NonNull Context context) {
        this(context, null);
    }

    public EventCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPagerAdapter
    }
}
