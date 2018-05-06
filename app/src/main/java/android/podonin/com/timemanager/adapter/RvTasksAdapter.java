package android.podonin.com.timemanager.adapter;

import android.content.Context;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
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

    private List<TimeTask> mTimeTasks = new ArrayList<>();
    private OnClickListener mOnItemClickListener;

    public void setData(List<TimeTask> timeTasks) {
        mTimeTasks.clear();
        mTimeTasks.addAll(timeTasks);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TasksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_item_task, parent, false);
        return new TasksHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksHolder holder, int position) {
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
        private TextView mEfficiencyView;
        private View mItemLayout;

        public TasksHolder(@NonNull View itemView) {
            super(itemView);
            mBodyView = itemView.findViewById(R.id.task_body);
            mDateView = itemView.findViewById(R.id.task_date);
            mEfficiencyView = itemView.findViewById(R.id.efficiency);
            mItemLayout = itemView.findViewById(R.id.item_liner_layout);
        }

        public void bind(final TimeTask timeTask){
            mTimeTask = timeTask;
            mItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(v, mTimeTask.getTaskId());
                    }
                }
            });
            mBodyView.setText(mTimeTask.getTaskBody());
            mDateView.setText(CalendarUtils.toDateString(getContext(), mTimeTask.getStartDate()));
            mEfficiencyView.setText(mTimeTask.getSubcategoryEfficiencies().first().getEfficiency().toString());
        }

        public Context getContext() {
            return mItemLayout != null ? mItemLayout.getContext() : null;
        }
    }

}
