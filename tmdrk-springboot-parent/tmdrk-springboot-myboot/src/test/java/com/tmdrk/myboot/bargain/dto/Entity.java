package com.tmdrk.myboot.bargain.dto;

import java.io.Serializable;

public interface Entity<T> extends Serializable {
    T getId();
}
