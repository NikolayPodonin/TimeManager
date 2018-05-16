package android.podonin.com.timemanager.repository;

import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 *  @author Nic Podonin
 */

public class RealmHelper {

    private static RealmHelper sRealmHelper;

    private Realm mRealm;

    public static RealmHelper getInstance() {
        if (sRealmHelper == null){
            return new RealmHelper();
        }
        return sRealmHelper;
    }

    private RealmHelper() {
        try {
            mRealm = Realm.getDefaultInstance();
        } catch (Exception e) {
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .name("timemanager.realm")
                    .deleteRealmIfMigrationNeeded()
                    .build();
            mRealm = Realm.getInstance(config);
        }
    }

    public <R extends RealmObject> void update(R obj){
        insertOrUpdate(obj, true);
    }

    public <R extends RealmObject> void insert(R obj){
        insertOrUpdate(obj, false);
    }

    public <R extends RealmObject> List<R> getAll(Class<R> rClass){
        return getAllResults(rClass);
    }

    public <R extends RealmObject> List<R> getAll(Class<R> rClass, @NonNull String key, long value){
        return mRealm.where(rClass).equalTo(key, value).findAll();
    }

    public <R extends RealmObject> List<R> getAll(Class<R> rClass, @NonNull String key, @NonNull String value){
        return mRealm.where(rClass).equalTo(key, value).findAll();
    }

    /**
     * Returns typed RealmObject. Need if you are often write and reed only one exemplar of type
     *
     * @param rClass - object type.
     * @return exemplar of class extends RealmObject.
     **/
    public <R extends RealmObject> R get(Class<R> rClass){
        return mRealm.where(rClass).findFirst();
    }

    public <R extends RealmObject> R get(Class<R> rClass, @NonNull String key, @NonNull String value){
        return mRealm.where(rClass).equalTo(key, value).findFirst();
    }

    public <R extends RealmObject> void delete(Class<R> rClass, @NonNull String key, @NonNull String value){
        mRealm.executeTransaction(r -> {
            try {
                RealmResults results = mRealm.where(rClass).equalTo(key, value).findAll();
                results.deleteAllFromRealm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private <R extends RealmObject> RealmResults<R> getAllResults(Class<R> rClass){
        return mRealm.where(rClass).findAll();
    }

    private <R extends RealmObject> void insertOrUpdate(R obj, final boolean needClear) {
        mRealm.executeTransaction(r -> {
                try {
                    if (needClear) {
                        getAllResults(obj.getClass()).deleteAllFromRealm();
                    }
                    r.insertOrUpdate(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    r.cancelTransaction();
                }
            });
    }


    public List<Subcategory> getAllSubcategories(){
        return mRealm.where(Subcategory.class).findAll();
    }

    public List<Subcategory> getSubcategoriesFromCategory(Category category) {
        return mRealm.where(Subcategory.class).equalTo(Subcategory.CATEGORY_FIELD, category.toString()).findAll();
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
        timeTask.setStartDate(CalendarUtils.currentDay(timeTask.getStartDate()));
        TimeTask tt = mRealm.copyToRealm(timeTask);
        mRealm.commitTransaction();
        return tt;
    }

    public TimeTask getTimeTask(String taskId) {
        return mRealm.where(TimeTask.class).equalTo(TimeTask.TASK_ID, taskId).findFirst();
    }

    public void deleteTimeTask(String taskId) {
        mRealm.beginTransaction();
        RealmResults<TimeTask> results = mRealm.where(TimeTask.class).equalTo(TimeTask.TASK_ID, taskId).findAll();
        results.deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    public void insertTaskSubcategoryEfficiency(TaskSubcategoryEfficiency subcategoryEfficiency) {
        mRealm.beginTransaction();
        TaskSubcategoryEfficiency tse = mRealm.copyToRealm(subcategoryEfficiency);
        tse.getTimeTask().getSubcategoryEfficiencies().add(tse);
        mRealm.commitTransaction();
    }


    public void close() {
        mRealm.close();
    }

    public void clear() {
        try {
            mRealm.close();
            Realm.deleteRealm(mRealm.getConfiguration());
            sRealmHelper = null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
