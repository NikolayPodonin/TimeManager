package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.ContainerActivityView;
import android.support.annotation.NonNull;

public class ContainerActivityPresenter {

    @NonNull
    private ContainerActivityView mLayoutView;
    private final RealmHelper mRealmHelper;

    public ContainerActivityPresenter(@NonNull ContainerActivityView view){
        mLayoutView = view;
        mRealmHelper = RealmHelper.getInstance();
    }

    public void dispatchCreate(){
        mLayoutView.showDiagramFragment();
    }

    public void dispatchDestroy(){
        mLayoutView = null;
        mRealmHelper.clear();
    }


    public void onChooseTasksInMenu() {
        mLayoutView.showTasksFragment();
    }

    public void onChooseDiagramInMenu() {
        mLayoutView.showDiagramFragment();
    }
}
