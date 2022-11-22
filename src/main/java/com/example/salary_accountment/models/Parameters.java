package com.example.salary_accountment.models;
import javax.persistence.*;

@Entity
public class Parameters {
    @Id
    private Integer parametersId;
    private double netSalary, pension, fszn;
    @OneToOne
    @JoinColumn(name="salaryId", referencedColumnName = "salaryId", nullable = false)
    private Salary salary;

    public Integer getParametersId() {
        return parametersId;
    }

    public void setParametersId(Integer parametersId) {
        this.parametersId = parametersId;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getPension() {
        return pension;
    }

    public void setPension(double pension) {
        this.pension = pension;
    }

    public double getFszn() {
        return fszn;
    }

    public void setFszn(double fszn) {
        this.fszn = fszn;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }
}
