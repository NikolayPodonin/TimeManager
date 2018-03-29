package android.podonin.com.timemanager.repository;

import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Ybr on 15.03.2018.
 */

public class Repository {
    Realm mRealm;

    public Repository(){
        mRealm = Realm.getDefaultInstance();
    }

    public List<Subcategory> getAllSubcategories(){
        return mRealm.where(Subcategory.class).findAll();
    }

    public Subcategory addSubcategory(Subcategory subcategory){
        mRealm.beginTransaction();
        Subcategory s = mRealm.copyToRealm(subcategory);
        mRealm.commitTransaction();
        return s;
    }

    public List<TimeTask> getAllTimeTasks(){
        return mRealm.where(TimeTask.class).findAll();
    }

    public TimeTask addTimeTask(TimeTask timeTask) {
        mRealm.beginTransaction();
        TimeTask tt = mRealm.copyToRealm(timeTask);
        mRealm.commitTransaction();
        return tt;
    }

    public TaskSubcategoryEfficiency addTaskSubcategoryEfficiency(TaskSubcategoryEfficiency subcategoryEfficiency) {
        mRealm.beginTransaction();
        TaskSubcategoryEfficiency tse = mRealm.copyToRealm(subcategoryEfficiency);
        tse.getTimeTask().getSubcategoryEfficiencies().add(tse);
        mRealm.commitTransaction();
        return tse;
    }
}
