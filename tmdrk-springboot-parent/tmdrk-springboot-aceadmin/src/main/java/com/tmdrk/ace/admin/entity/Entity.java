package com.tmdrk.ace.admin.entity;

import java.io.Serializable;

/**
 * TODO
 *
 * @author Jie.Zhou
 * @date 2020/8/12 17:00
 */
public interface Entity<T> extends Serializable {
    T getId();
}
