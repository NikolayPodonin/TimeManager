package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;

public class TaskEditFragmentPresenter {
    private TimeTask mTimeTask;
    private RealmHelper mRealmHelper;

    public TaskEditFragmentPresenter(String timeTaskId) {
        mRealmHelper = new RealmHelper();
        mTimeTask = mRealmHelper.getTimeTask(timeTaskId);
    }


}
