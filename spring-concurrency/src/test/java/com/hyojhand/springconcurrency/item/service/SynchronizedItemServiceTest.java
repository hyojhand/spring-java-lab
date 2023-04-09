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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class SynchronizedItemServiceTest {

    @Autowired
    private SynchronizedItemService itemService;

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
    @DisplayName("synchronized 적용 - 동시에 100개의 아이템 구매 요청 테스트")
    void buyItem_Synchronized_Test() throws InterruptedException {
        int threadCount = 100;

        // 멀티스레드 이용 ExecutorService : 비동기를 단순하게 처리할 수 있도록 해주는 java api
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // 다른 스레드에서 수행이 완료될 때 까지 대기할 수 있도록 도와주는 API, 요청이 끝날때 까지 기다림
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                        itemService.buyItem(1L, 1L);
                        countDownLatch.countDown();
                    }
            );
        }

        countDownLatch.await();

        Item item = itemRepository.findById(1L)
                .orElseThrow(() -> new ItemNotExistException("아이템이 존재하지 않습니다"));
        assertThat(item.getStock()).isEqualTo(0L);
    }

}