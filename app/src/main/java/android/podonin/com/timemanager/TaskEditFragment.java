package android.podonin.com.timemanager;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.podonin.com.timemanager.databinding.FragmentTaskEditBinding;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.viewmodel.TimeTaskViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TaskEditFragment extends Fragment {

    private static final String ARG_TASK_ID = "task_id";

    private TimeTask mTimeTask;
    private FragmentTaskEditBinding mTaskEditBinding;
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
        mTaskEditBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_edit, container, false);

        TimeTaskViewModel taskViewModel = new TimeTaskViewModel(getActivity());
        mTaskEditBinding.setTaskViewModel(taskViewModel);
        mTaskEditBinding.getTaskViewModel().setTimeTask(mTimeTask);
        mTaskEditBinding.taskBodyEdit.addTextChangedListener(taskViewModel);

        return mTaskEditBinding.getRoot();
    }

    @Override
    public void onStop() {
        mRealmHelper.addTimeTask(mTaskEditBinding.getTaskViewModel().getTimeTask());
        super.onStop();
    }
}
