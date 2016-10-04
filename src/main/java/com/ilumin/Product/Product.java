package com.ilumin.Product;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "ProductID")
    public Long productId;

    @Column(name = "ProductName")
    public String productName;

    @Column(name = "QuantityPerUnit")
    public String quantityPerUnit;

    @Column(name = "UnitPrice")
    public Double unitPrice;

    @Column(name = "UnitsInStock")
    public Integer unitsInStock;

    @Column(name = "UnitsOnOrder")
    public Integer unitsOnOrder;

    @Column(name = "ReorderLevel")
    public Integer reorderLevel;

    @Column(name = "Discontinued")
    public Character discontinued;

    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "SupplierID")
    // public Supplier supplier;
    //
    // @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    // public List<OrderDetail> orderDetails;

    @Column(name = "SupplierID")
    public Long supplierID;

}
