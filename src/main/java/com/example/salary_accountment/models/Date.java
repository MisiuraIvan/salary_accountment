package com.example.salary_accountment.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Date {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer dateId;
    private String month;
    private int year,workHours;
    @OneToMany
    private List<TimeSheet> timeSheets;
}
