package org.example.pnubookstore.domain.product.repository;

import org.example.pnubookstore.domain.product.entity.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPictureJpaRepository extends JpaRepository<ProductPicture, Long> {

}
