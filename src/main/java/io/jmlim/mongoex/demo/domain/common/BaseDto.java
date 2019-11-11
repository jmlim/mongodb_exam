package io.jmlim.mongoex.demo.domain.common;

public interface BaseDto<T> {
    T toEntity();
}