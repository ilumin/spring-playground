package com.ilumin.Product;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;
import java.util.Set;

@RepositoryRestController
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findByProductIdIn(Set<Long> productIdCollection);
}
