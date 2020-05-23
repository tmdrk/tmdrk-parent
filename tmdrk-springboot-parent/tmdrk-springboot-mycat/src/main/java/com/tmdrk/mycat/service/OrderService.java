package com.tmdrk.mycat.service;

import com.tmdrk.mycat.annotationMapper.AnnotationOrderMapper;
import com.tmdrk.mycat.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/19 18:03
 * @Version 1.0
 **/
@Service
public class OrderService {
    @Autowired
    AnnotationOrderMapper annotationOrderMapper;

    public int insert(Order order){
        int insert = annotationOrderMapper.insert(order);
        return insert;
    }
}
