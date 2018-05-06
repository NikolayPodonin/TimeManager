package android.podonin.com.timemanager.adapter;

import android.podonin.com.timemanager.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class RvCategoriesAdapter extends RecyclerView.Adapter<RvCategoriesAdapter.CategoryHolder> {

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_item_category, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        switch (position){
            case 0:
                holder.bind(R.drawable.ic_body_black_64dp, R.string.body);
                break;
            case 1:
                holder.bind(R.drawable.ic_business_black_64dp, R.string.business);
                break;
            case 2:
                holder.bind(R.drawable.ic_spirit_black_64dp, R.string.spirit);
                break;
            case 3:
                holder.bind(R.drawable.ic_relationships_black_64dp, R.string.relationships);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageButton mImageButton;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            mImageButton = itemView.findViewById(R.id.category_image_button);
            mTextView = itemView.findViewById(R.id.category_text_view);
        }

        public void bind(int imageResId, int stringId){
            mImageButton.setImageResource(imageResId);
            mTextView.setText(stringId);
        }
    }
}
