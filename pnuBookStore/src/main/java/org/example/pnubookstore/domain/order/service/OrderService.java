package org.example.pnubookstore.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.exception.Exception404;
import org.example.pnubookstore.domain.order.OrderExceptionStatus;
import org.example.pnubookstore.domain.order.dto.CreateOrderDto;
import org.example.pnubookstore.domain.order.entity.Order;
import org.example.pnubookstore.domain.order.repository.OrderJpaRepository;
import org.example.pnubookstore.domain.order.repository.ProductJpaRepositoryForOrder;
import org.example.pnubookstore.domain.order.repository.UserJpaRepositoryForOrder;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final ProductJpaRepositoryForOrder productJpaRepositoryForOrder;
    private final UserJpaRepositoryForOrder userJpaRepositoryForOrder;

    @Transactional
    public void createOrder(CreateOrderDto createOrderDto){
        Product findedProduct = productJpaRepositoryForOrder.findById(createOrderDto.getProductId())
                        .orElseThrow(() -> new Exception404(OrderExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));

        if(createOrderDto.getBuyerNickname().equals(createOrderDto.getSellerNickname())){
            throw new Exception404(OrderExceptionStatus.SAME_SELLER_BUYER.getErrorMessage());
        }

        User seller = userJpaRepositoryForOrder.findByNickname(createOrderDto.getSellerNickname())
                        .orElseThrow(() -> new Exception404(OrderExceptionStatus.USER_NOT_FOUND.getErrorMessage()));

        User buyer = userJpaRepositoryForOrder.findByNickname(createOrderDto.getBuyerNickname())
                .orElseThrow(() -> new Exception404(OrderExceptionStatus.USER_NOT_FOUND.getErrorMessage()));

        orderJpaRepository.save(Order.builder()
                .product(findedProduct)
                .seller(seller)
                .buyer(buyer)
                .money(createOrderDto.getMoney())
                .build());
    }

    @Transactional
    void deleteOrder(Long orderId){
        orderJpaRepository.deleteById(orderId);
    }


}
