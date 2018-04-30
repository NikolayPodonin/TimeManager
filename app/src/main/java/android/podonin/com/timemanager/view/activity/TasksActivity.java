package android.podonin.com.timemanager.view.activity;

import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.navigation.FragmentNavigator;
import android.podonin.com.timemanager.presenter.TasksActivityPresenter;
import android.podonin.com.timemanager.view.TasksActivityView;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ybr on 16.03.2018.
 */

public class TasksActivity extends AppCompatActivity implements TasksActivityView {

    FragmentNavigator mFragmentNavigator;
    TasksActivityPresenter mLayoutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mFragmentNavigator = FragmentNavigator.initializeInstance(this);
        mLayoutPresenter = new TasksActivityPresenter(this);
        mLayoutPresenter.dispatchCreate();
    }

    @Override
    public void showTasksFragment() {
        mFragmentNavigator.showTasksFragment();
    }
}
