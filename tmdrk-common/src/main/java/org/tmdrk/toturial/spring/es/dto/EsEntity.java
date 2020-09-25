package org.tmdrk.toturial.spring.es.dto;

import java.io.Serializable;

/**
 * @author jianwei.long
 * @Description: es
 * @date 2020/6/17 10:49
 */
public interface EsEntity<T> extends Serializable {
    T getIndexId();
}
