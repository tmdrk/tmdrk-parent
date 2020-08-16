package org.tmdrk.toturial.spring.es.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 秒杀活动
 *
 * @author William
 */
@Data
public class ThemeDTO implements Serializable {

    /**
     * 主题ID
     */
    private Long id;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * SPU编码
     */
    private String spuCode;

    /**
     * 排序
     */
    private Integer sort;

}
