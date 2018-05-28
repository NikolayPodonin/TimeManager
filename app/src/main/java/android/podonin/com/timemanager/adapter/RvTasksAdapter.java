package android.podonin.com.timemanager.adapter;

import android.content.Context;
import android.os.Build;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.utilites.CalendarUtils;
import android.podonin.com.timemanager.model.TimeTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RvTasksAdapter extends RecyclerView.Adapter<RvTasksAdapter.TasksHolder> {


    public interface OnClickListener{
        void onClick(View v, String taskId);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, String taskId);
    }

    private List<TimeTask> mTimeTasks = new ArrayList<>();
    private OnClickListener mOnItemClickListener;
    private OnLongClickListener mOnLongItemClickListener;

    public void setData(List<TimeTask> timeTasks) {
        mTimeTasks.clear();
        mTimeTasks.addAll(timeTasks);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTimeTasks.sort((timeTask, t1) -> {
                if (timeTask.getTaskBody() != null && t1.getTaskBody() != null) {
                    return timeTask.getTaskBody().compareTo(t1.getTaskBody());
                } else {
                    return 0;
                }
            });
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(OnLongClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    @NonNull
    @Override
    public TasksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_item_task, parent, false);
        return new TasksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksHolder holder, int position) {
        holder.bind(mTimeTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTimeTasks.size();
    }


    class TasksHolder extends RecyclerView.ViewHolder {
        private TimeTask mTimeTask;
        private TextView mBodyView;
        private TextView mDateView;
        private View mItemLayout;

        TasksHolder(@NonNull View itemView) {
            super(itemView);
            mBodyView = itemView.findViewById(R.id.task_body);
            mDateView = itemView.findViewById(R.id.task_date);
            mItemLayout = itemView.findViewById(R.id.item_liner_layout);
        }

        void bind(@NonNull final TimeTask timeTask){
            mTimeTask = timeTask;
            mItemLayout.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(v, mTimeTask.getTaskId());
                }
            });
            mItemLayout.setOnLongClickListener(view -> {
                if (mOnLongItemClickListener != null){
                    mOnLongItemClickListener.onLongClick(view, mTimeTask.getTaskId());
                }
                return false;
            });
            mBodyView.setText(mTimeTask.getTaskBody());
            mDateView.setText(CalendarUtils.toDateString(getContext(), mTimeTask.getStartDate()));
    }

        public Context getContext() {
            return mItemLayout != null ? mItemLayout.getContext() : null;
        }
    }

}
