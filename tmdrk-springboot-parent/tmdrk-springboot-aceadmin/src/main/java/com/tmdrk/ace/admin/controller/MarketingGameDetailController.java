package com.tmdrk.ace.admin.controller;

import com.tmdrk.ace.admin.entity.MarketingGameDetail;
import com.tmdrk.ace.admin.service.MarketingGameDetailService;
import com.tmdrk.ace.admin.util.ResultUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * (MarketingGameDetail)控制层
 *
 * @author makejava
 * @since 2020-09-01 21:11:49
 */
@RestController
@RequestMapping("/marketingGameDetail")
public class MarketingGameDetailController {
    /**
     * 服务对象
     */
    @Resource
    private MarketingGameDetailService marketingGameDetailService;

    ExecutorService executorService = Executors.newFixedThreadPool(50);

    /**
     * 通过主键查询单条数据
     *
     * @param marketingGameDetail 参数对象
     * @return 单条数据
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResultUtil.Result selectOne(MarketingGameDetail marketingGameDetail) {
        MarketingGameDetail result = marketingGameDetailService.selectById(marketingGameDetail.getId());
        if (result != null) {
            return ResultUtil.success(200,result);
        }
        return ResultUtil.fail("查询失败");
    }

    /**
     * 新增一条数据
     *
     * @param marketingGameDetail 实体类
     * @return Response对象
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public ResultUtil.Result insert(@RequestBody MarketingGameDetail marketingGameDetail) {
        int result = marketingGameDetailService.insert(marketingGameDetail);
        if (result > 0) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.fail("新增失败");
    }

    /**
     * 修改一条数据
     *
     * @param marketingGameDetail 实体类
     * @return Response对象
     */
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResultUtil.Result update(@RequestBody MarketingGameDetail marketingGameDetail) {
        MarketingGameDetail result = marketingGameDetailService.update(marketingGameDetail);
        if (result != null) {
            return ResultUtil.success(200,result);
        }
        return ResultUtil.fail("修改失败");
    }

    /**
     * 删除一条数据
     *
     * @param marketingGameDetail 参数对象
     * @return Response对象
     */
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public ResultUtil.Result delete(MarketingGameDetail marketingGameDetail) {
        int result = marketingGameDetailService.deleteById(marketingGameDetail.getId());
        if (result > 0) {
            return ResultUtil.success("删除成功");
        }
        return ResultUtil.fail("删除失败");
    }

    /**
     * 查询全部
     *
     * @return Response对象
     */
    @RequestMapping(value = "selectAll", method = RequestMethod.GET)
    public ResultUtil.Result selectAll() {
        List<MarketingGameDetail> marketingGameDetails = marketingGameDetailService.selectAll();
        if (marketingGameDetails != null) {
            return ResultUtil.success(200,marketingGameDetails);
        }
        return ResultUtil.fail("查询失败");
    }

    /**
     * 分页查询
     *
     * @param start 偏移
     * @param limit 条数
     * @return Response对象
     */
    @RequestMapping(value = "selectPage", method = RequestMethod.GET)
    public ResultUtil.Result selectPage(Integer start, Integer limit) {
        List<MarketingGameDetail> marketingGameDetails = marketingGameDetailService.selectPage(start, limit);
        if (marketingGameDetails != null) {
            return ResultUtil.success(200, marketingGameDetails);
        }
        return ResultUtil.fail("查询失败");
    }

    @RequestMapping(value = "updateBatch", method = RequestMethod.PUT)
    public ResultUtil.Result updateBatch(@RequestBody MarketingGameDetail marketingGameDetail) {
        try{
            int result = marketingGameDetailService.updateBatch(marketingGameDetail);
            return ResultUtil.success(200,result);
        }catch (Exception e){
            return ResultUtil.fail("批量修改失败");
        }
    }

    @RequestMapping(value = "updateBatch2", method = RequestMethod.PUT)
    public ResultUtil.Result updateBatch2() {
        try{
            Random random = new Random();
            for(int i=0;i<300;i++){
                int ind = random.nextInt(4);
                System.out.println(ind);
                MarketingGameDetail detail = new MarketingGameDetail();
                detail.setGameId(ind+1L);
                executorService.execute(()->marketingGameDetailService.updateBatch(detail));
            }
            return ResultUtil.success(200);
        }catch (Exception e){
            return ResultUtil.fail("批量修改失败");
        }
    }


}