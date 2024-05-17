package org.example.pnubookstore.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.domain.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 물품 리스트
    @GetMapping(value = "/products")
    public String products(Model model){
        model.addAttribute("products", productService.findProductList());

        return "board/auction-board.html";
    }


    // 물품 조회 페이지
    @GetMapping(value = "/products/{productId}")
    public String productDetail(Model model, @PathVariable("productId") Long productId){
        model.addAttribute("product", productService.findProduct(productId));

        return "board/auction-detail.html";
    }

    @GetMapping(value = "/product")
    public String productPage(){
        // 물품 등록 페이지
        return "board/product";
    }
}
