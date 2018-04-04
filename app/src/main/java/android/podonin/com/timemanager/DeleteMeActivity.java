package android.podonin.com.timemanager;

import android.podonin.com.timemanager.R;
import android.podonin.com.timemanager.calendarwidget.CalendarUtils;
import android.podonin.com.timemanager.calendarwidget.EventCalendarView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DeleteMeActivity extends AppCompatActivity {

    private EventCalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_me);


        mCalendarView = findViewById(R.id.calendar_view);
        mCalendarView.setListener(new EventCalendarView.OnChangeListener() {
            @Override
            public void onSelectedDayChange(long dayMillis) {
                String month = CalendarUtils.toMonthString(mCalendarView.getContext(), dayMillis);
                Toast.makeText(DeleteMeActivity.this, month, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
