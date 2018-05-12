package android.podonin.com.timemanager.adapter.subcategoriesadapter;

import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.model.Subcategory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

class SubcategoryHolder extends RecyclerView.ViewHolder{
    private TextView mSubcategoryName;
    private SeekBar mEfficiencySeekBar;
    private CheckBox mIsAddedToTask;
    private Subcategory mSubcategory;
    private TseState mTseState;

    SubcategoryHolder(View itemView) {
        super(itemView);
        mSubcategoryName = itemView.findViewById(R.id.subcategory_name_text_view);
        mEfficiencySeekBar = itemView.findViewById(R.id.efficiency_seek_bar);
        mIsAddedToTask = itemView.findViewById(R.id.is_added_check_box);
    }

    void bind(Subcategory subcategory, TseState tseState){
        mSubcategory = subcategory;
        mSubcategoryName.setText(mSubcategory.getName());

        mTseState = tseState;
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
                    mTseState.efficiencyWasChanged(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        if(tseState.getTse() != null && !tseState.isDeleted()){
            mIsAddedToTask.setChecked(true);
            mEfficiencySeekBar.setVisibility(View.VISIBLE);
        } else {
            mIsAddedToTask.setChecked(false);
        }
    }

    private void onIsAddedCheckChange(boolean isChecked){
        mTseState.tseWasChecked(isChecked, mSubcategory);
        if(isChecked){
            mEfficiencySeekBar.setProgress(mTseState.getEfficiencyInt());
            mEfficiencySeekBar.setVisibility(View.VISIBLE);
        } else {
            mEfficiencySeekBar.setVisibility(View.GONE);
        }
    }
}