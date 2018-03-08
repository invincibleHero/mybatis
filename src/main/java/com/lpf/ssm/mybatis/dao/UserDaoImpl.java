package com.lpf.ssm.mybatis.dao;

import com.lpf.ssm.mybatis.po.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.jws.soap.SOAPBinding;

public class UserDaoImpl implements UserDao {

    // 需要向 dao 实现类中注入 SqlSessionFactory
    // 这里通过 构造方法注入
    private SqlSessionFactory sqlSessionFactory;

    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }


    public User findUserById(int id) throws Exception {

        // SqlSession 在方法内部使用，防止出现线程不安全的问题
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = sqlSession.selectOne("test.findUserById",id);

        // 释放资源
        sqlSession.close();
        return user;

    }


    public void insertUser(User user) throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 执行插入操作
        sqlSession.insert("test.insertUser" , user);

        // 提交事务
        sqlSession.commit();

        // 释放资源
        sqlSession.close();
    }

    public void deleteUser(int id) throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("test.deleteUser", id);

        sqlSession.commit();

        sqlSession.close();
    }
}
