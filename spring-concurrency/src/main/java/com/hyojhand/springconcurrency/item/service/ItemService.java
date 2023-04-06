package com.hyojhand.springconcurrency.item.service;

import com.hyojhand.springconcurrency.item.domain.Item;
import com.hyojhand.springconcurrency.item.exception.ItemNotExistException;
import com.hyojhand.springconcurrency.item.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public synchronized void buyItem(Long id, Long quantity) {
        transactionBuy(id, quantity);
    }

    @Transactional
    public void transactionBuy(Long id, Long quantity) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotExistException("아이템이 존재하지 않습니다"));
        item.decreaseStock(quantity);
        itemRepository.save(item);
    }
}
