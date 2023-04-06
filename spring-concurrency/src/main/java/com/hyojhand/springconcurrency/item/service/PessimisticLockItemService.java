package com.hyojhand.springconcurrency.item.service;

import com.hyojhand.springconcurrency.item.domain.Item;
import com.hyojhand.springconcurrency.item.exception.ItemNotExistException;
import com.hyojhand.springconcurrency.item.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessimisticLockItemService {

    private final ItemRepository itemRepository;

    public PessimisticLockItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void buyItem(Long id, Long quantity) {
        Item item = itemRepository.findWithPessimisticLockById(id)
                .orElseThrow(() -> new ItemNotExistException("아이템이 존재하지 않습니다"));
        item.decreaseStock(quantity);
    }
}
