package android.podonin.com.timemanager.adapter;

import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.widgets.piediagramwidget.PieDiagramView;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

public class DiagramPagerAdapter extends PagerAdapter {
    public interface OnPageChangeListener{
        void onPageChange(int page);
    }

    private static final int PAGES_COUNT = 3;

    private OnPageChangeListener mOnPageChangeListener;
    private PieChart mPieChart;

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public void setData(@NonNull float[] values, @NonNull ArrayList<String> labels, @NonNull int[] colors) {

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            entries.add(new Entry(values[i], i));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Hallo pie");
        dataSet.setColors(colors);

        PieData pieData = new PieData(labels, dataSet);
        mPieChart.setData(pieData);
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mPieChart  = new PieChart(container.getContext());
        mPieChart.setLayoutParams(new ViewPager.LayoutParams());
        mPieChart.setPadding(30, 10, 30, 10);
        mPieChart.setRotationEnabled(false);
        if (mOnPageChangeListener != null){
            mOnPageChangeListener.onPageChange(position);
        }
        container.addView(mPieChart);
        return mPieChart;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        mPieChart = null;
    }
}
