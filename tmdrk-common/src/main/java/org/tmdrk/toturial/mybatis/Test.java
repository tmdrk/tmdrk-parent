package org.tmdrk.toturial.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.tmdrk.toturial.mybatis.entity.AceUser;
import org.tmdrk.toturial.mybatis.entity.AceUserExample;
import org.tmdrk.toturial.mybatis.mapper.AceUserMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/6/10 2:44
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) throws IOException {

        String resource = "config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //创建Session实例
        SqlSession session = sqlSessionFactory.openSession();
        AceUserMapper mapper = session.getMapper(AceUserMapper.class);

//        AceUser aceUser = mapper.selectByPrimaryKey(1);
//        System.out.println(aceUser.getUserName()+"|"+aceUser.getPassword());

        AceUserExample aceUserExample = new AceUserExample();
        AceUserExample.Criteria criteria = aceUserExample.createCriteria();
        criteria.andIdIn(Arrays.asList(1,2));
        criteria.andUserNameEqualTo("zhoujie");
        List<AceUser> aceUsers = mapper.selectByExample(aceUserExample);
        aceUsers.forEach(aceUser1 -> {
            System.out.println(aceUser1.getUserName()+"|"+aceUser1.getPassword());
        });

        //statementType取值说明：
        //1、STATEMENT:直接操作sql，不进行预编译，获取数据：$—Statement
        //2、PREPARED:预处理，参数，进行预编译，获取数据：#—–PreparedStatement:默认
        //3、CALLABLE:执行存储过程————CallableStatement
        AceUser aceUser2 = mapper.selectByIdSTATEMENT(1);
        System.out.println(aceUser2.getUserName()+"|"+aceUser2.getPassword());
        AceUser aceUser3 = mapper.selectByIdPREPARED(1);
        System.out.println(aceUser3.getUserName()+"|"+aceUser3.getPassword());
    }
}
