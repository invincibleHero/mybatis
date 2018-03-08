package com.lpf.ssm.mybatis.test;

import com.lpf.ssm.mybatis.mapper.UserMapper;
import com.lpf.ssm.mybatis.po.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/*
采用 mapper 规范来进行代码开发的测试
 */
public class MapperTest {

    private SqlSessionFactory sqlSessionFactory;

    /*public MapperTest(SqlSessionFactory sqlSessionFactory){

        this.sqlSessionFactory = sqlSessionFactory;
    }*/

    @Before
    public void setUp() throws Exception{
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testFindUserById() throws Exception {


        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建 UserMapper 对象， mybatis 自动生成 mapper 代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 调用UserMapper 方法
        User user = userMapper.findUserById(10);
        System.out.println("使用mapper 规范开发查询出来的结果是：" + user.toString());

    }

    @Test
    public void findUserByName() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建 UserMapper 对象， mybatis 自动生成 mapper 代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 调用UserMapper 方法
        List<User> lists;
        lists = userMapper.findUserByName("小明");

        for (int i = 0; i < lists.size(); i++) {
            System.out.println("使用mapper 规范开发查询出来的结果是：" + lists.get(i).toString());
        }
    }



    //  有问题？？？？？？  java.lang.Exception: Method findUserList should have no parameters
/*    @Test
    public void findUserList(UserQueryVo userQueryVo) throws Exception{
        List<UserCustom> userCustomList;
        SqlSession sqlSession =  sqlSessionFactory.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 创建包装对象，设置查询条件
        UserQueryVo userQueryVo1  = new UserQueryVo();
        UserCustom userCustom = new UserCustom();
        userCustom.setSex("1");
        userCustom.setUsername("张三丰");
        userQueryVo.setUserCustom(userCustom);


        *//*List<UserCustom> list = userMapper.findUserList(userQueryVo);

        for (int i = 0; i < list.size(); i++) {
            System.out.println("查询出来的结果是：" + list.get(i).toString());
        }*//*

        sqlSession.commit();
        sqlSession.close();
    }*/
}
