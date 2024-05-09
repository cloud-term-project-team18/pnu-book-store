package org.example.pnubookstore.domain.product.dto;

import lombok.*;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;
import org.example.pnubookstore.domain.product.entity.constant.UseStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FindProductDto {
    private String sellerName;
    private String subjectName;
    private String professor;
    private String department;
    private String productName;
    private Integer price;
    private String description;
    private String author;
    private LocalDateTime pubDate;
    private Boolean isBargain;
    private String canBargainReason;
    private SaleStatus saleStatus;
    private UseStatus underline;
    private UseStatus note;
    private Boolean naming;
    private Boolean discolor;
    private Boolean damage;
    private List<String> productPictureList;

    public static FindProductDto of(Product product, List<String> urlList){
        return FindProductDto.builder()
                .sellerName(product.getSeller().getNickname())
                .subjectName(product.getSubject().getSubjectName())
                .professor(product.getSubject().getProfessor())
                .department(product.getSubject().getDepartment())
                .productName(product.getProductName())
                .price(product.getPrice())
                .description(product.getDescription())
                .author(product.getAuthor())
                .pubDate(product.getPubDate())
                .isBargain(product.getIsBargain())
                .canBargainReason(product.getCanBargainReason())
                .saleStatus(product.getSaleStatus())
                .underline(product.getUnderline())
                .note(product.getNote())
                .naming(product.getNaming())
                .discolor(product.getDiscolor())
                .damage(product.getDamage())
                .productPictureList(urlList)
                .build();
    }
}