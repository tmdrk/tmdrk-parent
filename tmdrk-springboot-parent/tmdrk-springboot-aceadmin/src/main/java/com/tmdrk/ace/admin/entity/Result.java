package com.tmdrk.ace.admin.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Result<T> implements Serializable {
    private int code = 200;
    private String msg;
    private T data;

    public Result() {}

    public Result(T data) {
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>();
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> failure(int code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> failure(IErrorCode code) {
        return new Result<>(code.getCode(), code.getDesc(), null);
    }

    public static <T> Result<T> failure(IErrorCode code, String message) {
        return new Result<>(code.getCode(), message, null);
    }

    public static <T> Result<T> of(T data) {
        return new Result<>(data);
    }

    public boolean successful() {
        return code == 200;
    }


}
