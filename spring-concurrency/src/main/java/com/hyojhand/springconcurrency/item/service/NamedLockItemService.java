package com.hyojhand.springconcurrency.item.service;

import com.hyojhand.springconcurrency.item.domain.Item;
import com.hyojhand.springconcurrency.item.exception.ItemNotExistException;
import com.hyojhand.springconcurrency.item.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NamedLockItemService {

    private final ItemRepository itemRepository;

    public NamedLockItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // 부모 트랜잭션과 분리
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buyItem(Long id, Long quantity) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotExistException("아이템이 존재하지 않습니다"));
        item.decreaseStock(quantity);
    }

}
