package com.ilumin.Employee;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

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

    @Column(name = "BirthDate")
    public Calendar birthDate;

    @Column(name = "HireDate")
    public Calendar hireDate;

    @Column(name = "Address")
    public String address;

    @Column(name = "City")
    public String city;

    @Column(name = "Region")
    public String region;

    @Column(name = "PostalCode")
    public String postalCode;

    @Column(name = "Country")
    public String country;

    @Column(name = "HomePhone")
    public String homePhone;

    @Column(name = "Extension")
    public String extension;

    @Column(name = "Photo")
    public String Photo;

    @Column(name = "Notes")
    public String notes;

}
