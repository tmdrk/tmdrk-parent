package org.tmdrk.toturial.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.tmdrk.toturial.spring.es.IErrorCode;

/**
 * BusinessException
 *
 * @author Jie.Zhou
 * @date 2021/1/25 12:29
 */
@AllArgsConstructor
@Data
public class BusinessException extends RuntimeException{
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
