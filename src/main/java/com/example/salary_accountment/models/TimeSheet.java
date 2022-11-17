package com.example.salary_accountment.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class TimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer timeSheetId;
    private int workTime,sickLeave,holiday,overtime,absenteeism;
    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "userId", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name="dateId", referencedColumnName = "dateId", nullable = false)
    private Date date;
    @OneToOne
    private Salary salary;

    public Integer getTimeSheetId() {
        return timeSheetId;
    }

    public void setTimeSheetId(Integer timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(int sickLeave) {
        this.sickLeave = sickLeave;
    }

    public int getHoliday() {
        return holiday;
    }

    public void setHoliday(int holiday) {
        this.holiday = holiday;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public int getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(int absenteeism) {
        this.absenteeism = absenteeism;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }
}
