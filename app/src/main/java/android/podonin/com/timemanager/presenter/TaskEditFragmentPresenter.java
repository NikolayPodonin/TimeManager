package android.podonin.com.timemanager.presenter;

import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.utilites.CalendarUtils;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.RealmHelper;
import android.podonin.com.timemanager.view.TaskEditFragmentView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class TaskEditFragmentPresenter {
    private TimeTask mTimeTask;
    private RealmHelper mRealmHelper;
    private TaskEditFragmentView mFragmentView;
    private boolean mIsNeedToSave;
    private boolean mIsNeedToDelete;
    private boolean mIsOkClosed;
    private Category mCurrentCategory;

    public TaskEditFragmentPresenter(TaskEditFragmentView fragmentView, String timeTaskId) {
        mRealmHelper = RealmHelper.getInstance();
        mFragmentView = fragmentView;
        mTimeTask = mRealmHelper.get(TimeTask.class, TimeTask.TASK_ID, timeTaskId);
        mIsNeedToSave = false;
        mIsOkClosed = false;
        mIsNeedToDelete = (isEmptyBody(mTimeTask.getTaskBody())) &&
                (mTimeTask.getSubcategoryEfficiencies() == null ||
                        mTimeTask.getSubcategoryEfficiencies().size() == 0);
    }

    public void dispatchCreate() {
        if(mTimeTask != null && mFragmentView != null){
            mFragmentView.showTaskBody(mTimeTask.getTaskBody());
            mFragmentView.showTaskDate(mTimeTask.getStartDate());
            mFragmentView.setDone(mTimeTask.isDone());
        }
    }

    public void dispatchDestroy(){
        mFragmentView = null;
    }

    public void onCategoryChoose(@Nullable Category category, boolean isCashEmpty){
        mCurrentCategory = category;
        if(category == null){
            mFragmentView.setSubcategoriesVisibility(false);
        } else if(isCashEmpty) {
            mFragmentView.setSubcategoriesVisibility(true);
            getAndShowSubcategories(category);
        } else {
            mFragmentView.setSubcategoriesVisibility(true);
            mFragmentView.showSubcategoriesFromCash(category);
        }
    }

    public void onSaveTaskSubcategoryEfficiencies(@NonNull List<TaskSubcategoryEfficiency> changed, @NonNull List<TaskSubcategoryEfficiency> deleted, @NonNull List<TaskSubcategoryEfficiency> added) {
        for (TaskSubcategoryEfficiency ce : changed) {
            mRealmHelper.insert(ce);
        }
        for (TaskSubcategoryEfficiency de : deleted) {
            mTimeTask.getSubcategoryEfficiencies().remove(de);
            mRealmHelper.delete(de.getClass(), TaskSubcategoryEfficiency.TASK_SUB_EFFICIENCY_ID, de.getTaskSubEfficiencyId());
        }
        for (TaskSubcategoryEfficiency ne : added) {
            ne.setTimeTask(mTimeTask);
            mTimeTask.getSubcategoryEfficiencies().add(ne);
            mRealmHelper.insert(ne);
        }
    }

    public void onSaveTask(@Nullable final String taskBody, final long taskDate, final boolean checked) {
        mTimeTask.setStartDate(taskDate);
        mTimeTask.setTaskBody(taskBody);
        mTimeTask.setDone(checked);
        mRealmHelper.insert(mTimeTask);
    }

    private boolean isEmptyBody(@Nullable String taskBody) {
        return taskBody == null || taskBody.isEmpty() || taskBody.equals("");
    }

    public void onOkExit(){
        mIsOkClosed = true;
        mFragmentView.exit();
    }

    public void onBackExit(){
        mFragmentView.exit();
    }

    public void onExit() {
        mFragmentView.checkSomeChanges();
        int message = 0;
        if (mIsNeedToSave && mIsOkClosed){
                mFragmentView.saveChanges();
                message = R.string.changes_saved;
        } else if (mIsNeedToDelete) {
            mRealmHelper.delete(TimeTask.class, TimeTask.TASK_ID, mTimeTask.getTaskId());
            message = R.string.changes_not_saved;
        }
        mFragmentView.showMessage(message);
    }

    public void onCheckChanges(boolean isTseChanged, @NonNull final String taskBody, final long taskDate, final boolean isDone) {
        boolean body;
        if (isEmptyBody(taskBody)){
            body = !isEmptyBody(mTimeTask.getTaskBody());
        } else {
            body = !taskBody.equals(mTimeTask.getTaskBody());
        }
        boolean date = taskDate != mTimeTask.getStartDate();
        boolean done = isDone != mTimeTask.isDone();
        mIsNeedToSave = isTseChanged || body || date || done;
        if (mIsNeedToSave && !mIsOkClosed){
            mIsNeedToDelete = false;
        }
    }

    public void onLongSubcategoryClick(String subId) {
        mFragmentView.showDeleteSubcategoryDialog(subId);
    }

    public void deleteSubcategory(String subId) {
        Category category = mRealmHelper.get(Subcategory.class, Subcategory.SUBCATEGORY_ID, subId).getCategory();

        TaskSubcategoryEfficiency tseff = null;
        for (TaskSubcategoryEfficiency tse : mTimeTask.getSubcategoryEfficiencies()) {
            if (tse.getSubcategory().getSubcategoryId().equals(subId)){
                tseff = tse;
                break;
            }
        }
        if (tseff != null){
            mTimeTask.getSubcategoryEfficiencies().remove(tseff);
        }

        mRealmHelper.delete(TaskSubcategoryEfficiency.class,
                TaskSubcategoryEfficiency.SUBCATEGORY_FIELD + "." + Subcategory.SUBCATEGORY_ID,
                subId);
        mRealmHelper.delete(Subcategory.class, Subcategory.SUBCATEGORY_ID, subId);

        getAndShowSubcategories(category);
    }

    private void getAndShowSubcategories(Category category) {
        List<Subcategory> subcategories = new ArrayList<>(mRealmHelper.getAll(Subcategory.class, Subcategory.CATEGORY_FIELD, category.toString()));
        List<TaskSubcategoryEfficiency> tseList = new ArrayList<>(mTimeTask.getSubcategoryEfficiencies());

        mFragmentView.showSubcategories(category, subcategories, tseList);
    }

    public void onAddSubcategoryClick() {
        mFragmentView.showAddSubcategoryDialog();
    }

    public void addNewSubcategory(String subName) {
        Subcategory newSub = new Subcategory();
        newSub.setCategory(mCurrentCategory);
        newSub.setName(subName);
        mRealmHelper.insert(newSub);

        getAndShowSubcategories(mCurrentCategory);
    }

    public void onDateChoose(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        long dayMillis = calendar.getTimeInMillis();
        dayMillis = CalendarUtils.currentDay(dayMillis);
        mFragmentView.showTaskDate(dayMillis);
    }

    public void onDateClick() {
        mFragmentView.showDatePickerDialog();
    }
}
