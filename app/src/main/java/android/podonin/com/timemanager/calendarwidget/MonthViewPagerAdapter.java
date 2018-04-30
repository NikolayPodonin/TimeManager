package android.podonin.com.timemanager.calendarwidget;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

class MonthViewPagerAdapter extends PagerAdapter{

    private static final int ITEM_COUNT = 5; //buffer, left, active, right, buffer
    private static final String STATE_FIRST_MONTH_MILLIS = "state:month";
    private static final String STATE_SELECTED_DAY_MILLIS = "state:selectedDay";
    private final MonthView.OnDateChangeListener mListener;
    private final List<Long> mMonths = new ArrayList<>(getCount());
    final List<MonthView> mViews = new ArrayList<>(getCount());
    private long mSelectedDayMillis = CalendarUtils.today();

    public MonthViewPagerAdapter(long dayMillis, MonthView.OnDateChangeListener listener) {
        mListener = listener;
        int mid = ITEM_COUNT / 2;
        for (int i = 0; i < getCount(); i++){
            mMonths.add(CalendarUtils.addMonths(dayMillis, i - mid));
            mViews.add(null);
        }
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        MonthView view = new MonthView(container.getContext());
        view.setLayoutParams(new ViewPager.LayoutParams());
        view.setOnDateChangeListener(mListener);
        mViews.set(position, view);
        container.addView(view); //views are not added in same order as adapter items
        bind(position);
        return view;
    }

    void bind(int position) {
        if (mViews.get(position) != null){
            mViews.get(position).setCalendar(mMonths.get(position));
        }
        bindSelectedDay(position);
    }

    private void bindSelectedDay(int position) {
        if (mViews.get(position) != null){
            mViews.get(position).setSelectedDay(mSelectedDayMillis);
        }
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((MonthView)object).setOnDateChangeListener(null);
        container.removeView((View)object);
    }

    @Nullable
    @Override
    public Parcelable saveState() {
        Bundle bundle = new Bundle();
        bundle.putLong(STATE_FIRST_MONTH_MILLIS, mMonths.get(0));
        bundle.putLong(STATE_SELECTED_DAY_MILLIS, mSelectedDayMillis);
        return bundle;
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        Bundle savedState = (Bundle)state;
        if (savedState == null){
            return;
        }
        mSelectedDayMillis = savedState.getLong(STATE_SELECTED_DAY_MILLIS);
        long firstMonthMillis = savedState.getLong(STATE_FIRST_MONTH_MILLIS);
        for (int i = 0; i < getCount(); i++){
            mMonths.set(i, CalendarUtils.addMonths(firstMonthMillis, i));
        }
    }

    public void setSelectedDay(int position, long dayMillis, boolean notifySelf) {
        mSelectedDayMillis = dayMillis;
        if(notifySelf){
            bindSelectedDay(position);
        }
        if (position > 0){
            bindSelectedDay(position - 1);
        }
        if (position < getCount() - 1){
            bindSelectedDay(position + 1);
        }
    }

    public long getMonth(int position) {
        return mMonths.get(position);
    }

    public void shiftLeft() {
        for (int i = 0; i < getCount() - 2; i++){
            mMonths.add(CalendarUtils.addMonths(mMonths.remove(0), getCount()));
        }
        // rebind current item (2nd) and 2 adjacent items
        for (int i = 0; i <= 2; i++){
            bind(i);
        }
    }

    public void shiftRight() {
        for (int i = 0; i < getCount() - 2; i++){
            mMonths.add(0, CalendarUtils.addMonths(mMonths.remove(getCount() - 1), -getCount()));
        }
        // rebind current item (2nd) and 2 adjacent items
        for (int i = 0; i <= 2; i++){
            bind(getCount() - 1 - i);
        }
    }


}
