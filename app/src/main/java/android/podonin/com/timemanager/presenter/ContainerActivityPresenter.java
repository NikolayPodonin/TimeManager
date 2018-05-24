package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.ActivityView;
import android.support.annotation.NonNull;

import java.util.Calendar;

public class ContainerActivityPresenter {

    @NonNull
    private ActivityView mLayoutView;
    private final RealmHelper mRealmHelper;

    public ContainerActivityPresenter(@NonNull ActivityView view){
        mLayoutView = view;
        mRealmHelper = RealmHelper.getInstance();
    }

    public void dispatchCreate(){
        createExampleData();
        mLayoutView.showTasksFragment();
    }

    public void dispatchDestroy(){
        mLayoutView = null;
        mRealmHelper.clear();
    }


    private void createExampleData(){
        RealmHelper mRealmHelper = RealmHelper.getInstance();

        for (Category cat : Category.values()) {
            for(Integer i = 0; i < 4; i++){
                Subcategory sc = new Subcategory();
                sc.setCategory(cat);
                sc.setName(i.toString() + "_" + cat.name());
                mRealmHelper.insert(sc);
            }
        }

        for (Subcategory sc: mRealmHelper.getAll(Subcategory.class)) {
            TimeTask timeTask = new TimeTask();
            timeTask.setStartDate(CalendarUtils.today());
            timeTask.setTaskBody(sc.getName());
            mRealmHelper.insert(timeTask);
            TaskSubcategoryEfficiency subcategoryEfficiency = new TaskSubcategoryEfficiency(timeTask, sc);
            mRealmHelper.insert(subcategoryEfficiency);
        }
    }
}
