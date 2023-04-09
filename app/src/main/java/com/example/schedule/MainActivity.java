package com.example.schedule;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.schedule.Service.ScheduleContract;
import com.example.schedule.Service.ScheduleDbHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner mGroupSpinner;
    private ScheduleDbHelper mDbHelper;

    private Button addGroup;
    private Button selectGroup;
    private Button deleteGroupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new ScheduleDbHelper(this);
        deleteDBIfExist(mDbHelper.getReadableDatabase());
        System.out.println(mDbHelper.getWritableDatabase());
        initSchedules();
        // Находим Spinner в макете
        mGroupSpinner = findViewById(R.id.group_spinnner);
        addGroup = findViewById(R.id.addGroup);
        selectGroup = findViewById(R.id.selectButton);
        deleteGroupButton = findViewById(R.id.deleteGroup);
        // Создаем адаптер для Spinner с группами
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, getGroupNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGroupSpinner.setAdapter(adapter);

        // Создаем экземпляр класса ScheduleDbHelper
        try {
            addGroup.setOnClickListener(v -> {
                addGroup(v);
            });
            selectGroup.setOnClickListener(v -> {
                selectSchedule(v);
            });
            deleteGroupButton.setOnClickListener(v -> {
                deleteGroup(v);
            });
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "нет такого элемента", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void initSchedules() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //new
        values.put(ScheduleContract.ScheduleEntry.COLUMN_GROUP, "43");
        values.put(ScheduleContract.ScheduleEntry.COLUMN_DAY, "Понедельник");
        values.put(ScheduleContract.ScheduleEntry.COLUMN_TIME, "11.05.23");
        values.put(ScheduleContract.ScheduleEntry.COLUMN_SUBJECT, "Английский яз.\nAndroid разработка\nРазработка ИС на 1С");
        db.insert(ScheduleContract.ScheduleEntry.TABLE_NAME, null, values);
        //new
        values.put(ScheduleContract.ScheduleEntry.COLUMN_GROUP, "13");
        values.put(ScheduleContract.ScheduleEntry.COLUMN_DAY, "Вторник");
        values.put(ScheduleContract.ScheduleEntry.COLUMN_TIME, "12.05.23");
        values.put(ScheduleContract.ScheduleEntry.COLUMN_SUBJECT, "Русский яз.\nХимия\nАлгебра");
        db.insert(ScheduleContract.ScheduleEntry.TABLE_NAME, null, values);

        values.put(ScheduleContract.ScheduleEntry.COLUMN_GROUP, "14");
        values.put(ScheduleContract.ScheduleEntry.COLUMN_DAY, "Среда");
        values.put(ScheduleContract.ScheduleEntry.COLUMN_TIME, "13.05.23");
        values.put(ScheduleContract.ScheduleEntry.COLUMN_SUBJECT, "Русский яз.\nХимия\nАлгебра");
        db.insert(ScheduleContract.ScheduleEntry.TABLE_NAME, null, values);

    }

    // Обработчик нажатия на кнопку "Выбрать"
    public void selectSchedule(View view) {
        // Получаем выбранную группу
        String selectedGroup = (String) mGroupSpinner.getSelectedItem();
        noSuchElement(selectedGroup);
        // Переходим на SecondActivity с выбранной группой
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("selected_group", selectedGroup);
        startActivity(intent);
    }

    private void noSuchElement(String selectedGroup) {
        if (selectedGroup == null || selectedGroup.isEmpty()
                || selectedGroup.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "нет такого элемента", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Обработчик нажатия на кнопку "Добавить группу"
    public void addGroup(View view) {
        // Создаем диалоговое окно для добавления группы
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить группу");

        // Создаем EditText для ввода названия группы
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Добавляем кнопку "Добавить"
        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Получаем название группы из EditText
                String groupName = input.getText().toString().trim();

                // Добавляем группу в базу данных
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(ScheduleContract.GroupEntry.COLUMN_NAME, groupName);
                db.insert(ScheduleContract.GroupEntry.TABLE_NAME, null, values);

                // Обновляем Spinner с группами
                updateGroupSpinner();
            }
        });

        // Добавляем кнопку "Отмена"
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Показываем диалоговое окно
        builder.show();
    }

    // Обработчик нажатия на кнопку "Удалить группу"
    public void deleteGroup(View view) {
        // Получаем выбранную группу
        String selectedGroup = (String) mGroupSpinner.getSelectedItem();
        noSuchElement(selectedGroup);
        // Удаляем группу из базы данных
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(ScheduleContract.GroupEntry.TABLE_NAME,
                ScheduleContract.GroupEntry.COLUMN_NAME + " = ?",
                new String[]{selectedGroup});

        // Обновляем Spinner с группами
        updateGroupSpinner();
    }

    // Обработчик нажатия на кнопку "Редактировать группу"
    public void editGroup(View view) {
        // Получаем выбраний код MainActivity.java:
//Создаем диалоговое окно для редактирования группы
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Редактировать группу");

        // Создаем EditText для ввода нового названия группы
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Добавляем кнопку "Редактировать"
        builder.setPositiveButton("Редактировать", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Получаем новое название группы из EditText
                String newGroupName = input.getText().toString().trim();

                // Получаем выбранную группу
                String selectedGroup = (String) mGroupSpinner.getSelectedItem();

                // Обновляем название группы в базе данных
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(ScheduleContract.GroupEntry.COLUMN_NAME, newGroupName);
                db.update(ScheduleContract.GroupEntry.TABLE_NAME,
                        values,
                        ScheduleContract.GroupEntry.COLUMN_NAME + " = ?",
                        new String[]{selectedGroup});

                // Обновляем Spinner с группами
                updateGroupSpinner();
            }
        });

        // Добавляем кнопку "Отмена"
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Показываем диалоговое окно
        builder.show();
    }

    // Обновляем Spinner с группами
    private void updateGroupSpinner() {
        // Создаем адаптер для Spinner с группами
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item,
//                getGroupNames());
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, getGroupNames());
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        mGroupSpinner.setAdapter(adapter);
    }

    // Получаем список названий групп из базы данных
    private List<String> getGroupNames() {
        List<String> groupNames = new ArrayList<>();

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ScheduleContract.GroupEntry._ID,
                ScheduleContract.GroupEntry.COLUMN_NAME
        };

        Cursor cursor = db.query(
                ScheduleContract.GroupEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String groupName = cursor.getString(
                    cursor.getColumnIndexOrThrow(ScheduleContract.GroupEntry.COLUMN_NAME));
            groupNames.add(groupName);
        }

        cursor.close();

        return groupNames;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
    }

    public void deleteDBIfExist(SQLiteDatabase db){
        db.delete(ScheduleContract.ScheduleEntry.TABLE_NAME, null, null);
//        File dir = new File("/data/data/com.example.schedule/databases");
//        File file = new File(dir, "schedule.db");
//        File file2 = new File(dir, "schedule.db-journal");
//        if (file.exists()) {
//
//            file2.delete();
//            file.delete();
//        } else {
//            Log.i("OK", "OK");
//        }
    }

    public void handleNotExists(String selected){

    }
}