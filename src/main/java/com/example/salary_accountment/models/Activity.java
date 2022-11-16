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
}
