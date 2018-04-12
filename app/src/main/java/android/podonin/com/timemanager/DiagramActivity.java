package android.podonin.com.timemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
