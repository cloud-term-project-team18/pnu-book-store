package org.example.pnubookstore.domain.order.repository;

import org.example.pnubookstore.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
