package com.tmdrk.ace.admin;

import com.tmdrk.ace.admin.entity.MarketingGameDetail;
import com.tmdrk.ace.admin.service.MarketingGameDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TmdrkSpringbootAceadminApplicationTests {
	ExecutorService executorService = Executors.newFixedThreadPool(20);
	@Resource
	private MarketingGameDetailService marketingGameDetailService;

	@Test
	public void contextLoads() {
		System.out.println("==================================");
	}

	@Test
	public void updateBatch() {
		Random random = new Random();
		for(int i=0;i<100;i++){
            int ind = random.nextInt(4);
            System.out.println(ind);
            MarketingGameDetail marketingGameDetail = new MarketingGameDetail();
			marketingGameDetail.setGameId(ind+1L);
            executorService.execute(()->marketingGameDetailService.updateBatch(marketingGameDetail));
		}
		System.out.println("==================================");
	}
}
