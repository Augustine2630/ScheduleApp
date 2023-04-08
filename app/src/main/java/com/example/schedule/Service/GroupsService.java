package com.example.schedule.Service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schedule.model.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupsService extends AppCompatActivity {
    private List<Group> groups = new ArrayList<>();


    public List<Group> getGroups() {
        return groups;
    }

    public List<Group> initGroups(SQLiteDatabase db) {
        groups.clear();
        db.execSQL("DROP TABLE IF EXISTS groups;");
        db.execSQL("CREATE TABLE IF NOT EXISTS groups (id INTEGER, code TEXT)");
        db.execSQL("INSERT INTO groups VALUES (1, '181-291')");
        db.execSQL("INSERT INTO groups VALUES (2, '204-372')");
        db.execSQL("INSERT INTO groups VALUES (3, '204-371')");
        Cursor query;
        query = db.rawQuery("SELECT * FROM groups;", null);
        while (query.moveToNext()){
            groups.add(new Group(query.getInt(0), query.getString(1)));
        }
        query.close();
        return groups;
    }
}
