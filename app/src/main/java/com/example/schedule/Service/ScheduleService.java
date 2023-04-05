package com.example.schedule.Service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedule.MainActivity;
import com.example.schedule.model.Group;
import com.example.schedule.model.Schedule;

import java.util.ArrayList;
import java.util.List;


public class ScheduleService extends AppCompatActivity {
    public ScheduleService() {
    }

    List<Schedule> schedules = new ArrayList<>();

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void initSchedules(SQLiteDatabase db) {
        Cursor query;
        schedules.clear();
        db.execSQL("DROP TABLE IF EXISTS schedules");
        db.execSQL("CREATE TABLE IF NOT EXISTS schedules (id INTEGER, name TEXT, date TEXT)");
        db.execSQL("INSERT INTO schedules VALUES (1, 'Math', '12.02.2022')");
        db.execSQL("INSERT INTO schedules VALUES (2, \"Lang\", \"12.02.2022\")");
        db.execSQL("INSERT INTO schedules VALUES (3, \"Aboba\", \"12.02.2022\")");
        query = db.rawQuery("SELECT * FROM schedules;", null);
        while (query.moveToNext()){
            schedules.add(new Schedule(query.getInt(0), query.getString(1), query.getString(2)));
        }
        query.close();
    }
}
