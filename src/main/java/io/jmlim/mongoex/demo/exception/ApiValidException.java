package io.jmlim.mongoex.demo.exception;

import lombok.Getter;

public class ApiValidException extends RuntimeException {
    @Getter
    private String message;

    public ApiValidException(String message) {
        super(message);
        this.message = message;
    }
}
