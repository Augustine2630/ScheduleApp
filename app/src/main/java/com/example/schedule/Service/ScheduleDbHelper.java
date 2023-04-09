package com.example.schedule.Service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScheduleDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "schedule.db";

    public ScheduleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Создаем таблицу для хранения групп
        String SQL_CREATE_GROUP_TABLE = "CREATE TABLE " + ScheduleContract.GroupEntry.TABLE_NAME + " (" +
                ScheduleContract.GroupEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ScheduleContract.GroupEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE)";

        db.execSQL(SQL_CREATE_GROUP_TABLE);

        // Создаем таблицу для хранения расписания
        String SQL_CREATE_SCHEDULE_TABLE = "CREATE TABLE " + ScheduleContract.ScheduleEntry.TABLE_NAME + " (" +
                ScheduleContract.ScheduleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ScheduleContract.ScheduleEntry.COLUMN_DAY + " TEXT NOT NULL," +
                ScheduleContract.ScheduleEntry.COLUMN_TIME + " TEXT NOT NULL," +
                ScheduleContract.ScheduleEntry.COLUMN_SUBJECT + " TEXT NOT NULL," +
                ScheduleContract.ScheduleEntry.COLUMN_GROUP + " TEXT NOT NULL," +
                "FOREIGN KEY(" + ScheduleContract.ScheduleEntry.COLUMN_GROUP + ") REFERENCES " +
                ScheduleContract.GroupEntry.TABLE_NAME + "(" + ScheduleContract.GroupEntry.COLUMN_NAME + "))";

        db.execSQL(SQL_CREATE_SCHEDULE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаляем таблицы при обновлении базы данных
        db.execSQL("DROP TABLE IF EXISTS " + ScheduleContract.GroupEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScheduleContract.ScheduleEntry.TABLE_NAME);
//        onCreate(db);
    }
}