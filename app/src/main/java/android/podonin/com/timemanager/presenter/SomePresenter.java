package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.SomeView;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author Nic Podonin
 */

public class SomePresenter {

    @NonNull
    private RealmHelper mRealmHelper;

    private SomeView mSomeView;

    public SomePresenter(@NonNull RealmHelper realmHelper) {
        mRealmHelper = realmHelper;
    }

    public void setSomeView(@NonNull SomeView view){
        mSomeView = view;
    }

    public void dispatchCreate(){
        try {
            List<TimeTask> timeTasks = mRealmHelper.getAllTimeTasks();
            if(timeTasks != null){
                mSomeView.showTimeTasks(timeTasks);
            }
        }catch (Exception e){
            e.printStackTrace();
            mSomeView.showError();

        }
    }

    public void dispatchDestroy(){
        mSomeView = null;
    }
}
