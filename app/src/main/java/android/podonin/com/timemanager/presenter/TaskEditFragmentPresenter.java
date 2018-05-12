package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.TaskEditFragmentView;

import java.util.List;

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
            mFragmentView.showTaskDate(CalendarUtils.toDateString(mFragmentView.getFragmentContext(), mTimeTask.getStartDate()));
            mFragmentView.setDone(mTimeTask.isDone());
        }
    }

    public void dispatchDestroy(){
        mFragmentView = null;
    }

    public void onCategoryChoose(Category category, boolean isCashEmpty){
        if(category == null){
            mFragmentView.setSubcategoriesVisibility(false);
        } else if(isCashEmpty) {
            mFragmentView.setSubcategoriesVisibility(true);
            List<Subcategory> subcategories = mRealmHelper.getSubcategoriesFromCategory(category);
            mFragmentView.showSubcategories(subcategories, mTimeTask.getSubcategoryEfficiencies());
        } else {
            mFragmentView.setSubcategoriesVisibility(true);
            mFragmentView.showSubcategoriesFromCash(category);
        }
    }

    public void onSaveTaskSubcategoryEfficiencies(List<TaskSubcategoryEfficiency> changed, List<TaskSubcategoryEfficiency> deleted, List<TaskSubcategoryEfficiency> added) {
        //TODO сохранить или удалить из БД объекты всех массивов
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

    public void onSaveExit() {
        mFragmentView.saveChanges();
    }
}
