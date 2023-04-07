package com.hyojhand.springconcurrency.item.structure;

import com.hyojhand.springconcurrency.item.repository.LockRepository;
import com.hyojhand.springconcurrency.item.service.NamedLockItemService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NamedLockStructure {

    private final LockRepository lockRepository;
    private final NamedLockItemService namedLockItemService;

    public NamedLockStructure(LockRepository lockRepository, NamedLockItemService namedLockItemService) {
        this.lockRepository = lockRepository;
        this.namedLockItemService = namedLockItemService;
    }

    @Transactional
    public void namedLockBuyItem(Long id, int timeoutSeconds, Long quantity) {
        try {
            // NamedLock 획득
            lockRepository.getLock(id.toString(), timeoutSeconds);
            namedLockItemService.buyItem(id, quantity);
        } finally {
            // NamedLock 해제
            lockRepository.releaseLock(id.toString());
        }
    }
}
