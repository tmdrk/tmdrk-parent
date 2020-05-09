package org.tmdrk.toturial.spring.service.circle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserBService
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/15 22:32
 * @Version 1.0
 **/
@Service
public class UserBService {
    @Autowired
    UserAService userAService;
}
