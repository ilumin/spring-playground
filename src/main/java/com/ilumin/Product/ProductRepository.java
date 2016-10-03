package com.ilumin.Product;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
