package io.jmlim.mongoex.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ApiValidException.class})
    @ResponseBody
    private ResponseEntity handleApiValidException(HttpStatus status, ApiValidException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(status)
                .body(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    private ResponseEntity handleException(HttpStatus status, Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(status)
                .body(exception.getMessage());
    }

}
