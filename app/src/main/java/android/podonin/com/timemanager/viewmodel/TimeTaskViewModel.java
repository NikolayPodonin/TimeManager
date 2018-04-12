package android.podonin.com.timemanager.viewmodel;

import android.content.Context;
import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.model.TimeTask;

/**
 * Created by Ybr on 17.03.2018.
 */

public class TimeTaskViewModel {
    private TimeTask mTimeTask;
    private Context mContext;

    public TimeTaskViewModel(Context context){
        mContext = context;
    }

    public TimeTask getTimeTask() {
        return mTimeTask;
    }

    public void setTimeTask(TimeTask timeTask) {
        mTimeTask = timeTask;
    }

    public String getTaskBody(){
        return mTimeTask.getTaskBody();
    }

    public void setTaskBody(String taskBody) {
        mTimeTask.setTaskBody(taskBody);
    }

    public String getTaskDate(){
        String date = CalendarUtils.toDateString(mContext, mTimeTask.getStartDate());
        return date;
    }

    public void setTaskDate(long taskDate){

    }
    //for test
    public String getTaskSubcategoryEfficiency() {
        return  mTimeTask.getSubcategoryEfficiencies().first().getSubcategory().getName() + ", " + mTimeTask.getSubcategoryEfficiencies().first().getEfficiency().toString();
    }
}
