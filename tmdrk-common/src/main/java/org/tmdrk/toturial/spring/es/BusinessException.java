package org.tmdrk.toturial.spring.es;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Business exception.
 */
@AllArgsConstructor
@Data
public class BusinessException extends RuntimeException {
    private int code;
    private String message;

    /**
     * Instantiates a new Business exception.
     * 通过枚举创建
     * @param code the code
     */
    public BusinessException(IErrorCode code) {
        this.code = code.getCode();
        this.message = code.getDesc();
    }

    public BusinessException(IErrorCode code, String message) {
        this.code = code.getCode();
        this.message = message;
    }

}