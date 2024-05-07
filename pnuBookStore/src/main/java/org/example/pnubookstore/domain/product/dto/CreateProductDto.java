package org.example.pnubookstore.domain.product.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;
import org.example.pnubookstore.domain.product.entity.constant.UseStatus;
import org.example.pnubookstore.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateProductDto {

    private String sellerEmail;
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

    private String subjectName;
    private String professor;
    private String department;
}
