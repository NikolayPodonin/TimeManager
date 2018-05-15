package android.podonin.com.timemanager.view;

import android.content.Context;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;

import java.util.List;

public interface TaskEditFragmentView {
    void setSubcategoriesVisibility(boolean visibility);

    void showSubcategories(List<Subcategory> subcategories, List<TaskSubcategoryEfficiency> efficiencies);

    void showTaskBody(String taskBody);

    void showTaskDate(long taskDate);

    void setDone(boolean isDone);

    void saveChanges();

    void showSubcategoriesFromCash(Category category);

    void exit();

    void showEmptyBodyMessage();
}
