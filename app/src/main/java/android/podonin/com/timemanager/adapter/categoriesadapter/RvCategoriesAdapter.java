package android.podonin.com.timemanager.adapter.categoriesadapter;

import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.model.Category;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class RvCategoriesAdapter extends RecyclerView.Adapter<CategoryHolder> implements ActivePositionSetter {

    public interface OnCategoryClickListener{
        void onClick(Category category);
    }

    interface OnSetActiveHolderListener{
        void onSetActiveHolder(int position);
    }

    private final ArrayList<CategoryDraws> mCategoryDraws = new ArrayList<CategoryDraws>(){{
        add(0, new CategoryDraws(R.drawable.ic_body_black_64dp_new, R.drawable.ic_body_color_64dp,
                R.string.body, R.color.color_body));
        add(1, new CategoryDraws(R.drawable.ic_business_black_64dp_new, R.drawable.ic_business_color_64dp,
                R.string.business, R.color.color_business));
        add(2, new CategoryDraws(R.drawable.ic_spirit_black_64dp_new, R.drawable.ic_spirit_color_64dp,
                R.string.spirit, R.color.color_spirit));
        add(3, new CategoryDraws(R.drawable.ic_relation_black_64dp, R.drawable.ic_relation_color_64dp,
                R.string.relationships, R.color.color_relation));
    }};

    private int mActivePosition = -1;
    private List<OnSetActiveHolderListener> mSetActiveHolderListeners = new ArrayList<>();
    private OnCategoryClickListener mOnCategoryClickListener;

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_item_category, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.bind(mCategoryDraws.get(position), position, this);
        mSetActiveHolderListeners.add(holder);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        mOnCategoryClickListener = onCategoryClickListener;
    }

    public void setActivePosition(int activePosition) {
        if (activePosition == mActivePosition) {
            mActivePosition = -1;
        } else {
            mActivePosition = activePosition;
        }
        for (OnSetActiveHolderListener listener :
                mSetActiveHolderListeners) {
            listener.onSetActiveHolder(mActivePosition);
        }

        if(mOnCategoryClickListener != null){
            switch (mActivePosition){
                case 0:
                    mOnCategoryClickListener.onClick(Category.Body);
                    break;
                case 1:
                    mOnCategoryClickListener.onClick(Category.Business);
                    break;
                case 2:
                    mOnCategoryClickListener.onClick(Category.Spirit);
                    break;
                case 3:
                    mOnCategoryClickListener.onClick(Category.Relationships);
                    break;
                default:
                    mOnCategoryClickListener.onClick(null);
            }
        }
    }

    static class CategoryDraws{
        private final int mBlackDraw;
        private final int mColorDraw;
        private final int mNameString;
        private final int mColorResource;

        CategoryDraws(int blackDraw, int blueDraw, int string, int colorResId) {
            mBlackDraw = blackDraw;
            mColorDraw = blueDraw;
            mNameString = string;
            mColorResource = colorResId;
        }

        public int getBlackDraw() {
            return mBlackDraw;
        }

        public int getColorDraw() {
            return mColorDraw;
        }

        public int getNameString() {
            return mNameString;
        }

        public int getColorResource() {
            return mColorResource;
        }
    }
}

