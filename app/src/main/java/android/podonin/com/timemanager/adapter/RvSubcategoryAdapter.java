package android.podonin.com.timemanager.adapter;

import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.model.Efficiency;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RvSubcategoryAdapter extends RecyclerView.Adapter<RvSubcategoryAdapter.SubcategoriesHolder> {

    public interface OnSaveChangesListener{
        void onSaveChanges(List<TaskSubcategoryEfficiency> changed, List<TaskSubcategoryEfficiency> deleted);
    }

    private List<Subcategory> mSubcategories = new ArrayList<>();

    private OnSaveChangesListener mOnSaveChangesListener;
    //TODO заполнить массивы по мере изменения состояний view holder'ов
    private List<TaskSubcategoryEfficiency> mOldTseList = new ArrayList<>();
    private List<TaskSubcategoryEfficiency> mChangedTseList = new ArrayList<>();
    private List<TaskSubcategoryEfficiency> mDeletedTseList = new ArrayList<>();


    public void setOnSaveChangesListener(OnSaveChangesListener onSaveChangesListener) {
        mOnSaveChangesListener = onSaveChangesListener;
    }

    public void saveChanges(){
        mOnSaveChangesListener.onSaveChanges(mChangedTseList, mDeletedTseList);
    }

    public void setData(List<Subcategory> subcategories, List<TaskSubcategoryEfficiency> efficiencies) {
        mSubcategories.clear();
        mSubcategories.addAll(subcategories);
        mOldTseList.clear();
        mOldTseList.addAll(efficiencies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubcategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_item_subcategory_efficiency, parent, false);
        return new SubcategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoriesHolder holder, int position) {
        TaskSubcategoryEfficiency efficiency = null;
        for (TaskSubcategoryEfficiency ef : mChangedTseList) {
            if (mSubcategories.get(position).getSubcategoryId().equals(ef.getSubcategory().getSubcategoryId())){
                efficiency = ef;
                break;
            }
        }
        if (efficiency == null){
            for (TaskSubcategoryEfficiency ef : mOldTseList) {
                if (mSubcategories.get(position).getSubcategoryId().equals(ef.getSubcategory().getSubcategoryId())){
                    efficiency = ef;
                    break;
                }
            }
        }
        if (efficiency != null && mDeletedTseList.contains(efficiency)){
            efficiency = null;
        }
        holder.bind(mSubcategories.get(position), efficiency);
    }

    @Override
    public int getItemCount() {
        return mSubcategories.size();
    }

    class SubcategoriesHolder extends RecyclerView.ViewHolder{
        TextView mSubcategoryName;
        SeekBar mEfficiencySeekBar;
        CheckBox mIsAddedToTask;
        Subcategory mSubcategory;
        TaskSubcategoryEfficiency mEfficiency;

        SubcategoriesHolder(View itemView) {
            super(itemView);
            mSubcategoryName = itemView.findViewById(R.id.subcategory_name_text_view);
            mEfficiencySeekBar = itemView.findViewById(R.id.efficiency_seek_bar);
            mIsAddedToTask = itemView.findViewById(R.id.is_added_check_box);
        }

        void bind(Subcategory subcategory, TaskSubcategoryEfficiency efficiency){
            mSubcategory = subcategory;
            mSubcategoryName.setText(mSubcategory.getName());

            mEfficiency = efficiency;
            mEfficiencySeekBar.setVisibility(View.GONE);

            mIsAddedToTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onIsAddedCheckChange(isChecked);
                }
            });

            mEfficiencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser){
                        if (!mChangedTseList.contains(mEfficiency)){
                            mChangedTseList.add(mEfficiency);
                        }
                        mEfficiency.setEfficiency(Efficiency.getEfficiency(progress));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            });

            if(efficiency != null){
                mIsAddedToTask.setChecked(true);
                mEfficiencySeekBar.setVisibility(View.VISIBLE);
            } else {
                mIsAddedToTask.setChecked(false);
            }
        }

        private void onIsAddedCheckChange(boolean isChecked){
            if(isChecked){
                if(mEfficiency == null){
                    mEfficiency = new TaskSubcategoryEfficiency();
                    mEfficiency.setSubcategory(mSubcategory);
                    mChangedTseList.add(mEfficiency);
                } else {
                    mEfficiencySeekBar.setProgress(mEfficiency.getEfficiency().ordinal());
                    if (mDeletedTseList.contains(mEfficiency)){
                        mDeletedTseList.remove(mEfficiency);
                    }
                }
                mEfficiencySeekBar.setVisibility(View.VISIBLE);
            } else {
                mEfficiencySeekBar.setVisibility(View.GONE);
                if (mOldTseList.contains(mEfficiency)){
                    if (!mDeletedTseList.contains(mEfficiency)){
                        mDeletedTseList.add(mEfficiency);
                    }
                }
            }
        }
    }
}
