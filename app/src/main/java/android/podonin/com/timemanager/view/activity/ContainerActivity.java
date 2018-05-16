package android.podonin.com.timemanager.view.activity;

import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.navigation.FragmentNavigator;
import android.podonin.com.timemanager.presenter.ContainerActivityPresenter;
import android.podonin.com.timemanager.view.ActivityView;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ybr on 16.03.2018.
 */

public class ContainerActivity extends AppCompatActivity implements ActivityView {

    FragmentNavigator mFragmentNavigator;
    ContainerActivityPresenter mLayoutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mFragmentNavigator = new FragmentNavigator(this);
        mLayoutPresenter = new ContainerActivityPresenter(this);
        mLayoutPresenter.dispatchCreate();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mLayoutPresenter.dispatchDestroy();
        super.onDestroy();
    }

    public FragmentNavigator getFragmentNavigator() {
        return mFragmentNavigator;
    }

    @Override
    public void showTasksFragment() {
        mFragmentNavigator.showTasksFragment();
    }
}
