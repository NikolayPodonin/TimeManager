package android.podonin.com.timemanager;

import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Efficiency;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.Repository;
import android.support.v4.app.Fragment;

import java.util.Calendar;

import io.realm.Realm;

/**
 * Created by Ybr on 16.03.2018.
 */

public class TasksActivity extends SingleFragmentActivity {

    Repository mRepository;

    @Override
    protected Fragment createFragment() {

        mRepository = new Repository();

        for (Category cat : Category.values()) {
            for(Integer i = 0; i < 4; i++){
                Subcategory sc = new Subcategory();
                sc.setCategory(cat);
                sc.setName(i.toString());
                mRepository.addSubcategory(sc);
            }
        }

        for (Subcategory sc: mRepository.getAllSubcategories()) {
            TimeTask timeTask = new TimeTask();
            timeTask.setStartDate(Calendar.getInstance().getTime());
            timeTask.setTaskBody(sc.getName());
            final TimeTask t = mRepository.addTimeTask(timeTask);
            TaskSubcategoryEfficiency subcategoryEfficiency = new TaskSubcategoryEfficiency(t, sc);
            mRepository.addTaskSubcategoryEfficiency(subcategoryEfficiency);
        }

        return TasksFragment.newInstance();
    }
}
