package android.podonin.com.timemanager.view;

import android.content.Context;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TimeTask;

import java.util.List;

public interface TaskEditFragmentView {
    void setSubcategoriesVisibility(boolean visibility);

    void setSubcategories(List<Subcategory> subcategories, TimeTask timeTask);

    void showTaskBody(String taskBody);

    void showTaskDate(String taskDate);

    void setDone(boolean isDone);

    Context getFragmentContext();
}
