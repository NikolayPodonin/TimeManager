package android.podonin.com.timemanager;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.podonin.com.timemanager.databinding.FragmentTasksBinding;
import android.podonin.com.timemanager.databinding.ListItemTaskBinding;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.Repository;
import android.podonin.com.timemanager.viewmodel.TimeTaskViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybr on 16.03.2018.
 */

public class TasksFragment extends Fragment {


    private List<TimeTask> mTimeTasks = new ArrayList<>();
    private Repository mRepository;

    public static TasksFragment newInstance(){
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRepository = new Repository();
        mTimeTasks = mRepository.getAllTimeTasks();

        FragmentTasksBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks, container, false);

        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(isAdded()){
            binding.tasksRecyclerView.setAdapter(new TasksAdapter(mTimeTasks));
        }

        return binding.getRoot();
    }

    private class TasksHolder extends RecyclerView.ViewHolder{
        private TimeTask mTimeTask;
        private ListItemTaskBinding mItemTaskBinding;

        public TasksHolder(ListItemTaskBinding binding) {
            super(binding.getRoot());
            mItemTaskBinding = binding;
            mItemTaskBinding.setTask(new TimeTaskViewModel());
        }


        public void bind(TimeTask timeTask){
            mTimeTask = timeTask;
            mItemTaskBinding.getTask().setTimeTask(timeTask);
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
