package android.podonin.com.timemanager.calendarview;

import android.database.Cursor;
import android.database.CursorWrapper;

public class EventCursor extends CursorWrapper{
    private static final int PROJECTION_INDEX_DTSTART = 3;
    private static final int PROJECTION_INDEX_DTEND = 4;
    private static final int PROJECTION_INDEX_ALL_DAY = 5;

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public EventCursor(Cursor cursor) {
        super(cursor);
    }

    public long getDateTimeStart() {
        return getLong(PROJECTION_INDEX_DTSTART);
    }

    public long getDateTimeEnd() {
        return getLong(PROJECTION_INDEX_DTEND);
    }

    public boolean getAllDay() {
        return getInt(PROJECTION_INDEX_ALL_DAY) == 1;
    }
}
