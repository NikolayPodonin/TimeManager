package android.podonin.com.timemanager.presenter;

import android.graphics.Color;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.model.Efficiency;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.DiagramFragmentView;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class DiagramFragmentPresenter {
    private enum KindOfDiagram { amountTasks, amountEfficiency, averageEfficiency}

    @NonNull
    private DiagramFragmentView mLayoutView;
    private final RealmHelper mRealmHelper;

    private ArrayList<TaskSubcategoryEfficiency> mBody = new ArrayList<>();
    private ArrayList<TaskSubcategoryEfficiency> mBusiness = new ArrayList<>();
    private ArrayList<TaskSubcategoryEfficiency> mSpirit = new ArrayList<>();
    private ArrayList<TaskSubcategoryEfficiency> mRelation = new ArrayList<>();

    private int mStringResId[] = { R.string.body, R.string.business, R.string.spirit, R.string.relationships };
    private int mColors[] = { Color.GREEN, Color.YELLOW, Color.BLUE, Color.RED };

    public DiagramFragmentPresenter(@NonNull DiagramFragmentView view){
        mLayoutView = view;
        mRealmHelper = RealmHelper.getInstance();
    }

    public void dispatchCreate(){
        ArrayList<TimeTask> doneTasks = new ArrayList<>(mRealmHelper.getAll(TimeTask.class, TimeTask.IS_DONE_FIELD, true));
        ArrayList<TaskSubcategoryEfficiency> allEfficiencies = new ArrayList<>();
        for (TimeTask task : doneTasks) {
            allEfficiencies.addAll(task.getSubcategoryEfficiencies());
        }
        for (TaskSubcategoryEfficiency efficiency : allEfficiencies){
            switch (efficiency.getSubcategory().getCategory()){
                case Body:
                    mBody.add(efficiency);
                    break;
                case Business:
                    mBusiness.add(efficiency);
                    break;
                case Spirit:
                    mSpirit.add(efficiency);
                    break;
                case Relationships:
                    mRelation.add(efficiency);
            }
        }
    }

    public void dispatchDestroy(){
        mLayoutView = null;
    }

    public void onChangeDiagram(int page){
        float body = 0;
        float business = 0;
        float spirit = 0;
        float relation = 0;

        switch (page) {
            case 0:
                body = mBody.size();
                business = mBusiness.size();
                spirit = mSpirit.size();
                relation = mRelation.size();
                break;
            case 1:
                for (TaskSubcategoryEfficiency tse : mBody) {
                    body += tse.getEfficiency().ordinal();
                }
                for (TaskSubcategoryEfficiency tse : mBusiness) {
                    business += tse.getEfficiency().ordinal();
                }
                for (TaskSubcategoryEfficiency tse : mSpirit) {
                    spirit += tse.getEfficiency().ordinal();
                }
                for (TaskSubcategoryEfficiency tse : mRelation) {
                    relation += tse.getEfficiency().ordinal();
                }
                break;
            case 2:
                for (TaskSubcategoryEfficiency tse : mBody) {
                    body += tse.getEfficiency().ordinal();
                }
                if (body != 0){
                    body = body / mBody.size();
                }
                for (TaskSubcategoryEfficiency tse : mBusiness) {
                    business += tse.getEfficiency().ordinal();
                }
                if (business != 0) {
                    business = business / mBusiness.size();
                }
                for (TaskSubcategoryEfficiency tse : mSpirit) {
                    spirit += tse.getEfficiency().ordinal();
                }
                if (spirit != 0) {
                    spirit = spirit / mSpirit.size();
                }
                for (TaskSubcategoryEfficiency tse : mRelation) {
                    relation += tse.getEfficiency().ordinal();
                }
                if (relation != 0) {
                    relation = relation / mRelation.size();
                }
                break;
        }

        float values[] = {body, business, spirit, relation};
        mLayoutView.setData(values, mStringResId, mColors);
    }
}
