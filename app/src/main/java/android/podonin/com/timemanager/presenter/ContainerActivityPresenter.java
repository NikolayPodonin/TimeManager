package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Efficiency;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
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

    public void onFirstStart(@NonNull String bodyFirstSub, @NonNull String businessFirstSub,
                             @NonNull String spiritFirstSub, @NonNull String relationFirstSub,
                             @NonNull String firstTaskName, long dayMillis) {
        for (Category cat : Category.values()) {
            Subcategory sub = new Subcategory();
            sub.setCategory(cat);
            switch (cat){
                case Body:
                    sub.setName(bodyFirstSub);
                    break;
                case Business:
                    sub.setName(businessFirstSub);
                    break;
                case Spirit:
                    sub.setName(spiritFirstSub);
                    break;
                case Relationships:
                    sub.setName(relationFirstSub);
                    break;
                default:
            }
            mRealmHelper.insert(sub);
        }
        TimeTask timeTask = new TimeTask();
        timeTask.setStartDate(dayMillis);
        timeTask.setTaskBody(firstTaskName);
        timeTask.setDone(true);
        mRealmHelper.insert(timeTask);

        timeTask = mRealmHelper.get(TimeTask.class);

        for (Subcategory sub : mRealmHelper.getAll(Subcategory.class)) {
            TaskSubcategoryEfficiency efficiency = new TaskSubcategoryEfficiency();
            efficiency.setSubcategory(sub);
            efficiency.setEfficiency(Efficiency.seven);
            efficiency.setTimeTask(timeTask);
            mRealmHelper.insert(efficiency);
            timeTask.getSubcategoryEfficiencies().add(efficiency);
        }

        mRealmHelper.insert(timeTask);
    }
}
