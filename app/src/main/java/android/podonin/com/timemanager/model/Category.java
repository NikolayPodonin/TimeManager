package android.podonin.com.timemanager.model;

import android.podonin.com.timemanager.R;

/**
 * Created by User on 14.03.2018.
 */

public enum Category {
    Body(R.string.body),
    Business(R.string.business),
    Spirit(R.string.spirit),
    Relationships(R.string.relationships);

    private final int mNameResource;

    Category(int nameResource){
        mNameResource = nameResource;
    }

    public int getNameResource() {
        return mNameResource;
    }
}
