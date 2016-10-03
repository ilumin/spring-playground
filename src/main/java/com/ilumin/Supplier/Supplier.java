package com.ilumin.Supplier;

import com.ilumin.Product.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "suppliers")
public class Supplier {

    @Id
    @Column(name = "SupplierID")
    public Long supplierID;

    @Column(name = "CompanyName")
    public String companyName;

    @Column(name = "ContactName")
    public String contactName;

    @Column(name = "ContactTitle")
    public String contactTitle;

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

    @Column(name = "Phone")
    public String Phone;

    @Column(name = "Fax")
    public String fax;

    @Column(name = "HomePage")
    public String homePage;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    public List<Product> products;

}
