package org.tmdrk.toturial.spring.es.service;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.tmdrk.toturial.spring.app.MainConfigOfEs;
import org.tmdrk.toturial.spring.es.IPage;
import org.tmdrk.toturial.spring.es.Page;
import org.tmdrk.toturial.spring.es.dto.BargainQueryDTO;
import org.tmdrk.toturial.spring.es.dto.Bargains;

import java.util.Arrays;
import java.util.Date;

/**
 * IEsBargainServiceTest
 *
 * @author Jie.Zhou
 * @date 2020/9/24 10:14
 */
public class IEsBargainServiceTest {
    public AnnotationConfigApplicationContext init(Class<?>... classes){
        return init(false,false,classes);
    }
    public AnnotationConfigApplicationContext init(boolean b,Class<?>... classes){
        return init(b,false,classes);
    }
    public AnnotationConfigApplicationContext init(boolean b,boolean d,Class<?>... classes){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(classes);
        if(b){
            String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
            System.out.println("print beanDefinitionNames -------------------------start");
            Arrays.stream(beanDefinitionNames).forEach(str->{
                if(d){
                    str +=  "=========================="+annotationConfigApplicationContext.getBeanDefinition(str);
                }
                System.out.println(str);      });
            System.out.println("print beanDefinitionNames -------------------------end");
        }
        return annotationConfigApplicationContext;
    }

    @Test
    public void esTest() throws InterruptedException {
        AnnotationConfigApplicationContext annocfgappctx = init(true, MainConfigOfEs.class);
        IEsBargainService esBargainService = (IEsBargainService)annocfgappctx.getBean("esBargainService");

        System.out.println("根据索引id查询文档是否存在开始");
        boolean b = esBargainService.existsIndex("bocfd_bargain");
        System.out.println("文档是否存在:"+b);
        System.out.println("根据索引id查询文档是否存在结束");

        System.out.println("新增开始");
//        for(int i=0;i<20;i++){
//            Bargains bargains = new Bargains();
//            Long now = System.currentTimeMillis()/1000;
//            bargains.setId(i+1L).setActivityId(4L).setAddress("高碑店5号院").setArea("朝阳区").setBargainedPrice(13)
//                    .setBargainPrice(0).setCity("北京市").setCreateTime(new Date()).setCustomerId(1000+i%3+"").setCustomerName("张三"+i%3)
//                    .setDel(false).setHeadImg("http://www.baidu.com").setIndexId(i+1L+"").setItemId(2L).setLimitTime(now.intValue())
//                    .setMainPic("http://www.baidu.com?id=1").setMobile("13989087867").setPhone("13467648767").setPrice(130)
//                    .setProvince("北京市").setReceiverName("张三"+i%3).setSkuCode("PB1002"+i).setSkuName("测试产品"+i).setStatus(i%2);
//            esBargainService.put(bargains);
//        }
        System.out.println("新增结束");

//        System.out.println("processor批量新增开始");
//        List<Bargains> list = new ArrayList<>();
//        for(int i=0;i<3;i++){
//            Bargains bargains = new Bargains();
//            Long now = System.currentTimeMillis()/1000;
//            bargains.setId(i+1L).setActivityId(4L).setAddress("高碑店5号院").setArea("朝阳区").setBargainedPrice(13)
//                    .setBargainPrice(0).setCity("北京市").setCreateTime(new Date()).setCustomerId(1000+i%3+"").setCustomerName("张三"+i%3)
//                    .setDel(false).setHeadImg("http://www.baidu.com").setIndexId(i+1L+"").setItemId(2L).setLimitTime(now.intValue())
//                    .setMainPic("http://www.baidu.com?id=1").setMobile("13989087867").setPhone("13467648767").setPrice(130)
//                    .setProvince("北京市").setReceiverName("张三"+i%3).setSkuCode("PB1002"+i).setSkuName("测试产品"+i).setStatus(i%2);
//            list.add(bargains);
//        }
//        esBargainService.putAllProcessor(list);
//        System.out.println("processor批量新增结束");

        System.out.println("根据索引id查询开始");
        Bargains bargains1 = esBargainService.selectByIndexId("5");
        System.out.println(JSON.toJSONString(bargains1));
        System.out.println("根据索引id查询结束");

        System.out.println("分页查询开始");
        BargainQueryDTO query = new BargainQueryDTO();
//        query.setCustomerId("1002");
//        query.setStatus(1);
//        query.setDesc("skuCode");
        IPage<Bargains> page = new Page<>();
        page.setSize(30);
        IPage<Bargains> bargainsIPage = esBargainService.selectPage(query, page);
        bargainsIPage.getList().stream().forEach(bargains -> {
            System.out.println(JSON.toJSONString(bargains));
        });
        System.out.println("分页查询结束");

//        String bar = "{\"activityId\":4,\"address\":\"高碑店5号院\",\"area\":\"朝阳区\",\"bargainPrice\":0,\"bargainedPrice\":13,\"city\":\"北京市\",\"createTime\":1600917925342,\"customerId\":\"1000\",\"customerName\":\"张三0\",\"del\":false,\"headImg\":\"http://www.baidu.com\",\"id\":1,\"indexId\":\"1\",\"itemId\":2,\"limitTime\":-480,\"mainPic\":\"http://www.baidu.com?id=1\",\"mobile\":\"13989087867\",\"phone\":\"13467648767\",\"price\":130,\"province\":\"北京市\",\"receiverName\":\"张三0\",\"skuCode\":\"PB10020\",\"skuName\":\"测试产品0\",\"status\":0}";
//        bargains = JSON.parseObject(bar,Bargains.class);
        System.out.println("全量更新开始");
        //实际是重新插入一条新纪录
        bargains1.setIndexId("3");
        bargains1.setStatus(4);
        esBargainService.put(bargains1);
        System.out.println("全量更新结束");

        System.out.println("更新根据查询开始");
        Bargains bargainsUpdate = new Bargains();
        bargainsUpdate.setIndexId("6");
        bargainsUpdate.setStatus(3);
        bargainsUpdate.setBargainedPrice(23);
        bargainsUpdate.setReceiverName("理想3");
        bargainsUpdate.setCreateTime(new Date());
        Long aLong = esBargainService.updateByIndexId(bargainsUpdate);
        System.out.println("更新数据条数:"+aLong);
        System.out.println("更新根据查询结束");

        System.out.println("更新开始");
        Bargains bar = new Bargains();
        bar.setIndexId("7");
        bar.setStatus(3);
        bar.setBargainedPrice(23);
        bar.setReceiverName("理想3");
        bar.setCreateTime(new Date());
        bar.setDel(true);
        Long update = esBargainService.update(bar);
        System.out.println("更新结果:"+update);
        System.out.println("更新结束");

        System.out.println("根据索引id查询开始");
        Bargains bargains2 = esBargainService.selectByIndexId("5");
        System.out.println(JSON.toJSONString(bargains2));
        System.out.println("根据索引id查询结束");

    }
}
