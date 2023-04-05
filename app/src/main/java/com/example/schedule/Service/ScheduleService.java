package com.example.schedule.Service;

import com.example.schedule.MainActivity;
import com.example.schedule.model.Schedule;

import java.util.ArrayList;
import java.util.List;


public class ScheduleService {

    public ScheduleService() {
    }

    List<Schedule> schedules = new ArrayList<>();
    {
        schedules.add(new Schedule(1, "Math", "12.02.2022"));
        schedules.add(new Schedule(2, "Lang", "12.02.2022"));
        schedules.add(new Schedule(3, "Aboba", "12.02.2022"));
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
