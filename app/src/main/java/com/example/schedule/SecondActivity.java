package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.schedule.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private List<Schedule> schedules = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.task);
        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            value = extras.getString("id");
        }
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor cursorCourses
                = db.rawQuery("SELECT * FROM groups WHERE id =" + value, null);


        if (cursorCourses.moveToFirst()){
            schedules.add(new Schedule(cursorCourses.getInt(0), cursorCourses.getString(1), cursorCourses.getString(2)));
        }
        textView.setText(schedules.get(0).toString());
    }
}