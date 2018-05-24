package android.podonin.com.timemanager.view;

import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;

import java.util.List;

public interface TaskEditFragmentView {
    void setSubcategoriesVisibility(boolean visibility);

    void showDeleteSubcategoryDialog(String taskId);

    void showDatePickerDialog();

    void showSubcategories(Category category, List<Subcategory> subcategories, List<TaskSubcategoryEfficiency> efficiencies);

    void showTaskBody(String taskBody);

    void showTaskDate(long taskDate);

    void setDone(boolean isDone);

    void saveChanges();

    void showSubcategoriesFromCash(Category category);

    void exit();

    void checkSomeChanges();

    void showMessage(int message);

    void showAddSubcategoryDialog();
}
