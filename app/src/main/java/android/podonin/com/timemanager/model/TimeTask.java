package android.podonin.com.timemanager.model;

import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by User on 14.03.2018.
 */

public class TimeTask extends RealmObject{
    private String mTaskBody;
    private Date mStartDate;
    private RealmList<TaskSubcategoryEfficiency> mSubcategoryEfficiencies;


    public String getTaskBody() {
        return mTaskBody;
    }

    public void setTaskBody(String taskBody) {
        mTaskBody = taskBody;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public RealmList<TaskSubcategoryEfficiency> getSubcategoryEfficiencies() {
        return mSubcategoryEfficiencies;
    }

    public void setSubcategoryEfficiencies(RealmList<TaskSubcategoryEfficiency> subcategoryEfficiencies) {
        mSubcategoryEfficiencies = subcategoryEfficiencies;
    }
}
