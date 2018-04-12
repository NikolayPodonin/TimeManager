package android.podonin.com.timemanager.adapter;

import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.model.TimeTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nic Podonin
 */

public class TimeTaskAdapter  extends RecyclerView.Adapter<TimeTaskAdapter.AvatarListViewHolder> {

    @NonNull
    private final List<TimeTask> mData = new ArrayList<>();


    public void setData(@NonNull List<TimeTask> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AvatarListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_task_adapter, parent, false);
        return new AvatarListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarListViewHolder holder, int position) {
        holder.bindView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class AvatarListViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        AvatarListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text);
        }

        void bindView(@NonNull TimeTask data) {
            mTextView.setText(data.getTaskBody());
        }
    }
}

