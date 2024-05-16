package org.example.pnubookstore.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.domain.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public String products(Model model){
        model.addAttribute("products",productService.findProductList());

        return "auction-board.html";
    }
}
