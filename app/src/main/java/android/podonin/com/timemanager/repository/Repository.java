package android.podonin.com.timemanager.repository;

import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;

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
}
