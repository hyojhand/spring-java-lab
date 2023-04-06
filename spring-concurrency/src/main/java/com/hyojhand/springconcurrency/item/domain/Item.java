package com.hyojhand.springconcurrency.item.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private String imageUrl;

    private Long stock;

    public Item(Long id) {
        this.id = id;
    }

    public Item(String name, int price, String imageUrl, Long stock) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public Item decreaseStock(Long quantity) {
        if(this.stock - quantity < 0) {
            throw new IllegalArgumentException("재고가 부족합니다");
        }

        this.stock = this.stock - quantity;
        return this;
    }

    public Long getStock() {
        return stock;
    }
}
