package android.podonin.com.timemanager.adapter.subcategoriesadapter;

import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RvSubcategoryAdapter extends RecyclerView.Adapter<SubcategoryHolder> {

    public interface OnSaveChangesListener{
        void onSaveChanges(List<TaskSubcategoryEfficiency> changed, List<TaskSubcategoryEfficiency> deleted, List<TaskSubcategoryEfficiency> added);
    }

    private OnSaveChangesListener mOnSaveChangesListener;

    private List<TseState> mBodyTseStateCash = new ArrayList<>();
    private List<TseState> mBusinessTseStateCash = new ArrayList<>();
    private List<TseState> mSpiritTseStateCash = new ArrayList<>();
    private List<TseState> mRelationTseStateCash = new ArrayList<>();
    private List<Subcategory> mBodySubcategoryCash = new ArrayList<>();
    private List<Subcategory> mBusinessSubcategoryCash = new ArrayList<>();
    private List<Subcategory> mSpiritSubcategoryCash = new ArrayList<>();
    private List<Subcategory> mRelationSubcategoryCash = new ArrayList<>();
    private Category mCurrentCategory = Category.Body;

    public void setOnSaveChangesListener(OnSaveChangesListener onSaveChangesListener) {
        mOnSaveChangesListener = onSaveChangesListener;
    }

    public void saveChanges(){
        List<TaskSubcategoryEfficiency> changedTseList = new ArrayList<>();
        List<TaskSubcategoryEfficiency> deletedTseList = new ArrayList<>();
        List<TaskSubcategoryEfficiency> addedTseList = new ArrayList<>();

        for (TseState tseState: mBodyTseStateCash) {
            if (tseState.getTse() != null){
                if (tseState.isChanged()){
                    changedTseList.add(tseState.getTse());
                } else if (tseState.isDeleted()){
                    deletedTseList.add(tseState.getTse());
                } else if (tseState.isNew()){
                    addedTseList.add(tseState.getTse());
                }
            }
        }
        for (TseState tseState: mBusinessTseStateCash) {
            if (tseState.getTse() != null){
                if (tseState.isChanged()){
                    changedTseList.add(tseState.getTse());
                } else if (tseState.isDeleted()){
                    deletedTseList.add(tseState.getTse());
                }else if (tseState.isNew()){
                    addedTseList.add(tseState.getTse());
                }
            }
        }
        for (TseState tseState: mSpiritTseStateCash) {
            if (tseState.getTse() != null){
                if (tseState.isChanged()){
                    changedTseList.add(tseState.getTse());
                } else if (tseState.isDeleted()){
                    deletedTseList.add(tseState.getTse());
                } else if (tseState.isNew()){
                    addedTseList.add(tseState.getTse());
                }
            }
        }
        for (TseState tseState: mRelationTseStateCash) {
            if (tseState.getTse() != null){
                if (tseState.isChanged()){
                    changedTseList.add(tseState.getTse());
                } else if (tseState.isDeleted()){
                    deletedTseList.add(tseState.getTse());
                } else if (tseState.isNew()){
                    addedTseList.add(tseState.getTse());
                }
            }
        }

        mOnSaveChangesListener.onSaveChanges(changedTseList, deletedTseList, addedTseList);
    }

    public boolean isCategoryCashEmpty(Category category) {
        if (category != null){
            return getSubcategoryCash(category).isEmpty();
        } else {
            return false;
        }
    }

    public void setData(List<Subcategory> subcategories, List<TaskSubcategoryEfficiency> efficiencies) {
        mCurrentCategory = subcategories.get(0).getCategory();

        List<Subcategory> currentSubcategoryCash = getCurrentSubcategoryCash();
        currentSubcategoryCash.clear();
        currentSubcategoryCash.addAll(subcategories);

        List<TseState> currentTseStateCash = getCurrentTseStateCash();
        currentTseStateCash.clear();
        for (Subcategory subcategory : currentSubcategoryCash) {
            currentTseStateCash.add(new TseState(null));
            int i = currentTseStateCash.size();
            for (TaskSubcategoryEfficiency efficiency: efficiencies) {
                if (subcategory.getSubcategoryId().equals(efficiency.getSubcategory().getSubcategoryId())) {
                    currentTseStateCash.remove(i - 1);
                    currentTseStateCash.add(new TseState(efficiency));
                }
            }
        }

        notifyDataSetChanged();
    }
    public void setDataFromCash(Category category) {
        mCurrentCategory = category;
        notifyDataSetChanged();
    }

    private List<TseState> getCurrentTseStateCash(){
        return getTseStateCash(mCurrentCategory);
    }

    private List<TseState> getTseStateCash(Category category){
        switch (category){
            case Body:
                return mBodyTseStateCash;
            case Business:
                return mBusinessTseStateCash;
            case Spirit:
                return mSpiritTseStateCash;
            case Relationships:
                return mRelationTseStateCash;
            default:
                return null;
        }
    }

    private List<Subcategory> getCurrentSubcategoryCash(){
        return getSubcategoryCash(mCurrentCategory);
    }

    private List<Subcategory> getSubcategoryCash(Category category){
        switch (category){
            case Body:
                return mBodySubcategoryCash;
            case Business:
                return mBusinessSubcategoryCash;
            case Spirit:
                return mSpiritSubcategoryCash;
            case Relationships:
                return mRelationSubcategoryCash;
            default:
                return null;
        }
    }

    @NonNull
    @Override
    public SubcategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_item_subcategory_efficiency, parent, false);
        return new SubcategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoryHolder holder, int position) {
        List<Subcategory> currentSubcategoryCash = getCurrentSubcategoryCash();
        List<TseState> currentTseStateCash = getCurrentTseStateCash();

        holder.bind(currentSubcategoryCash.get(position), currentTseStateCash.get(position));
    }

    @Override
    public int getItemCount() {
        return getCurrentSubcategoryCash().size();
    }
}
