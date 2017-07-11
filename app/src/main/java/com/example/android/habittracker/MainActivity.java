package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitDbHelper(this);

    }

    private void insertDummyHabit() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "Walking the dog");
        values.put(HabitEntry.COLUMN_DATE, 20170711);
        values.put(HabitEntry.COLUMN_START_TIME, 1107);
        values.put(HabitEntry.COLUMN_FINISH_TIME, 1234);

        db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    private Cursor readData() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_DATE,
                HabitEntry.COLUMN_START_TIME,
                HabitEntry.COLUMN_FINISH_TIME
        };

        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }

    private void displayDatabaseInfo() {

        TextView displayView = (TextView) findViewById(R.id.habit_db_view);
        Cursor c = readData();

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // habits table in the database).
            displayView.setText("The habits table contains " + readData().getCount() + " rows.\n\n");
            displayView.append(HabitEntry._ID + " - " + HabitEntry.COLUMN_HABIT_NAME + " - " + HabitEntry.COLUMN_DATE + " - " + HabitEntry.COLUMN_START_TIME + " - " + HabitEntry.COLUMN_FINISH_TIME + "\n");

            int idColumnIndex = c.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = c.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int dateColumnIndex = c.getColumnIndex(HabitEntry.COLUMN_DATE);
            int starttimeColumnIndex = c.getColumnIndex(HabitEntry.COLUMN_START_TIME);
            int finishtimeColumnIndex = c.getColumnIndex(HabitEntry.COLUMN_FINISH_TIME);

            while (c.moveToNext()) {
                int currentID = c.getInt(idColumnIndex);
                String currentName = c.getString(nameColumnIndex);
                int currentDate = c.getInt(dateColumnIndex);
                int currentStartTime = c.getInt(starttimeColumnIndex);
                int currentFinishTime = c.getInt(finishtimeColumnIndex);

                displayView.append(("\n" + currentID + " - " + currentName + " - " + currentDate + " - " + currentStartTime + " - " + currentFinishTime));
            }

        } finally {

            c.close();
        }
    }

    public void buttonClick(View v) {
        insertDummyHabit();
        displayDatabaseInfo();
    }
}
