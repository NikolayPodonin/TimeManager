package android.podonin.com.timemanager.model;

/**
 * Created by User on 14.03.2018.
 */

public class Subcategory {
    private Category mCategory;
    private String mName;

    public Subcategory(Category category, String name) {
        mCategory = category;
        mName = name;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
