package com.example.salary_accountment.models;
import javax.persistence.*;

@Entity
public class Parameters {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer parametersId;
    private double netSalary, pension, fszn;
    @OneToOne
    @JoinColumn(name="salaryId", referencedColumnName = "salaryId", nullable = false)
    private Salary salary;
}
