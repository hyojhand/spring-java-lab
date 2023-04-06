package com.hyojhand.springconcurrency.item.exception;

public class ItemNotExistException extends RuntimeException {

    public ItemNotExistException(String message) {
        super(message);
    }
}
