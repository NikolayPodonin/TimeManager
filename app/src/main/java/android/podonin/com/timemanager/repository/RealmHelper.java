package android.podonin.com.timemanager.repository;

import android.podonin.com.timemanager.model.TimeTask;
import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
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
        RealmResults<R> results = mRealm.where(rClass).equalTo(key, value).findAll();
        return mRealm.copyFromRealm(results);
    }

    public <R extends RealmObject> List<R> getAll(Class<R> rClass, @NonNull String key, @NonNull String value){
        RealmResults<R> results = mRealm.where(rClass).equalTo(key, value).findAll();
        return mRealm.copyFromRealm(results);
    }

    public <R extends RealmObject> List<R> getAll(Class<R> rClass, @NonNull String key, @NonNull boolean value){
        RealmResults<R> results = mRealm.where(rClass).equalTo(key, value).findAll();
        return mRealm.copyFromRealm(results);
    }

    /**
     * Returns typed RealmObject. Need if you are often write and reed only one exemplar of type
     *
     * @param rClass - object type.
     * @return exemplar of class extends RealmObject.
     **/
    public <R extends RealmObject> R get(Class<R> rClass){
        R r = mRealm.where(rClass).findFirst();
        return r != null ? mRealm.copyFromRealm(r) : null;
    }

    public <R extends RealmObject> R get(Class<R> rClass, @NonNull String key, @NonNull String value){
        R r = mRealm.where(rClass).equalTo(key, value).findFirst();
        return r != null ? mRealm.copyFromRealm(r) : null;
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

    private <R extends RealmObject> List<R> getAllResults(Class<R> rClass){
        RealmResults<R> results = mRealm.where(rClass).findAll();
        return mRealm.copyFromRealm(results);
    }

    private <R extends RealmObject> void insertOrUpdate(R obj, final boolean needClear) {
        mRealm.executeTransaction(r -> {
                try {
                    if (needClear) {
                        mRealm.where(obj.getClass()).findAll().deleteAllFromRealm();
                    }
                    r.insertOrUpdate(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    r.cancelTransaction();
                }
            });
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
