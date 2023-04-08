package com.example.schedule.activityResources;

import com.example.schedule.model.Group;

import java.util.ArrayList;
import java.util.List;

public class MainActivityRes {

    private List<Group> groups = new ArrayList<>();

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void addNewGroup(Group group){
        groups.add(group);
    }
}
