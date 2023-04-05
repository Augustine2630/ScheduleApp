package com.example.schedule;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.schedule.Service.ScheduleService;
import com.example.schedule.model.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



//    private ScheduleService scheduleService = new ScheduleService();
    private List<Schedule>  schedules = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("DELETE FROM groups");
        schedules.clear();
        db.execSQL("CREATE TABLE IF NOT EXISTS groups (id INTEGER, name TEXT, date TEXT)");
        db.execSQL("INSERT INTO groups VALUES (1, 'Math', '12.02.2022')");
        db.execSQL("INSERT INTO groups VALUES (2, \"Lang\", \"12.02.2022\")");
        db.execSQL("INSERT INTO groups VALUES (3, \"Aboba\", \"12.02.2022\")");

        Cursor query = db.rawQuery("SELECT * FROM groups;", null);
        while (query.moveToNext()){
            schedules.add(new Schedule(query.getInt(0), query.getString(1), query.getString(2)));
        }

        query.close();
        db.close();

        TextView selection = findViewById(R.id.selection);
        ListView countriesList = findViewById(R.id.countriesList);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, schedules);
        countriesList.setAdapter(adapter);
        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String selectedItem = String.valueOf(schedules.get(position).getId());
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("id", selectedItem);
                startActivity(intent);
            }
        });
//        schedules.clear();
    }
}