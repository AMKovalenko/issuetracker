package com.axmor.dao;

import com.axmor.entity.User;


public interface UserDao {

    User getUserByUsername(String username) throws Exception ;

    Integer createUser(User user) throws Exception;
}
