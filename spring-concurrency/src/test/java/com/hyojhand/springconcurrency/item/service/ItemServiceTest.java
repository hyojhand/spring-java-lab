package com.hyojhand.springconcurrency.item.service;

import com.hyojhand.springconcurrency.item.domain.Item;
import com.hyojhand.springconcurrency.item.exception.ItemNotExistException;
import com.hyojhand.springconcurrency.item.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void before() {
        Item item = new Item("아이템명", 1000, "이미지", 100L);
        itemRepository.save(item);
        log.info("아이템 저장");
    }

    @AfterEach
    void after() {
        log.info("아이템 삭제");
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("아이템을 구매하여 재고가 감소하는 테스트")
    void itemService_BuyItem_Test() {
        itemService.buyItem(1L, 1L);
        Item item = itemRepository.findById(1L).orElseThrow(() -> new ItemNotExistException("아이템이 존재하지 않습니다"));
        assertThat(item.getStock()).isEqualTo(99);
    }

}