package com.ilumin.Order;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long> {
}
