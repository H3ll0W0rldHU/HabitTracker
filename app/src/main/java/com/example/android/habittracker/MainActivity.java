package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_DATE,
                HabitEntry.COLUMN_START_TIME,
                HabitEntry.COLUMN_FINISH_TIME
        };

        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
        TextView displayView = (TextView) findViewById(R.id.habit_db_view);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // habits table in the database).
            displayView.setText("The habits table contains " + cursor.getCount() + " rows.\n\n");
            displayView.append(HabitEntry._ID + " - " + HabitEntry.COLUMN_HABIT_NAME + " - " + HabitEntry.COLUMN_DATE + " - " + HabitEntry.COLUMN_START_TIME + " - " + HabitEntry.COLUMN_FINISH_TIME + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DATE);
            int starttimeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_START_TIME);
            int finishtimeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_FINISH_TIME);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentDate = cursor.getInt(dateColumnIndex);
                int currentStartTime = cursor.getInt(starttimeColumnIndex);
                int currentFinishTime = cursor.getInt(finishtimeColumnIndex);

                displayView.append(("\n" + currentID + " - " + currentName + " - " + currentDate + " - " + currentStartTime + " - " + currentFinishTime));
            }

        } finally {

            cursor.close();
        }
    }

    public void buttonClick(View v) {
        insertDummyHabit();
        displayDatabaseInfo();
    }
}
