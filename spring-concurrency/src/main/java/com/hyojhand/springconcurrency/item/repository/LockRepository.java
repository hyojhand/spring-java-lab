package com.hyojhand.springconcurrency.item.repository;

import com.hyojhand.springconcurrency.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LockRepository extends JpaRepository<Item, Long> {

    @Query(value = "select GET_LOCK(:key, :timeoutSeconds)", nativeQuery = true)
    void getLock(String key, int timeoutSeconds);

    @Query(value = "select RELEASE_LOCK(:key)", nativeQuery = true)
    void releaseLock(String key);

}
