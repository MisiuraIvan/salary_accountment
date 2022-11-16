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
}
