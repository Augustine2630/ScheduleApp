package com.example.schedule;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.schedule.Service.GroupsService;
import com.example.schedule.Service.ScheduleService;
import com.example.schedule.activityResources.MainActivityRes;
import com.example.schedule.model.Group;
import com.example.schedule.model.Schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ScheduleService scheduleService = new ScheduleService();
    private GroupsService groupsService = new GroupsService();
    private List<Group> groups = new ArrayList<>();
    private List<Schedule> schedules = new ArrayList<>();
    private Button addGroup;
    private Button deleteGroup;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivityRes maRes = new MainActivityRes();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        if (groupsService.getGroups().isEmpty()){
            groups = groupsService.initGroups(db);
        }
        scheduleService.initSchedules(db);
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
        addGroup = findViewById(R.id.addSchedule);
        deleteGroup = findViewById(R.id.deleteSchedule);
        addGroup.setText("Добавить группу");
        addGroup.setOnClickListener(v -> {

        });
        deleteGroup.setOnClickListener(v -> {

        });


//        schedules.clear();
    }
}
