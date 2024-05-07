package org.example.pnubookstore.domain.product.repository;

import org.example.pnubookstore.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

}
