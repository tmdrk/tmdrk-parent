package org.tmdrk.toturial.spring.es;

import lombok.Data;

import java.util.Date;

@Data
public class XPage<T> extends Page<T> {

    private Date currentTime;

}
