package org.tmdrk.toturial.common.util.cache;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * BargainItemSpuQueryDTO
 *
 * @author Jie.Zhou
 * @date 2020/9/16 11:29
 */
@Data
@Accessors(chain = true)
public class BargainItemSpuQueryDTO {
    private Long activityId;
    private String customerId;
    private String description;
    private Long page;
    private Long size;
    private Long total;
    private Long totalPages;
    private List<BargainsInfoDTO> list;
}
