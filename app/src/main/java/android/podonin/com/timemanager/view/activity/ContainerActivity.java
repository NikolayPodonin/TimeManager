package android.podonin.com.timemanager.view.activity;

import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.navigation.FragmentNavigator;
import android.podonin.com.timemanager.presenter.ActivityPresenter;
import android.podonin.com.timemanager.view.ActivityView;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ybr on 16.03.2018.
 */

public class ContainerActivity extends AppCompatActivity implements ActivityView {

    FragmentNavigator mFragmentNavigator;
    ActivityPresenter mLayoutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mFragmentNavigator = FragmentNavigator.getInstance(this);
        mLayoutPresenter = new ActivityPresenter(this);
        mLayoutPresenter.dispatchCreate();
    }

    @Override
    public void showTasksFragment() {
        mFragmentNavigator.showTasksFragment();
    }
}
