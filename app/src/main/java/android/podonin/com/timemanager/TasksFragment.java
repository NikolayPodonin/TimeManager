package android.podonin.com.timemanager;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.calendarwidget.EventCalendarView;
import android.podonin.com.timemanager.databinding.FragmentTasksBinding;
import android.podonin.com.timemanager.databinding.ListItemTaskBinding;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.viewmodel.TimeTaskViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybr on 16.03.2018.
 */

public class TasksFragment extends Fragment {


    private List<TimeTask> mTimeTasks = new ArrayList<>();
    private RealmHelper mRealmHelper;

    public static TasksFragment newInstance(){
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRealmHelper = new RealmHelper();
        mTimeTasks = mRealmHelper.getAllTimeTasks();

        final FragmentTasksBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks, container, false);

        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(isAdded()){
            binding.tasksRecyclerView.setAdapter(new TasksAdapter(mTimeTasks));
        }

        binding.calendarView.setOnChangeListener(new EventCalendarView.OnChangeListener() {
            @Override
            public void onSelectedDayChange(long dayMillis) {
                // TODO: reset RecyclerView with selected day events
                binding.toolbarToggle.setText(CalendarUtils.toMonthString(getActivity(), dayMillis));
            }
        });

        binding.toolbarToggle.setText(CalendarUtils.toMonthString(getActivity(), CalendarUtils.today()));
        binding.toolbarToggleFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.toolbarToggle.toggle();
                if(binding.toolbarToggle.isChecked()){
                    binding.calendarView.setVisibility(View.VISIBLE);
                } else {
                    binding.calendarView.setVisibility(View.GONE);
                }
            }
        });

        binding.tasksActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTaskEditActivity();
            }
        });

        return binding.getRoot();
    }

    private void startTaskEditActivity(){
        TimeTask timeTask = mRealmHelper.addTimeTask(new TimeTask());
        startTaskEditActivity(timeTask.getTaskId());
    }

    private void startTaskEditActivity(String taskId){
        Intent intent = TaskEditActivity.newIntent(getActivity(), taskId);
        startActivity(intent);
    }

    private class TasksHolder extends RecyclerView.ViewHolder{
        private TimeTask mTimeTask;
        private ListItemTaskBinding mItemTaskBinding;

        public TasksHolder(@NonNull ListItemTaskBinding binding) {
            super(binding.getRoot());
            mItemTaskBinding = binding;
            mItemTaskBinding.setTask(new TimeTaskViewModel(getActivity()));
        }


        public void bind(TimeTask timeTask){
            mTimeTask = timeTask;
            mItemTaskBinding.getTask().setTimeTask(timeTask);
            mItemTaskBinding.itemLinerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startTaskEditActivity(mTimeTask.getTaskId());
                }
            });
            mItemTaskBinding.executePendingBindings();
        }
    }

    private class TasksAdapter extends RecyclerView.Adapter<TasksHolder>{
        private List<TimeTask> mTimeTasks;

        public TasksAdapter(List<TimeTask> timeTasks) {
            mTimeTasks = timeTasks;
        }

        @Override
        public TasksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            ListItemTaskBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_task, parent, false);
            return new TasksHolder(binding);
        }

        @Override
        public void onBindViewHolder(TasksHolder holder, int position) {
            holder.bind(mTimeTasks.get(position));
        }

        @Override
        public int getItemCount() {
            return mTimeTasks.size();
        }
    }
}
