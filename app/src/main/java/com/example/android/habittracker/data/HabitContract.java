package com.example.android.habittracker.data;


import android.provider.BaseColumns;

public final class HabitContract {

    private HabitContract() {}

    public static final class HabitEntry implements BaseColumns{
        public static final String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "habitname";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_START_TIME = "starttime";
        public static final String COLUMN_FINISH_TIME = "finishtime";

    }
}