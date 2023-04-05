package com.example.schedule;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.schedule.Service.GroupsService;
import com.example.schedule.Service.ScheduleService;
import com.example.schedule.model.Group;
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


    private ScheduleService scheduleService = new ScheduleService();
    private GroupsService groupsService = new GroupsService();
    private List<Group> groups = new ArrayList<>();
    private List<Schedule> schedules = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("aboba");
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        groupsService.initGroups(db);
        scheduleService.initSchedules(db);
        TextView selection = findViewById(R.id.selection);
        ListView countriesList = findViewById(R.id.countriesList);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, groupsService.getGroups());
        countriesList.setAdapter(adapter);
        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedItem = String.valueOf(groupsService.getGroups().get(position).getId());
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("id", selectedItem);
                startActivity(intent);
            }
        });

//        schedules.clear();
    }





}