package org.example.pnubookstore.domain.product.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import org.example.pnubookstore.domain.base.AuditingEntity;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;
import org.example.pnubookstore.domain.product.entity.constant.UseStatus;
import org.example.pnubookstore.domain.user.entity.User;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product_tb")
public class Product extends AuditingEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="seller", nullable=false, referencedColumnName = "id")
	private User seller;

	@ManyToOne
	@JoinColumn(name="subject", nullable=false, referencedColumnName = "id")
	private Subject subject;

	@Column(nullable = false)
	private String productName;
	@Column(nullable = false)
	private Integer price;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Column(nullable = false)
	private String author;
	@Column(nullable = false)
	private LocalDateTime pubDate;
	@Column(nullable = false)
	private Boolean isBargain;
	@Column(length = 1000)
	private String canBargainReason;
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private SaleStatus saleStatus;
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UseStatus underline;
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UseStatus note;
	@Column(nullable = false)
	private Boolean naming;
	@Column(nullable = false)
	private Boolean discolor;
	@Column(nullable = false)
	private Boolean damage;

	@Builder
	public Product(Long id, User seller, Subject subject, String productName, Integer price, String description, String author,
		LocalDateTime pubDate, Boolean isBargain, String canBargainReason, SaleStatus saleStatus, UseStatus underline,
		UseStatus note, Boolean naming, Boolean discolor, Boolean damage) {
		this.id = id;
		this.seller = seller;
		this.subject = subject;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.author = author;
		this.pubDate = pubDate;
		this.isBargain = isBargain;
		this.canBargainReason = canBargainReason;
		this.saleStatus = saleStatus;
		this.underline = underline;
		this.note = note;
		this.naming = naming;
		this.discolor = discolor;
		this.damage = damage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Product product = (Product)o;
		return Objects.equals(getId(), product.getId()) && Objects.equals(getProductName(),
			product.getProductName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getProductName());
	}
}
