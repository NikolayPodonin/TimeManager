package android.podonin.com.timemanager.viewmodel;

import android.content.Context;
import android.databinding.adapters.TextViewBindingAdapter;
import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.model.TimeTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.DatePicker;

/**
 * Created by Ybr on 17.03.2018.
 */

public class TimeTaskViewModel implements TextWatcher {
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
        return CalendarUtils.toDateString(mContext, mTimeTask.getStartDate());
    }

    public void setTaskDate(long taskDate){

    }

    //for test
    public String getTaskSubcategoryEfficiency() {
        return  mTimeTask.getSubcategoryEfficiencies().first().getSubcategory().getName() + ", " + mTimeTask.getSubcategoryEfficiencies().first().getEfficiency().toString();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setTaskBody(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}