package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.TaskEditFragmentView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class TaskEditFragmentPresenter {
    private TimeTask mTimeTask;
    private RealmHelper mRealmHelper;
    private TaskEditFragmentView mFragmentView;
    private boolean mIsNeedToSave;
    private boolean mIsNeedToDelete;
    private boolean mIsOkClosed;

    public TaskEditFragmentPresenter(TaskEditFragmentView fragmentView, String timeTaskId) {
        mRealmHelper = RealmHelper.getInstance();
        mFragmentView = fragmentView;
        mTimeTask = mRealmHelper.get(TimeTask.class, TimeTask.TASK_ID, timeTaskId);
        mIsNeedToSave = false;
        mIsOkClosed = false;
        mIsNeedToDelete = (isEmptyBody(mTimeTask.getTaskBody())) &&
                (mTimeTask.getSubcategoryEfficiencies() == null ||
                        mTimeTask.getSubcategoryEfficiencies().size() == 0);
    }

    public void dispatchCreate() {
        if(mTimeTask != null && mFragmentView != null){
            mFragmentView.showTaskBody(mTimeTask.getTaskBody());
            mFragmentView.showTaskDate(mTimeTask.getStartDate());
            mFragmentView.setDone(mTimeTask.isDone());
        }
    }

    public void dispatchDestroy(){
        mFragmentView = null;
    }

    public void onCategoryChoose(@Nullable Category category, boolean isCashEmpty){
        if(category == null){
            mFragmentView.setSubcategoriesVisibility(false);
        } else if(isCashEmpty) {
            mFragmentView.setSubcategoriesVisibility(true);
            List<Subcategory> subcategories = new ArrayList<>(mRealmHelper.getAll(Subcategory.class, Subcategory.CATEGORY_FIELD, category.toString()));
            List<TaskSubcategoryEfficiency> tseList = new ArrayList<>();
            if (mTimeTask.getSubcategoryEfficiencies() != null){
                tseList.addAll(mTimeTask.getSubcategoryEfficiencies());
            }
            mFragmentView.showSubcategories(subcategories, tseList);
        } else {
            mFragmentView.setSubcategoriesVisibility(true);
            mFragmentView.showSubcategoriesFromCash(category);
        }
    }

    public void onSaveTaskSubcategoryEfficiencies(@NonNull List<TaskSubcategoryEfficiency> changed, @NonNull List<TaskSubcategoryEfficiency> deleted, @NonNull List<TaskSubcategoryEfficiency> added) {
        for (TaskSubcategoryEfficiency de : deleted) {
            mRealmHelper.delete(de.getClass(), TaskSubcategoryEfficiency.TASK_SUB_EFFICIENCY_ID, de.getTaskSubEfficiencyId());
        }
        for (TaskSubcategoryEfficiency ne : added) {
            ne.setTimeTask(mTimeTask);
            mRealmHelper.insertTaskSubcategoryEfficiency(ne);
        }
    }

    public void onSaveTask(@Nullable final String taskBody, final long taskDate, final boolean checked) {
        mTimeTask.getRealm().executeTransaction(realm -> {
            mTimeTask.setStartDate(taskDate);
            mTimeTask.setTaskBody(taskBody);
            mTimeTask.setDone(checked);
        });
    }

    private boolean isEmptyBody(@Nullable String taskBody) {
        return taskBody == null || taskBody.isEmpty() || taskBody.equals("");
    }

    public void onOkExit(){
        mIsOkClosed = true;
        mFragmentView.exit();
    }

    public void onBackExit(){
        mFragmentView.exit();
    }

    public void onExit() {
        mFragmentView.checkSomeChanges();
        int message = R.string.empty;
        if (mIsNeedToSave && mIsOkClosed){
                mFragmentView.saveChanges();
                message = R.string.changes_saved;
        } else if (mIsNeedToDelete) {
            mRealmHelper.delete(TimeTask.class, TimeTask.TASK_ID, mTimeTask.getTaskId());
            message = R.string.changes_not_saved;
        }
        mFragmentView.showMessage(message);
    }

    public void onCheckChanges(boolean isTseChanged, @NonNull final String taskBody, final long taskDate, final boolean isDone) {
        boolean body;
        if (isEmptyBody(taskBody)){
            body = !isEmptyBody(mTimeTask.getTaskBody());
        } else {
            body = !taskBody.equals(mTimeTask.getTaskBody());
        }
        boolean date = taskDate != mTimeTask.getStartDate();
        boolean done = isDone != mTimeTask.isDone();
        mIsNeedToSave = isTseChanged || body || date || done;
        if (mIsNeedToSave && !mIsOkClosed){
            mIsNeedToDelete = false;
        }
    }
}
