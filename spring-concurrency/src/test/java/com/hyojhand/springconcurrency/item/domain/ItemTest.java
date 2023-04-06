package com.hyojhand.springconcurrency.item.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ItemTest {

    @Test
    @DisplayName("id가 같으면 같은 객체임을 테스트")
    void item_SameObject_Test() {
        Item item = new Item(1L);
        assertThat(item).isEqualTo(new Item(1L));
    }

    @Test
    @DisplayName("아이템 구매 재고 감소 테스트")
    void item_DecreaseStock_Test() {
        Item item = new Item("아이템명", 1000, "이미지", 5L);
        item.decreaseStock(1L);
        assertThat(item.getStock()).isEqualTo(4);
    }

    @Test
    @DisplayName("아이템 구매 재고 부족 테스트")
    void item_NotEnoughStock_Exception() {
        Item item = new Item("아이템명", 1000, "이미지", 5L);
        assertThatThrownBy(() -> {
            item.decreaseStock(6L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다");
    }
}
