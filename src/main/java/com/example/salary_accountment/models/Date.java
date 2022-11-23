package com.example.salary_accountment.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Date {
    @Id
    private Integer dateId;
    private String month;
    private int year,workHours;
    @OneToMany
    private List<TimeSheet> timeSheets;

    public Date(){

    }

    public Date(int i, String month, int year, int workHours) {
        this.dateId=i;
        this.month=month;
        this.year=year;
        this.workHours=workHours;
    }

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWorkHours() {
        return workHours;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public List<TimeSheet> getTimeSheets() {
        return timeSheets;
    }

    public void setTimeSheets(List<TimeSheet> timeSheets) {
        this.timeSheets = timeSheets;
    }
}
