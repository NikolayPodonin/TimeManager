package android.podonin.com.timemanager.adapter;

import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
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

    List<Subcategory> mSubcategories = new ArrayList<>();
    TimeTask mTimeTask;

    public void setData(List<Subcategory> subcategories, TimeTask timeTask) {
        mSubcategories.clear();
        mSubcategories.addAll(subcategories);
        mTimeTask = timeTask;
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
        holder.bind(mSubcategories.get(position));
    }

    @Override
    public int getItemCount() {
        return mSubcategories.size();
    }

    class SubcategoriesHolder extends RecyclerView.ViewHolder{
        TextView mSubcategoryName;
        SeekBar mEfficiency;
        CheckBox mIsAddedToTask;
        Subcategory mSubcategory;

        SubcategoriesHolder(View itemView) {
            super(itemView);
            mSubcategoryName = itemView.findViewById(R.id.subcategory_name_text_view);
            mEfficiency = itemView.findViewById(R.id.efficiency_seek_bar);
            mIsAddedToTask = itemView.findViewById(R.id.is_added_check_box);
        }

        void bind(Subcategory subcategory){
            mEfficiency.setVisibility(View.GONE);

            mIsAddedToTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        mEfficiency.setVisibility(View.VISIBLE);
                    } else {
                        mEfficiency.setVisibility(View.GONE);
                    }
                }
            });

            for (TaskSubcategoryEfficiency efficiency: mTimeTask.getSubcategoryEfficiencies()) {
                if(efficiency.getSubcategory().getSubcategoryId().equals(subcategory.getSubcategoryId())){
                    mIsAddedToTask.setChecked(true);
                    mEfficiency.incrementProgressBy(efficiency.getEfficiency().ordinal());
                } else {
                    mIsAddedToTask.setChecked(false);
                }
            }

            mSubcategory = subcategory;
            mSubcategoryName.setText(mSubcategory.getName());
        }
    }
}
