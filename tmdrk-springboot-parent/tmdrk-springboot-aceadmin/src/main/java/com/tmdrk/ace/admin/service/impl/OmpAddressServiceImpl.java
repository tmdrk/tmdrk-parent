package com.tmdrk.ace.admin.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.tmdrk.ace.admin.entity.OmpAddress;
import com.tmdrk.ace.admin.entity.OmpAddressExample;
import com.tmdrk.ace.admin.export.mapping.JdAddressMapping;
import com.tmdrk.ace.admin.export.mapping.JdAddressMappingListener;
import com.tmdrk.ace.admin.export.omp.OmpAddressData;
import com.tmdrk.ace.admin.export.omp.OmpAddressDataListener;
import com.tmdrk.ace.admin.export.omp.OmpAddressExport;
import com.tmdrk.ace.admin.service.OmpAddressService;
import com.tmdrk.ace.admin.mapper.OmpAddressMapper;
import jodd.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

/**
 * OmpAddressServiceImpl
 *
 * @author Jie.Zhou
 * @date 2021/1/19 14:38
 */
@Slf4j
@Service("ompAddressService")
public class OmpAddressServiceImpl implements OmpAddressService {
    @Autowired
    private OmpAddressMapper ompAddressMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void parseAddress() {
        String fileName = "D:\\zj\\data\\" + File.separator + "message1.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
        EasyExcel.read(fileName, OmpAddressData.class, new OmpAddressDataListener(ompAddressMapper)).sheet().doRead();

    }

    @Override
    public void generateAddress(MultipartFile file){
        try {
            EasyExcel.read(file.getInputStream(), JdAddressMapping.class, new JdAddressMappingListener(redisTemplate)).sheet().doRead();
        } catch (IOException e) {
            log.error("生成映射异常",e);
        }
    }

    @Override
    public void download() {
        String fileName =  "D:\\zj\\data\\" + File.separator + + System.currentTimeMillis() + ".xlsx";
        // 根据用户传入字段 假设我们要忽略 date
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("tenantId");
        excludeColumnFiledNames.add("platform");
        OmpAddressExample example1 = new OmpAddressExample();
        OmpAddressExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andLevelEqualTo(1);
        List<OmpAddress> list1 = ompAddressMapper.selectByExample(example1);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, OmpAddressExport.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet("模板")
                .doWrite(list1);

        // 多sheet
        fileName =  "D:\\zj\\data\\" + File.separator + "sheet-" + System.currentTimeMillis() + ".xlsx";
        // 方法3 如果写到不同的sheet 不同的对象
        ExcelWriter excelWriter = null;
        try {
            // 这里 指定文件
            excelWriter = EasyExcel.write(fileName).build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (int i = 0; i < 3; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(OmpAddressExport.class).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                OmpAddressExample example = new OmpAddressExample();
                OmpAddressExample.Criteria criteria = example.createCriteria();
                criteria.andLevelEqualTo(i+1);
                List<OmpAddress> list = ompAddressMapper.selectByExample(example);
                excelWriter.write(list, writeSheet);
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Override
    public void selectCursor() {
        Cursor<OmpAddress> cursors = sqlSessionTemplate.getSqlSessionFactory().openSession()
                .selectCursor("com.tmdrk.ace.admin.mapper.OmpAddressMapper.allAddressList", new HashMap<>().put("parentCode","0"));

        try (Cursor<OmpAddress> cursor = sqlSessionTemplate.getSqlSessionFactory().openSession()
                .selectCursor("com.tmdrk.ace.admin.mapper.OmpAddressMapper.allAddressList", new HashMap<>())) {
            Semaphore s = new Semaphore(5);
            for (OmpAddress c : cursor) {
                s.acquire();
                CompletableFuture<Void> f = CompletableFuture.runAsync(new UpdateAwardRunnable(c));
                f.whenComplete((res, err) -> {
                    // res：返回值 err：异常信息
                    log.info("更新奖励");
                    s.release();
                });
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int updateById(OmpAddress ompAddress) {
       int result = ompAddressMapper.updateByPrimaryKeySelective(ompAddress);
       int i = 1/0;
       return result;
    }

    class UpdateAwardRunnable implements Runnable {
        private OmpAddress c;

        public UpdateAwardRunnable(OmpAddress c) {
            this.c = c;
        }

        @Override
        public void run() {
            log.info("name={} code={}",c.getName(),c.getCode());
        }
    }

}
