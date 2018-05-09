package android.podonin.com.timemanager.adapter.categoriesadapter;

import android.podonin.com.timemanager.R;

class CategoryDraws{
    private final int mBlackDraw;
    private final int mBlueDraw;
    private final int mNameString;
    private final int mBlackColor = R.color.black500;
    private final int mBlueColor = R.color.blue500;

    CategoryDraws(int blackDraw, int blueDraw, int string) {
        mBlackDraw = blackDraw;
        mBlueDraw = blueDraw;
        mNameString = string;
    }

    public int getBlackDraw() {
        return mBlackDraw;
    }

    public int getBlueDraw() {
        return mBlueDraw;
    }

    public int getNameString() {
        return mNameString;
    }

    public int getBlackColor() {
        return mBlackColor;
    }

    public int getBlueColor() {
        return mBlueColor;
    }
}
