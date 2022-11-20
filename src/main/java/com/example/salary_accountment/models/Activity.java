package com.example.salary_accountment.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer activityId;
    private int culturalEvents, delay, bonus, respect, uniform, timeliness, badHabits;
    @ManyToOne
    @JoinColumn(name="dateId", referencedColumnName = "dateId", nullable = false)
    private Date date;
    @OneToOne
    private Salary salary;
    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "userId", nullable = false)
    private User user;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public int getCulturalEvents() {
        return culturalEvents;
    }

    public void setCulturalEvents(int culturalEvents) {
        this.culturalEvents = culturalEvents;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getRespect() {
        return respect;
    }

    public void setRespect(int respect) {
        this.respect = respect;
    }

    public int getUniform() {
        return uniform;
    }

    public void setUniform(int uniform) {
        this.uniform = uniform;
    }

    public int getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(int timeliness) {
        this.timeliness = timeliness;
    }

    public int getBadHabits() {
        return badHabits;
    }

    public void setBadHabits(int badHabits) {
        this.badHabits = badHabits;
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
