package com.shop.repository;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Cart;
import com.shop.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    // 회원 엔티티 먼저 생성
    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@test.com");
        memberFormDto.setName("임꺽정");
        memberFormDto.setPassword("1111");
        memberFormDto.setAddress("서울시 강남구");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest() {
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        em.flush(); // 영속성 컨텍스트에 데이터 저장 -> 트랜잭션 -> flush()로 db 반영
        em.clear(); // 회원 엔티티도 같이 가져오는지 확인을 위해 영속성 컨텍스트 비우기
        
        // 저장된 장바구니 엔티티 조회
        Cart savedCart = cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);
        // 처음에 저장한 member 엔티티의 id와 savedCart에 매핑된 member 엔티티의 id 비교
        assertEquals(savedCart.getMember().getId(), member.getId()); 
    }
}