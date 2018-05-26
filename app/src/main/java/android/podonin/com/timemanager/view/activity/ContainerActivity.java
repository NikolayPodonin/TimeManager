package android.podonin.com.timemanager.view.activity;

import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.navigation.FragmentNavigator;
import android.podonin.com.timemanager.presenter.ContainerActivityPresenter;
import android.podonin.com.timemanager.view.ContainerActivityView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ybr on 16.03.2018.
 */

public class ContainerActivity extends AppCompatActivity implements ContainerActivityView {

    FragmentNavigator mFragmentNavigator;
    ContainerActivityPresenter mLayoutPresenter;

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()){
            case R.id.tasks_menu_item:
                mLayoutPresenter.onChooseTasksInMenu();
                return true;
            case R.id.diagram_menu_item:
                mLayoutPresenter.onChooseDiagramInMenu();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mFragmentNavigator = new FragmentNavigator(this);
        mLayoutPresenter = new ContainerActivityPresenter(this);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigationView.setSelectedItemId(R.id.diagram_menu_item);

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

    @Override
    public void showDiagramFragment() {
        mFragmentNavigator.showDiagramFragment();
    }
}
