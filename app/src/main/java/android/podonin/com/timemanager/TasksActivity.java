package android.podonin.com.timemanager;

import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.support.v4.app.Fragment;

import java.util.Calendar;

/**
 * Created by Ybr on 16.03.2018.
 */

public class TasksActivity extends SingleFragmentActivity {

    RealmHelper mRealmHelper;

    @Override
    protected Fragment createFragment() {

        mRealmHelper = new RealmHelper();

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

        return TasksFragment.newInstance();
    }
}
