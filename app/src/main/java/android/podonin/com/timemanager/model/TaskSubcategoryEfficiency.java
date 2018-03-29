package android.podonin.com.timemanager.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Ybr on 14.03.2018.
 */

public class TaskSubcategoryEfficiency extends RealmObject {
    private static final String TASK_SUB_EFFICIENCY_ID = "mTaskSubEfficiencyId";
    public static final String TASK_FIELD = "mTimeTask";
    public static final String SUBCATEGORY_FIELD = "mSubcategory";
    public static final String EFFICIENCIES_FIELD = "mEfficiency";

    @PrimaryKey
    @Required
    private String mTaskSubEfficiencyId;
    private TimeTask mTimeTask;
    private Subcategory mSubcategory;
    private String mEfficiency;

    public TaskSubcategoryEfficiency(){
        mTaskSubEfficiencyId = UUID.randomUUID().toString();
    }

    public TaskSubcategoryEfficiency(TimeTask timeTask, Subcategory subcategory) {
        mTaskSubEfficiencyId = UUID.randomUUID().toString();
        mTimeTask = timeTask;
        mSubcategory = subcategory;
        mEfficiency = Efficiency.zero.toString();
    }

    public String getTaskSubEfficiencyId() {
        return mTaskSubEfficiencyId;
    }

    public void setTaskSubEfficiencyId(String taskSubEfficiencyId) {
        mTaskSubEfficiencyId = taskSubEfficiencyId;
    }

    public TimeTask getTimeTask() {
        return mTimeTask;
    }

    public void setTimeTask(TimeTask timeTask) {
        mTimeTask = timeTask;
    }

    public Subcategory getSubcategory() {
        return mSubcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        mSubcategory = subcategory;
    }

    public Efficiency getEfficiency() {
        return Efficiency.valueOf(mEfficiency);
    }

    public void setEfficiency(Efficiency efficiency) {
        mEfficiency = efficiency.toString();
    }
}
