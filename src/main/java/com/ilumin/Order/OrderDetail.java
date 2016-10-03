package com.ilumin.Order;

import com.ilumin.Product.Product;
import lombok.Data;

import javax.persistence.*;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OrderID")
    public Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ProductID")
    public Product product;

}
