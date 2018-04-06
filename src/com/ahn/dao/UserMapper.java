package com.ahn.dao;

import com.ahn.entity.User;

public interface UserMapper {
    //根据id查询数据
    public User getUserByid(Integer id);
}
