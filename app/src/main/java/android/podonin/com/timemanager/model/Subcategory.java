package android.podonin.com.timemanager.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by User on 14.03.2018.
 */

public class Subcategory extends RealmObject {
    private static final String SUBCATEGORY_ID = "mSubcategoryId";
    public static final String NAME_FIELD = "mName";
    public static final String CATEGORY_FIELD = "mCategory";

    @PrimaryKey
    @Required
    private String mSubcategoryId;
    private String mCategory;
    private String mName;

    public Subcategory() {
        mSubcategoryId = UUID.randomUUID().toString();
    }

    public String getSubcategoryId() {
        return mSubcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        mSubcategoryId = subcategoryId;
    }

    public Category getCategory() {
        return Category.valueOf(mCategory);
    }

    public void setCategory(Category category) {
        mCategory = category.toString();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
