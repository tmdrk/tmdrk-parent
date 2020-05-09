package com.tmdrk.webboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TmdrkSpringbootWebbootApplicationTests {
	@Autowired
	DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Test
	public void contextLoads() throws SQLException {
		System.out.println("=================== dataSource:"+dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();

        //自己创建mysql数据库及表
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT * FROM ACE_USER");
        System.out.println("=================== "+maps.toString());

    }

}
