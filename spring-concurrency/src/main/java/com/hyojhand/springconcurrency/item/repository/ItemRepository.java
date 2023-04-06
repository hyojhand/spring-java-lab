package com.hyojhand.springconcurrency.item.repository;

import com.hyojhand.springconcurrency.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Item> findWithPessimisticLockById(Long id);
}
