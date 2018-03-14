package android.podonin.com.timemanager.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by User on 14.03.2018.
 */

public class Subcategory extends RealmObject {
    @PrimaryKey
    private long mId;
    private String mCategory;
    private String mName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
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
