package android.podonin.com.timemanager.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ybr on 14.03.2018.
 */

public class TaskSubcategoryEfficiency extends RealmObject {
    @PrimaryKey
    private long mId;
    private TimeTask mTimeTask;
    private Subcategory mSubcategory;
    private String mEfficiency;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
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
