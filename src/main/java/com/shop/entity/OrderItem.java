package com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    
    // 하나의 상품은 여러 주문에 들어갈 수 있으므로 주문 상품 기준 다대일 단방향 매핑 
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    
    // 한 번의 주문에 여러 개의 상품을 주문할 수 있으므로 주문 상품과 주문을 다대일 단방향 매핑으로 설정
    // 외래키 하나로 양방향(주문에 속한 상품, 해당 주문) 조회 가능
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
