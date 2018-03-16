package android.podonin.com.timemanager.repository;

import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
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

    public void addSubcategory(Subcategory subcategory){
        mRealm.beginTransaction();
        mRealm.copyToRealm(subcategory);
        mRealm.commitTransaction();
    }

    public List<TimeTask> getAllTimeTasks(){
        return mRealm.where(TimeTask.class).findAll();
    }

    public void addTimeTask(TimeTask timeTask) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(timeTask);
        mRealm.commitTransaction();
    }
}
