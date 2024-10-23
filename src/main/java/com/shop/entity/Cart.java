package com.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne // 회원 엔티티와 일대일 단방향 매핑, 따로 옵션 설정이 없으므로 FetchType.EAGER(즉시 로딩)
    @JoinColumn(name = "member_id") // 외래키 지정: name은 매핑할 외래키 이름, 지정하지 않으면 JPA가 알아서 ID를 찾아 매핑
    private Member member;
}
