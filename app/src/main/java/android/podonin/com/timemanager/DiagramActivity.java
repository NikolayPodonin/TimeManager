package android.podonin.com.timemanager;

import android.content.Intent;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.repository.Repository;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiagramActivity extends AppCompatActivity {

    Repository mRepository;
    TextView mTextView;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);

        mRepository = new Repository();
        for(Integer i = 0; i < 10; i++){
            Subcategory sc = new Subcategory();
            sc.setCategory(Category.Body);
            sc.setName(i.toString());
            mRepository.addSubcategory(sc);
        }

        mTextView = findViewById(R.id.text_view);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                for (Subcategory sub: mRepository.getAllSubcategories()) {
                    sb.append(sub.getName()).append(", ").append(getString(sub.getCategory().getNameResource())).append("; ");
                }
                mTextView.setText(sb.toString());
            }
        });
    }
}
