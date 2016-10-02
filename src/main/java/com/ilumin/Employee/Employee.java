package com.ilumin.Employee;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EmployeeID")
    public Long employeeId;

    @Column(name = "LastName")
    public String lastName;

    @Column(name = "FirstName")
    public String firstName;

    @Column(name = "Title")
    public String title;

    @Column(name = "TitleOfCourtesy")
    public String titleOfCourtesy;

}
