package com.ilumin.Order;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Calendar;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "OrderID")
    public Long orderID;

    @Column(name = "CustomerID")
    public String customerID;

    @Column(name = "EmployeeID")
    public Long employeeID;

    @Column(name = "OrderDate")
    public Calendar orderDate;

    @Column(name = "RequiredDate")
    public Calendar requiredDate;

    @Column(name = "ShippedDate")
    public Calendar shippedDate;

    @Column(name = "ShipVia")
    public Long shipVia;

    @Column(name = "Freight")
    public Double freight;

    @Column(name = "ShipName")
    public String shipName;

    @Column(name = "ShipAddress")
    public String shipAddress;

    @Column(name = "ShipCity")
    public String shipCity;

    @Column(name = "ShipRegion")
    public String shipRegion;

    @Column(name = "ShipPostalCode")
    public String shipPostalCode;

    @Column(name = "ShipCountry")
    public String shipCountry;

}
