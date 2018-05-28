package android.podonin.com.timemanager.adapter.categoriesadapter;

import android.podonin.com.timemanager.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

class CategoryHolder extends RecyclerView.ViewHolder implements RvCategoriesAdapter.OnSetActiveHolderListener {

    private TextView mTextView;
    private ImageButton mImageButton;
    private RvCategoriesAdapter.CategoryDraws mCategoryDraws;
    private int mPosition;

    public CategoryHolder(@NonNull View itemView) {
        super(itemView);
        mImageButton = itemView.findViewById(R.id.category_image_button);
        mTextView = itemView.findViewById(R.id.category_text_view);
    }

    public void bind(RvCategoriesAdapter.CategoryDraws categoryDraws, int position, final ActivePositionSetter setter){
        mCategoryDraws = categoryDraws;
        mPosition = position;
        mImageButton.setImageResource(mCategoryDraws.getBlackDraw());
        mTextView.setText(mCategoryDraws.getNameString());
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setter.setActivePosition(mPosition);
            }
        });
    }

    @Override
    public void onSetActiveHolder(int position) {
        if(mPosition == position){
            mImageButton.setImageResource(mCategoryDraws.getBlueDraw());
            mTextView.setTextColor(mCategoryDraws.getBlueColor());
        } else {
            mImageButton.setImageResource(mCategoryDraws.getBlackDraw());
            mTextView.setTextColor(mCategoryDraws.getBlackColor());
        }
    }
}
