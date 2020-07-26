package org.tmdrk.toturial.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.LoggerFactory;
import org.tmdrk.toturial.mybatis.entity.AceUser;
import org.tmdrk.toturial.mybatis.mapper.AceUserMapper;
import org.tmdrk.toturial.mybatis.mapper.UserMapper;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @ClassName JavaTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/6/11 13:10
 * @Version 1.0
 **/
public class JavaTest {
    public static void main(String[] args) throws IOException {
        DataSource dataSource = getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true);    //开启驼峰映射,可以不配置xml resultMap
//        configuration.addMapper(AceUserMapper.class);
//        configuration.addMapper(UserMapper.class);
        configuration.addMappers("org.tmdrk.toturial.mybatis.mapper");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        //创建Session实例
        SqlSession session = sqlSessionFactory.openSession();
        AceUserMapper mapper = session.getMapper(AceUserMapper.class);
        AceUser aceUser = mapper.selectByPrimaryKey(1);
        System.out.println(aceUser.getUserName()+"|"+aceUser.getPassword());

        UserMapper userMapper = session.getMapper(UserMapper.class);
        AceUser aceUser1 = userMapper.selectByIdAndName(1,"zhoujie");
        System.out.println(aceUser1.getUserName()+"|"+aceUser1.getPassword());


    }

    private static DataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://192.168.1.11:3306/ace-admin?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&allowMultiQueries=true");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
        return druidDataSource;
    }
}
