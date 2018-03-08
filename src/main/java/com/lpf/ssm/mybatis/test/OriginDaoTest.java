package com.lpf.ssm.mybatis.test;

import com.lpf.ssm.mybatis.dao.UserDao;
import com.lpf.ssm.mybatis.dao.UserDaoImpl;
import com.lpf.ssm.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;


import java.io.InputStream;

public class OriginDaoTest {

    private static SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {

        //mybatis 配置文件
        String resources ="SqlMapConfig.xml";
        //得到配置文件的输入流
        InputStream inputStream = Resources.getResourceAsStream(resources);
        // 创建会话工厂，传入mybatis 的配置文件信息
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }


    @Test
    public void testFindUserById() throws Exception{
        // 创建UserDao的对象
        UserDao  userDao = new UserDaoImpl(sqlSessionFactory);


        User user = userDao.findUserById(10);

        System.out.println("查询出来的用户信息是：" + user.toString());

    }


}
