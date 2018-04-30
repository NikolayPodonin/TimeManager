package android.podonin.com.timemanager.view.fragment;

import android.os.Bundle;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TaskEditFragment extends Fragment {

    private static final String ARG_TASK_ID = "task_id";

    private TimeTask mTimeTask;
    private RealmHelper mRealmHelper;

    public static TaskEditFragment newInstance(String taskId){
        Bundle args = new Bundle();
        args.putString(ARG_TASK_ID, taskId);

        TaskEditFragment fragment = new TaskEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String taskId = getArguments().getString(ARG_TASK_ID);
        mRealmHelper = new RealmHelper();

        mTimeTask = mRealmHelper.getTimeTask(taskId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_edit, container, false);

        return view;
    }

    @Override
    public void onStop() {
        //mRealmHelper.addTimeTask();
        super.onStop();
    }
}
