package org.tmdrk.toturial.spring.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tmdrk.toturial.spring.es.dto.ProductSpuEsDTO;
import org.tmdrk.toturial.spring.es.dto.ProductSpuQueryDTO;

import java.util.Date;
import java.util.Optional;

/**
 * TODO
 *
 * @author Jie.Zhou
 * @date 2020/8/12 16:52
 */
@Slf4j
@RestController
public class ProductSpuController {
    @Autowired
    private IEsProductSpuService esProductSpuService;

    /**
     * 从Es通过条件分页查询
     */
    @GetMapping("/search")
    public Result<IPage<ProductSpuEsDTO>> search(@RequestParam(value = "ascs", required = false) String ascs,
                                                      @RequestParam(value = "descs", required = false) String descs,
                                                      ProductSpuQueryDTO query, Page<ProductSpuEsDTO> page) {
        Optional.ofNullable(ascs).map(a -> a.trim().split(",")).ifPresent(page::setAsc);
        Optional.ofNullable(descs).map(a -> a.trim().split(",")).ifPresent(page::setDesc);
        if (StringUtils.isBlank(query.getPlatform())) {
            query.setPlatform(String.valueOf(Platform.POINT_MALL.getPlatform()));
        }
        esProductSpuService.search(query, page);
        XPage xPage = BeanUtils.createBeanByTarget(page, XPage.class);
        xPage.setCurrentTime(new Date());
        return Result.success(xPage);
    }
}
