package org.example.pnubookstore.domain.order.repository;

import org.example.pnubookstore.domain.order.entity.Order;
import org.example.pnubookstore.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    Page<Order> findOrderBySeller(User seller, Pageable pageable);
    Page<Order> findOrderByBuyer(User seller, Pageable pageable);
}
