package ru.saros.productservice.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.saros.productservice.models.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
