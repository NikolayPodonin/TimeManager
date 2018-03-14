package android.podonin.com.timemanager.model;

import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 14.03.2018.
 */

public class TimeTask {
    private String mTaskBody;
    private Date mStartDate;
    private Period mPeriod;
    private Map<Subcategory, Efficiency> mSubcategoryEfficiencyMap;
    private Boolean mIsNeedAlarm;
    private Date mAlarmTime;

    public TimeTask(){
        mSubcategoryEfficiencyMap = new HashMap<>();
    }

    public String getTaskBody() {
        return mTaskBody;
    }

    public TimeTask setTaskBody(String taskBody) {
        mTaskBody = taskBody;
        return this;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public TimeTask setStartDate(Date startDate) {
        mStartDate = startDate;
        return this;
    }

    public Period getPeriod() {
        return mPeriod;
    }

    public TimeTask setPeriod(Period period) {
        mPeriod = period;
        return this;
    }

    public Map<Subcategory, Efficiency> getSubcategoryEfficiencyMap() {
        return mSubcategoryEfficiencyMap;
    }

    public TimeTask setSubcategoryEfficiencyMap(Map<Subcategory, Efficiency> subcategoryEfficiencyMap) {
        mSubcategoryEfficiencyMap = subcategoryEfficiencyMap;
        return this;
    }

    public Boolean getNeedAlarm() {
        return mIsNeedAlarm;
    }

    public TimeTask setNeedAlarm(Boolean needAlarm) {
        mIsNeedAlarm = needAlarm;
        return this;
    }

    public Date getAlarmTime() {
        return mAlarmTime;
    }

    public TimeTask setAlarmTime(Date alarmTime) {
        mAlarmTime = alarmTime;
        return this;
    }
}
