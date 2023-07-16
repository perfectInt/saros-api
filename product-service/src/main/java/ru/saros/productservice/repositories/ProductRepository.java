package ru.saros.productservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.saros.productservice.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
