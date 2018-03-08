package com.lpf.ssm.mybatis.dao;

import com.lpf.ssm.mybatis.po.User;

public interface UserDao {

    // 根据用户id 查询用户信息
    public User findUserById(int id) throws Exception;

    // 添加用户信息
    public void insertUser(User user) throws Exception ;

    // 删除用户信息
    public void  deleteUser(int id) throws Exception;


}
