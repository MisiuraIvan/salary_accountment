package com.example.salary_accountment.models;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
public class Salary {
    @Id
    private Integer salaryId;
    @Column(nullable = true)
    private float award;
    @Column(nullable = true)
    private double salary;
    @Column(nullable = true)
    @ColumnDefault("null")
    private double paidSalary;
    @ColumnDefault("false")
    @Column(nullable = true)
    private boolean prepayment;
    @OneToOne
    @JoinColumn(name="timeSheetId", referencedColumnName = "timeSheetId", nullable = false)
    private TimeSheet timeSheet;
    @OneToOne
    @JoinColumn(name="activityId", referencedColumnName = "activityId", nullable = false)
    private Activity activity;
    @OneToOne
    @JoinColumn(name="parametersId", referencedColumnName = "parametersId", nullable = true)
    private Parameters parameters;

    public Salary() {
    }

    public Salary(int id,float award, double salary, TimeSheet timeSheet, Activity activity, Parameters parameters) {
        this.salaryId=id;
        this.award = award;
        this.salary = salary;
        this.timeSheet = timeSheet;
        this.activity = activity;
        this.parameters = parameters;
    }

    public Salary(int id,float award, double salary, TimeSheet timeSheet, Activity activity, boolean prepayment, double paidSalary) {
        this.salaryId=id;
        this.award = award;
        this.salary = salary;
        this.timeSheet = timeSheet;
        this.activity = activity;
        this.prepayment = prepayment;
        this.paidSalary=paidSalary;
    }

    public Integer getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Integer salaryId) {
        this.salaryId = salaryId;
    }

    public float getAward() {
        return award;
    }

    public void setAward(float award) {
        this.award = award;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isPrepayment() {
        return prepayment;
    }

    public void setPrepayment(boolean prepayment) {
        this.prepayment = prepayment;
    }

    public TimeSheet getTimeSheet() {
        return timeSheet;
    }

    public void setTimeSheet(TimeSheet timeSheet) {
        this.timeSheet = timeSheet;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public double getPaidSalary() {
        return paidSalary;
    }

    public void setPaidSalary(double paidSalary) {
        this.paidSalary = paidSalary;
    }
}
