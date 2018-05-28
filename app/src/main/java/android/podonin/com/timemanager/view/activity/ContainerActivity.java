package android.podonin.com.timemanager.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.navigation.FragmentNavigator;
import android.podonin.com.timemanager.presenter.ContainerActivityPresenter;
import android.podonin.com.timemanager.view.ContainerActivityView;
import android.podonin.com.timemanager.utilites.CalendarUtils;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ybr on 16.03.2018.
 */

public class ContainerActivity extends AppCompatActivity implements ContainerActivityView {
    private static final String SETTINGS = "ContainerActivity.Settings";

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

        checkFirstStart();

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigationView.setSelectedItemId(R.id.diagram_menu_item);

        mLayoutPresenter.dispatchCreate();
    }

    private void checkFirstStart() {
        String firstStart = "hasVisited";
        SharedPreferences preferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        boolean hasVisited = preferences.getBoolean(firstStart, false);

        if (!hasVisited){
            String s1 = getString(R.string.body_first_subcategory);
            String s2 = getString(R.string.business_first_subcategory);
            String s3 = getString(R.string.spirit_first_subcategory);
            String s4 = getString(R.string.relationships_first_subcategory);
            String s5 = getString(R.string.task_first_name);
            long d1 = CalendarUtils.today();
            mLayoutPresenter.onFirstStart(s1, s2, s3, s4, s5, d1);
            SharedPreferences.Editor e = preferences.edit();
            e.putBoolean(firstStart, true);
            e.apply();
        }
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
