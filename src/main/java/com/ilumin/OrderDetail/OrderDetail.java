package com.ilumin.OrderDetail;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @Column(name = "ID")
    public Long id;

    @Column(name = "UnitPrice")
    public Double unitPrice = 0D;

    @Column(name = "Quantity")
    public Integer quantity = 1;

    @Column(name = "Discount")
    public Float discount = 0F;

    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "OrderID")
    // public Orders order;
    //
    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "ProductID")
    // public Product product;

    @Column(name = "OrderID")
    public Long orderId;

    @Column(name = "ProductID")
    public Long productId;

}
