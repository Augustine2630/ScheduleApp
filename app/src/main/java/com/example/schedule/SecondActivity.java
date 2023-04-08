package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.schedule.Service.ScheduleContract;
import com.example.schedule.Service.ScheduleDbHelper;
import com.example.schedule.Service.ScheduleService;
import com.example.schedule.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private TextView mScheduleTextView;
    private ScheduleDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Находим TextView в макете
        mScheduleTextView = findViewById(R.id.schedule_text_view);

        // Получаем выбранную группу из Intent
        Intent intent = getIntent();
        String selectedGroup = intent.getStringExtra("selected_group");

        // Создаем экземпляр класса ScheduleDbHelper
        mDbHelper = new ScheduleDbHelper(this);

        // Получаем расписание для выбранной группы и выводим его в TextView
//        String schedule = getSchedule(selectedGroup);
        mScheduleTextView.setText(getSchedule(selectedGroup));
    }

    // Получаем расписание для выбранной группы
    private String getSchedule(String selectedGroup) {
        StringBuilder scheduleBuilder = new StringBuilder();

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ScheduleContract.ScheduleEntry._ID,
                ScheduleContract.ScheduleEntry.COLUMN_DAY,
                ScheduleContract.ScheduleEntry.COLUMN_TIME,
                ScheduleContract.ScheduleEntry.COLUMN_SUBJECT
        };

        String selection = ScheduleContract.ScheduleEntry.COLUMN_GROUP + " = ?";
        String[] selectionArgs = {selectedGroup};

        Cursor cursor = db.query(
                ScheduleContract.ScheduleEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                ScheduleContract.ScheduleEntry.COLUMN_DAY + ", " +
                        ScheduleContract.ScheduleEntry.COLUMN_TIME
        );

        while (cursor.moveToNext()) {
            String day = cursor.getString(
                    cursor.getColumnIndexOrThrow(ScheduleContract.ScheduleEntry.COLUMN_DAY));
            String time = cursor.getString(
                    cursor.getColumnIndexOrThrow(ScheduleContract.ScheduleEntry.COLUMN_TIME));
            String subject = cursor.getString(
                    cursor.getColumnIndexOrThrow(ScheduleContract.ScheduleEntry.COLUMN_SUBJECT));

            scheduleBuilder.append(day).append(": ").append(time).append(" - ").append(subject).append("\n");
        }

        cursor.close();
        System.out.println(scheduleBuilder.toString());
        return scheduleBuilder.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
    }
}