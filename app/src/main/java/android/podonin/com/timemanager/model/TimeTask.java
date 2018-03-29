package android.podonin.com.timemanager.model;

import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by User on 14.03.2018.
 */

public class TimeTask extends RealmObject{
    private static final String TASK_ID = "mTaskId";
    public static final String TASK_BODY_FIELD = "mTaskBody";
    public static final String START_DATE_FIELD = "mStartDate";
    public static final String SUBCATEGORY_EFFICIENCIES_FIELD = "mSubcategoryEfficiencies";

    @PrimaryKey
    @Required
    private String mTaskId;
    private String mTaskBody;
    private Date mStartDate;
    private RealmList<TaskSubcategoryEfficiency> mSubcategoryEfficiencies;

    public TimeTask() {
        mTaskId = UUID.randomUUID().toString();
    }

    public String getTaskId() {
        return mTaskId;
    }

    public void setTaskId(String taskId) {
        mTaskId = taskId;
    }

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
