package android.podonin.com.timemanager.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.podonin.com.timemanager.R;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

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

    public void setData(float[] values, @NonNull ArrayList<String> labels,
                        int[] colors, @NonNull String dataLabel, @NonNull String description, boolean anabelPercent) {

        mPieChart.setUsePercentValues(anabelPercent);
        Description desc = new Description();
        desc.setText("");
        mPieChart.setDescription(desc);
        mPieChart.setCenterText(generateCenterSpannableText(description));

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> newColors = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (values[i] != 0) {
                PieEntry entry = new PieEntry(values[i], i);
                entry.setLabel(labels.get(i));
                entries.add(entry);
                newColors.add(colors[i]);
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, dataLabel);
        dataSet.setColors(newColors);
        dataSet.setSliceSpace(3f);

        PieData pieData = new PieData(dataSet);
        if (anabelPercent) {
            pieData.setValueFormatter(new PercentFormatter());
        }
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.GRAY);
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

    private SpannableString generateCenterSpannableText(String text) {

        SpannableString s = new SpannableString(text);
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length(), 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
}
