package android.podonin.com.timemanager.viewmodel;

import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.Repository;

/**
 * Created by Ybr on 17.03.2018.
 */

public class TimeTaskViewModel {
    private TimeTask mTimeTask;

    public TimeTask getTimeTask() {
        return mTimeTask;
    }

    public void setTimeTask(TimeTask timeTask) {
        mTimeTask = timeTask;
    }

    public String getTaskBody(){
        return mTimeTask.getTaskBody();
    }

    public String getTaskDate(){
        return mTimeTask.getStartDate().toString();
    }

    //for test
    public String getTaskSubcategoryEfficiency() {
        return  mTimeTask.getSubcategoryEfficiencies().first().getSubcategory().getName() + ", " + mTimeTask.getSubcategoryEfficiencies().first().getEfficiency().toString();
    }
}
