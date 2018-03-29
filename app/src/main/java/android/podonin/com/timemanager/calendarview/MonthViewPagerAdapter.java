package android.podonin.com.timemanager.calendarview;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;

class MonthViewPagerAdapter extends PagerAdapter{

    private static final int ITEM_COUNT = 5; //buffer, left, active, right, buffer
    private final MonthView.OnDateChangeListener mListener;

    public MonthViewPagerAdapter(MonthView.OnDateChangeListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
