package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.TasksActivityView;
import android.support.annotation.NonNull;

import java.util.Calendar;

public class TasksActivityPresenter {

    @NonNull
    private TasksActivityView mLayoutView;

    public TasksActivityPresenter(@NonNull TasksActivityView view){
        mLayoutView = view;
    }

    public void dispatchCreate(){
        createExampleData();
        mLayoutView.showTasksFragment();
    }


    private void createExampleData(){
        RealmHelper mRealmHelper = new RealmHelper();

        for (Category cat : Category.values()) {
            for(Integer i = 0; i < 4; i++){
                Subcategory sc = new Subcategory();
                sc.setCategory(cat);
                sc.setName(i.toString());
                mRealmHelper.addSubcategory(sc);
            }
        }

        for (Subcategory sc: mRealmHelper.getAllSubcategories()) {
            TimeTask timeTask = new TimeTask();
            timeTask.setStartDate(Calendar.getInstance().getTimeInMillis());
            timeTask.setTaskBody(sc.getName());
            final TimeTask t = mRealmHelper.addTimeTask(timeTask);
            TaskSubcategoryEfficiency subcategoryEfficiency = new TaskSubcategoryEfficiency(t, sc);
            mRealmHelper.addTaskSubcategoryEfficiency(subcategoryEfficiency);
        }
    }
}
