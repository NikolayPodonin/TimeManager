package android.podonin.com.timemanager;

import android.content.Intent;
import android.podonin.com.timemanager.model.Category;
import android.podonin.com.timemanager.model.Efficiency;
import android.podonin.com.timemanager.model.Subcategory;
import android.podonin.com.timemanager.model.TaskSubcategoryEfficiency;
import android.podonin.com.timemanager.model.TimeTask;
import android.podonin.com.timemanager.repository.Repository;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class DiagramActivity extends AppCompatActivity {

    TextView mTextView;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);

        mTextView = findViewById(R.id.text_view);
        mButton = findViewById(R.id.button);

    }
}
