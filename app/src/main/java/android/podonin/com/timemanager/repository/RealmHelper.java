package android.podonin.com.timemanager.repository;

import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 *  @author Nic Podonin
 */

public class RealmHelper {

    private Realm mRealm;

    public RealmHelper(){
        mRealm = Realm.getDefaultInstance();
    }

    public List<Subcategory> getAllSubcategories(){
        RealmResults<Subcategory> results = mRealm.where(Subcategory.class).findAll();
        return results;
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

    public TaskSubcategoryEfficiency addTaskSubcategoryEfficiency(TaskSubcategoryEfficiency subcategoryEfficiency) {
        mRealm.beginTransaction();
        TaskSubcategoryEfficiency tse = mRealm.copyToRealm(subcategoryEfficiency);
        tse.getTimeTask().getSubcategoryEfficiencies().add(tse);
        mRealm.commitTransaction();
        return tse;
    }

    public List<TimeTask> getTimeTasksPerDay(long dayMillis) {
        long currentDay = CalendarUtils.currentDay(dayMillis);
        return mRealm.where(TimeTask.class).equalTo(TimeTask.START_DATE_FIELD, currentDay).findAll();
    }

    public TaskSubcategoryEfficiency changeTaskSubcategoryEfficiency(TaskSubcategoryEfficiency changedTse) {
        mRealm.beginTransaction();
        TaskSubcategoryEfficiency tse = mRealm.copyToRealm(changedTse);
        mRealm.commitTransaction();
        return tse;
    }

    public void deleteTaskSubcategoryEfficiency(TaskSubcategoryEfficiency deletedTse) {
        mRealm.beginTransaction();
        RealmResults<TaskSubcategoryEfficiency> query = mRealm.where(TaskSubcategoryEfficiency.class)
                .equalTo(TaskSubcategoryEfficiency.TASK_SUB_EFFICIENCY_ID, deletedTse.getTaskSubEfficiencyId()).findAll();
        deletedTse.getTimeTask().getSubcategoryEfficiencies().remove(deletedTse);
        query.deleteAllFromRealm();
        mRealm.commitTransaction();
    }
}
