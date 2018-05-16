package android.podonin.com.timemanager.adapter.subcategoriesadapter;

import android.podonin.com.timemanager.model.Efficiency;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.support.annotation.NonNull;

import io.realm.Realm;

class TseState {
    private TaskSubcategoryEfficiency mEfficiency;
    private boolean mIsChanged;
    private boolean mIsDeleted;
    private boolean mIsNew;

    TseState(TaskSubcategoryEfficiency subcategoryEfficiency){
        mEfficiency = subcategoryEfficiency;
        mIsChanged = false;
        mIsDeleted = false;
        mIsNew = false;
    }

    public TaskSubcategoryEfficiency getTse() {
        return mEfficiency;
    }

    public boolean isChanged() {
        return mIsChanged;
    }

    public boolean isDeleted() {
        return mIsDeleted;
    }

    public boolean isNew() {
        return mIsNew;
    }

    public void tseWasChecked(boolean isChecked, Subcategory subcategory){
        mIsDeleted = !isChecked;
        if (isChecked){
            if (mEfficiency == null){
                mEfficiency = new TaskSubcategoryEfficiency();
                mEfficiency.setSubcategory(subcategory);
                mIsNew = true;
            }
        } else {
            if (mIsNew){
                mEfficiency = null;
                mIsNew = false;
                mIsChanged = false;
                mIsDeleted = false;
            }
        }
    }

    public int getEfficiencyInt(){
        return mEfficiency.getEfficiency().ordinal();
    }

    public void efficiencyWasChanged(int newEfficiency){
        final Efficiency efficiency = Efficiency.getEfficiency(newEfficiency);
        if (!mIsNew){
            mEfficiency.getRealm().executeTransaction(realm -> mEfficiency.setEfficiency(efficiency));
            mIsChanged = true;
        } else {
            mEfficiency.setEfficiency(efficiency);
        }
    }
}
