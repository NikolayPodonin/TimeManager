package android.podonin.com.timemanager.calendarwidget;

import android.content.Context;
import android.podonin.com.timemanager.R;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MonthView extends RecyclerView{

    private static final int SPANS_COUNT = 7; // days in week
    private long mMonthMillis;
    private GridAdapter mAdapter;
    private OnDateChangeListener mListener;

    interface OnDateChangeListener {
        void onSelectedDayChange(long dayMillis);
    }

    public void setOnDateChangeListener(OnDateChangeListener listener) {
        mListener = listener;
    }

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), SPANS_COUNT));
        setHasFixedSize(true);
        setCalendar(CalendarUtils.today());
    }

    public void setCalendar(long monthMillis) {
        if(CalendarUtils.isNotTime(monthMillis)){
            throw new IllegalArgumentException("Invalid timestamp value");
        }
        if(CalendarUtils.sameMonth(mMonthMillis, monthMillis)){
            return;
        }
        mMonthMillis = monthMillis;
        mAdapter = new GridAdapter(monthMillis);
        mAdapter.registerAdapterDataObserver(new AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                if (mListener == null){
                    return;
                }
                if (payload instanceof SelectionPayload){
                    mListener.onSelectedDayChange(((SelectionPayload)payload).mTimeMillis);
                }
            }
        });
        setAdapter(mAdapter);
    }

    public void setSelectedDay(long dayMillis) {
        if (CalendarUtils.isNotTime(mMonthMillis)) {
            return;
        }
        if (CalendarUtils.isNotTime(dayMillis)){
            mAdapter.setSelectedDay(CalendarUtils.NO_TIME_MILLIS);
        } else if (CalendarUtils.sameMonth(mMonthMillis, dayMillis)){
            mAdapter.setSelectedDay(dayMillis);
        } else {
            mAdapter.setSelectedDay(CalendarUtils.NO_TIME_MILLIS);
        }

    }

    private static abstract class CellViewHolder extends RecyclerView.ViewHolder{

        public CellViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class HeaderViewHolder extends CellViewHolder{
        private final TextView mTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }

    private static class ContentViewHolder extends CellViewHolder{
        private final TextView mTextView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }

    private static class SelectionPayload {
        private final long mTimeMillis;

        public SelectionPayload(long timeMillis) {
            this.mTimeMillis = timeMillis;
        }
    }

    private class GridAdapter extends Adapter<CellViewHolder> {
        private static final int VIEW_TYPE_HEADER = 0;
        private static final int VIEW_TYPE_CONTENT = 1;
        private final String[] mWeekDays;
        private final long mBaseTimeMillis;
        private final int mStartOffset;
        private final int mDays;
        private int mSelectedPosition = -1;

        public GridAdapter(long monthMillis) {
            mWeekDays = DateFormatSymbols.getInstance().getShortWeekdays();
            mBaseTimeMillis = CalendarUtils.monthFirstDay(monthMillis);
            mStartOffset = CalendarUtils.monthFirstDayOffset(mBaseTimeMillis) + SPANS_COUNT;
            mDays = mStartOffset + CalendarUtils.monthSize(monthMillis);
        }

        @NonNull
        @Override
        public CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            switch (viewType){
                case VIEW_TYPE_HEADER:
                    return new HeaderViewHolder(inflater.inflate(R.layout.grid_item_header, parent, false));
                case VIEW_TYPE_CONTENT:
                default:
                    return new ContentViewHolder(inflater.inflate(R.layout.grid_item_content, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull CellViewHolder holder, int position) {
            if(holder instanceof HeaderViewHolder){
                int index;
                switch (CalendarUtils.sWeekStart){
                    case Calendar.SATURDAY:
                        index = position == 0 ? Calendar.SATURDAY : position;
                        break;
                    case Calendar.MONDAY:
                        index = position + Calendar.MONDAY == mWeekDays.length ? Calendar.SUNDAY : position + Calendar.MONDAY;
                        break;
                    case Calendar.SUNDAY:
                    default:
                        index = position + Calendar.SUNDAY;
                        break;
                }
                ((HeaderViewHolder)holder).mTextView.setText(mWeekDays[index]);
            } else { // holder instanceof ContentViewHolder
                if(position < mStartOffset){
                    ((ContentViewHolder)holder).mTextView.setText(null);
                } else {
                    final int adapterPosition = holder.getAdapterPosition();
                    TextView textView = ((ContentViewHolder)holder).mTextView;
                    int dayIndex = adapterPosition - mStartOffset;
                    String dayString = String.valueOf(dayIndex + 1);
                    SpannableString spannable = new SpannableString(dayString);
                    if(mSelectedPosition == adapterPosition){
                        spannable.setSpan(new CircleSpan(textView.getContext()),
                                0, dayString.length(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    textView.setText(spannable, TextView.BufferType.SPANNABLE);
                    textView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setSelectedPosition(adapterPosition, true);
                        }
                    });
                }
            }
        }

        @Override
        public int getItemCount() {
            return mDays;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < SPANS_COUNT){
                return VIEW_TYPE_HEADER;
            }
            return VIEW_TYPE_CONTENT;
        }

        private void setSelectedPosition(int position, boolean notifyObservers) {
            int last = mSelectedPosition;
            if (position == last){
                return;
            }
            mSelectedPosition = position;
            if (last >= 0){
                notifyItemChanged(last);
            }
            if (position >= 0) {
                long timeMillis = mBaseTimeMillis + (mSelectedPosition - mStartOffset) * DateUtils.DAY_IN_MILLIS;
                notifyItemChanged(position, notifyObservers ? new SelectionPayload(timeMillis) : null);
            }
        }


        public void setSelectedDay(long dayMillis) {
            setSelectedPosition(CalendarUtils.isNotTime(dayMillis) ? -1 :
                    mStartOffset + CalendarUtils.dayOfMonth(dayMillis) - 1, false);
        }
    }


}
