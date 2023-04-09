package com.hyojhand.springconcurrency.item.service.redis;

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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class RedissonServiceTest {

    @Autowired
    private RedissonService redissonService;

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
    @DisplayName("Redis Redisson 적용 - 동시에 100개의 아이템 구매 요청 테스트")
    void buyItem_RedisRedisson_Test() throws InterruptedException {
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                        try {
                            redissonService.buyItem(1L, 1L);
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