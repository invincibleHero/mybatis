package com.lpf.ssm.mybatis.mapper;

import com.lpf.ssm.mybatis.po.User;

import java.util.List;

public interface UserMapper {

    //根据用户id 查询用户信息
    public User findUserById(int id) throws Exception;


    // 根据用户名，模糊搜索
    public List<User> findUserByName(String name) throws Exception;





}
