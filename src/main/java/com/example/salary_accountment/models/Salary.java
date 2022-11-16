package com.example.salary_accountment.models;

import javax.persistence.*;

@Entity
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer salaryId;
    private float award;
    private double salary;
    private boolean prepayment;
    private boolean paid;
    @OneToOne
    @JoinColumn(name="timeSheetId", referencedColumnName = "timeSheetId", nullable = false)
    private TimeSheet timeSheet;
    @OneToOne
    @JoinColumn(name="activityId", referencedColumnName = "activityId", nullable = false)
    private Activity activity;
    @OneToOne
    private Parameters parameters;
}
