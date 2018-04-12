package android.podonin.com.timemanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TaskEditActivity extends SingleFragmentActivity {

    private static final String EXTRA_TASK_ID = "android.podonin.com.timemanager.task_id";

    @Override
    protected Fragment createFragment() {
        String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);
        return TaskEditFragment.newInstance(taskId);
    }

    public static Intent newIntent(Context packageContext, String taskId) {
        Intent intent = new Intent(packageContext, TaskEditActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }
}
