package com.ws.ch4.dao;

import com.ws.ch4.domain.*;

public interface UserDao {
    UserDto selectUser(String id) throws Exception;
    int deleteUser(String id) throws Exception;
    int insertUser(UserDto user) throws Exception;
    int updateUser(UserDto user) throws Exception;
    int count() throws Exception;
    int deleteAll() throws Exception;
    int id_check(String id) throws Exception;
    int email_check(String email) throws Exception;
}