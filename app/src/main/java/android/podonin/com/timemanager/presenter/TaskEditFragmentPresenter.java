package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
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

    public void onCategoryClick(Category category){
        if(category == null){
            mFragmentView.setSubcategoriesVisibility(false);
        } else {
            mFragmentView.setSubcategoriesVisibility(true);
            List<Subcategory> subcategories = mRealmHelper.getSubcategories(category);
            mFragmentView.setSubcategories(subcategories, mTimeTask);
        }
    }

}
