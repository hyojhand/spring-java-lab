package com.hyojhand.springconcurrency.item.service;

import com.hyojhand.springconcurrency.item.domain.Item;
import com.hyojhand.springconcurrency.item.exception.ItemNotExistException;
import com.hyojhand.springconcurrency.item.repository.ItemRepository;
import com.hyojhand.springconcurrency.item.structure.NamedLockStructure;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class NamedLockItemServiceTest {

    @Autowired
    private NamedLockStructure namedLockStructure;

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
    @DisplayName("네임드 락 적용 - 동시에 100개의 아이템 구매 요청 테스트")
    public void pessimistic_lock_test() throws InterruptedException {
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                        try {
                            namedLockStructure.namedLockBuyItem(1L, 3000, 1L);
                        } finally {
                            countDownLatch.countDown();
                        }
                    }
            );
        }

        countDownLatch.await();

        Item item = itemRepository.findById(1L)
                .orElseThrow(() -> new ItemNotExistException("아이템이 존재하지 않습니다"));
        assertThat(item.getStock()).isEqualTo(0L);
    }

}
