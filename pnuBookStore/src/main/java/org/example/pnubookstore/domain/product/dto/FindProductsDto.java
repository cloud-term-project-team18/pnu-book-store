package org.example.pnubookstore.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class FindProductsDto {
    private String productName;
    private String productPictureUrl;
    private String seller;
    private Integer price;
}
