package android.podonin.com.timemanager.view.fragment;

import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.adapter.DiagramPagerAdapter;
import android.podonin.com.timemanager.presenter.DiagramFragmentPresenter;
import android.podonin.com.timemanager.view.DiagramFragmentView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DiagramFragment extends Fragment
        implements DiagramFragmentView {

    public static DiagramFragment newInstance() {
        return new DiagramFragment();
    }

    private DiagramFragmentPresenter mPresenter;
    private DiagramPagerAdapter mDiagramPagerAdapter;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diagram, container, false);
        mViewPager = view.findViewById(R.id.view_pager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDiagramPagerAdapter = new DiagramPagerAdapter();
        mViewPager.setAdapter(mDiagramPagerAdapter);

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
                        int[] colorResIds, int labelResId, int descriptionResId, boolean anabelPercent) {

        ArrayList<String> labels = new ArrayList<>();
        for (int resId : stringResIds){
            labels.add(getString(resId));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int colorId : colorResIds) {
            colors.add(getResources().getColor(colorId));
        }

        mDiagramPagerAdapter.setData(values, labels, colors, "/" + getString(labelResId),
                getString(descriptionResId), anabelPercent);
    }
}
