package android.podonin.com.timemanager;

import android.os.Bundle;
import android.podonin.com.timemanager.model.TimeTask;
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


    private RecyclerView mRecyclerView;
    private List<TimeTask> mTimeTasks = new ArrayList<>();

    public static TasksFragment newInstance(){
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        mRecyclerView = view.findViewById(R.id.tasks_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(isAdded()){
            mRecyclerView.setAdapter(new TasksAdapter(mTimeTasks));
        }


        return view;
    }

    private class TasksHolder extends RecyclerView.ViewHolder{
        private TextView mTaskBody;
        private TextView mTaskDate;
        private TimeTask mTimeTask;

        public TasksHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));

            mTaskBody = (TextView) itemView.findViewById(R.id.task_body);
            mTaskDate = (TextView) itemView.findViewById(R.id.task_date);
        }


        public void bind(TimeTask timeTask){
            mTimeTask = timeTask;
            mTaskBody.setText(timeTask.getTaskBody());
            mTaskDate.setText(timeTask.getStartDate().toString());
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
            return new TasksHolder(layoutInflater, parent);
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
