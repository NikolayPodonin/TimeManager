package android.podonin.com.timemanager.presenter;

import android.graphics.Color;
import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.DiagramFragmentView;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class DiagramFragmentPresenter {
    @NonNull
    private DiagramFragmentView mLayoutView;
    private final RealmHelper mRealmHelper;

    private ArrayList<TaskSubcategoryEfficiency> mBody = new ArrayList<>();
    private ArrayList<TaskSubcategoryEfficiency> mBusiness = new ArrayList<>();
    private ArrayList<TaskSubcategoryEfficiency> mSpirit = new ArrayList<>();
    private ArrayList<TaskSubcategoryEfficiency> mRelation = new ArrayList<>();

    private int mStringResId[] = { R.string.body, R.string.business, R.string.spirit, R.string.relationships };
    private int mColorResIds[] = { R.color.color_body, R.color.color_business, R.color.color_spirit, R.color.color_relation};

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
                default:
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

        int resId = 0;
        boolean usePercent = false;

        switch (page) {
            case 0:
                body = mBody.size();
                business = mBusiness.size();
                spirit = mSpirit.size();
                relation = mRelation.size();
                resId = R.string.first_diagram_description;
                usePercent = true;
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
                resId = R.string.third_diagram_description;
                usePercent = true;
                break;
            case 2:
                if (mBody.size() != 0){
                    for (TaskSubcategoryEfficiency tse : mBody) {
                        body += tse.getEfficiency().ordinal();
                    }
                    body = body / mBody.size();
                }
                if (mBusiness.size() != 0) {
                    for (TaskSubcategoryEfficiency tse : mBusiness) {
                        business += tse.getEfficiency().ordinal();
                    }
                    business = business / mBusiness.size();
                }
                if (mSpirit.size() != 0) {
                    for (TaskSubcategoryEfficiency tse : mSpirit) {
                        spirit += tse.getEfficiency().ordinal();
                    }
                    spirit = spirit / mSpirit.size();
                }
                if (mRelation.size() != 0) {
                    for (TaskSubcategoryEfficiency tse : mRelation) {
                        relation += tse.getEfficiency().ordinal();
                    }
                    relation = relation / mRelation.size();
                }
                resId = R.string.second_diagram_description;
                usePercent = false;
                break;
            default:
        }

        float values[] = {body, business, spirit, relation};
        mLayoutView.setData(values, mStringResId, mColorResIds, R.string.categories, resId, usePercent);
    }
}
