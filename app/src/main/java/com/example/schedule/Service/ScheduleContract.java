package com.example.schedule.Service;

import android.provider.BaseColumns;

public final class ScheduleContract {

    private ScheduleContract() {}

    public static class GroupEntry implements BaseColumns {
        public static final String TABLE_NAME = "groups";
        public static final String COLUMN_NAME = "name";
    }

    public static class ScheduleEntry implements BaseColumns {
        public static final String TABLE_NAME = "schedule";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_SUBJECT = "subject";
        public static final String COLUMN_GROUP = "group_name";
    }
}
