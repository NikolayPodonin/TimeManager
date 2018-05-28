package android.podonin.com.timemanager.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.adapter.DiagramPagerAdapter;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.presenter.DiagramFragmentPresenter;
import android.podonin.com.timemanager.view.DiagramFragmentView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Map;

public class DiagramFragment extends Fragment
        implements DiagramFragmentView {

    public static DiagramFragment newInstance() {
        return new DiagramFragment();
    }

    private DiagramFragmentPresenter mPresenter;
    private DiagramPagerAdapter mDiagramPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diagram, container, false);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        mDiagramPagerAdapter = new DiagramPagerAdapter();
        viewPager.setAdapter(mDiagramPagerAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new DiagramFragmentPresenter(this);
        mDiagramPagerAdapter.setOnPageChangeListener(page -> mPresenter.onChangeDiagram(page));

        mPresenter.dispatchCreate();
    }

    @Override
    public void onDestroy() {
        mPresenter.dispatchDestroy();
        super.onDestroy();
    }

    @Override
    public void setData(float[] values, int[] stringResIds,
                        int[] colors, int labelResId, int descriptionResId, boolean anabelPercent) {

        ArrayList<String> labels = new ArrayList<>();
        for (int resId : stringResIds){
            labels.add(getString(resId));
        }

        mDiagramPagerAdapter.setData(values, labels, colors, "/" + getString(labelResId),
                getString(descriptionResId), anabelPercent);
    }
}
