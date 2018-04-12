package android.podonin.com.timemanager.view.activity;

import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.navigation.ExampleNavigator;
import android.podonin.com.timemanager.presenter.ExamplePresenter;
import android.podonin.com.timemanager.view.ExampleView;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * @author Nic Podonin
 */

public class ExampleActivity extends AppCompatActivity implements ExampleView {

    private ExamplePresenter mExamplePresenter;
    private ExampleNavigator mExampleNavigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_example);

        mExampleNavigator = new ExampleNavigator(this);
        mExamplePresenter = new ExamplePresenter(this);
        mExamplePresenter.dispatchCreate();
    }

    @Override
    public void showSomeFragment() {
        mExampleNavigator.showExampleFragment(228, "папиросим");
    }
}
