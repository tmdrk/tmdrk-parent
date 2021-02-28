package org.tmdrk.toturial.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * People
 *
 * @author Jie.Zhou
 * @date 2021/2/4 11:50
 */
@Data
@Accessors(chain = true)
public class People {
    private Long id;
    private String username;
    private String idCard;
    private Integer age;
    private Date birthDay;
    private List<User> users;
}
