package com.example.schedule.model;

public class Group {
    private Integer id;

    private String groupNumber;

    public Group() {
    }

    public Group(Integer id, String groupNumber) {
        this.id = id;
        this.groupNumber = groupNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Override
    public String toString() {
        return "Группа № " + groupNumber;
    }
}
