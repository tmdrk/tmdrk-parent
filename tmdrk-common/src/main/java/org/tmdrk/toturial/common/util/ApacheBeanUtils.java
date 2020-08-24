package org.tmdrk.toturial.common.util;

import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO
 *
 * @author Jie.Zhou
 * @date 2020/8/18 10:03
 */
public class ApacheBeanUtils {
    public static void main(String[] args) {

    }
}
@Data
@AllArgsConstructor
class Person1{
    private int id;
    @Alias("name")
    private String realName;
    @Alias("address")
    private String addr;
}

@Data
class Person2{
    private int id;
    private String name;
    private String address;
}