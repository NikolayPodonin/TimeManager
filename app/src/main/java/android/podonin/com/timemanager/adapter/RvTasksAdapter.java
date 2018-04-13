package android.podonin.com.timemanager.adapter;

import android.databinding.DataBindingUtil;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.databinding.ListItemTaskBinding;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.viewmodel.TimeTaskViewModel;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RvTasksAdapter extends RecyclerView.Adapter<RvTasksAdapter.TasksHolder> {

    public interface OnClickListener{
        void onClick(View v, String taskId);
    }

    private List<TimeTask> mTimeTasks;
    private OnClickListener mItemOnClickListener;

    public RvTasksAdapter(List<TimeTask> timeTasks) {
        mTimeTasks = timeTasks;
    }

    @NonNull
    @Override
    public TasksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
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


    public void setItemOnClickListener(OnClickListener itemOnClickListener) {
        mItemOnClickListener = itemOnClickListener;
    }


    class TasksHolder extends RecyclerView.ViewHolder{
        private TimeTask mTimeTask;
        private ListItemTaskBinding mItemTaskBinding;

        public TasksHolder(@NonNull ListItemTaskBinding binding) {
            super(binding.getRoot());
            mItemTaskBinding = binding;
            mItemTaskBinding.setTask(new TimeTaskViewModel(binding.getRoot().getContext()));
        }


        public void bind(final TimeTask timeTask){
            mTimeTask = timeTask;
            mItemTaskBinding.getTask().setTimeTask(timeTask);
            if (mItemOnClickListener != null){
                mItemTaskBinding.itemLinerLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemOnClickListener.onClick(v, timeTask.getTaskId());
                    }
                });
            }
            mItemTaskBinding.executePendingBindings();
        }
    }

}
