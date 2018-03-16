package android.podonin.com.timemanager;

import android.support.v4.app.Fragment;

/**
 * Created by Ybr on 16.03.2018.
 */

public class TasksActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return TasksFragment.newInstance();
    }
}
