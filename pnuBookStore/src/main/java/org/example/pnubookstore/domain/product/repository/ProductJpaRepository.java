package org.example.pnubookstore.domain.product.repository;

import org.example.pnubookstore.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p from Product p "
        + "join fetch p.subject "
        + "where p.id = :productId")
    Optional<Product> findByIdFetchJoin(@Param("productId") Long productId);

    List<Product> findAll();
}
