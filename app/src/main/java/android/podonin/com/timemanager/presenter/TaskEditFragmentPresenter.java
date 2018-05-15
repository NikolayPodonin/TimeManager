package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
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

    public TaskEditFragmentPresenter(String timeTaskId, RealmHelper realmHelper) {
        mRealmHelper = realmHelper;
        mTimeTask = mRealmHelper.getTimeTask(timeTaskId);
    }

    public void setFragmentView(TaskEditFragmentView fragmentView) {
        mFragmentView = fragmentView;
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
            List<Subcategory> subcategories = new ArrayList<>();
            subcategories.addAll(mRealmHelper.getSubcategoriesFromCategory(category));
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
        for (TaskSubcategoryEfficiency ch : changed) {
            mRealmHelper.changeTaskSubcategoryEfficiency(ch);
        }
        for (TaskSubcategoryEfficiency de : deleted) {
            mRealmHelper.deleteTaskSubcategoryEfficiency(de);
        }
        for (TaskSubcategoryEfficiency ne : added) {
            ne.setTimeTask(mTimeTask);
            mRealmHelper.addTaskSubcategoryEfficiency(ne);
        }
    }

    public void onSaveTask(@Nullable final String taskBody, final long taskDate, final boolean checked) {
        if (taskBody == null || taskBody.isEmpty() || taskBody.equals("")) {
            if (mTimeTask.getSubcategoryEfficiencies() == null || mTimeTask.getSubcategoryEfficiencies().size() == 0){
                mRealmHelper.deleteTimeTask(mTimeTask.getTaskId());
            } else {
                mFragmentView.showEmptyBodyMessage();
            }
        } else {
            mTimeTask.getRealm().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    mTimeTask.setStartDate(taskDate);
                    mTimeTask.setTaskBody(taskBody);
                    mTimeTask.setDone(checked);
                }
            });
        }
    }

    public void onExit(boolean withOkButton) {
        if (withOkButton) {
            mFragmentView.saveChanges();
        }
        mFragmentView.exit();
    }
}
