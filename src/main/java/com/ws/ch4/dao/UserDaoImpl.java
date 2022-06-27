package com.ws.ch4.dao;

import com.ws.ch4.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SqlSession session;
    private static String namespace = "com.ws.ch4.dao.userMapper.";

    @Override
    public int deleteUser(String id) throws Exception {
        return session.delete(namespace + "deleteUser", id);
    }

    @Override
    public UserDto selectUser(String id) throws Exception {
        return session.selectOne(namespace + "selectUser", id);
    }

    @Override
    public int insertUser(UserDto userDto) throws Exception {
        return session.insert(namespace + "insertUser", userDto);
    }

    @Override
    public int updateUser(UserDto userDto) throws Exception {
       return session.update(namespace + "updateUser", userDto);
    }

    @Override
    public int count() throws Exception {
       return session.selectOne(namespace + "count");
    }

    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace + "deleteAll");
    }

    @Override
    public int id_check(String id) throws Exception{
        return session.selectOne(namespace+"id_check", id);
    }
    @Override
    public int email_check(String email) throws Exception{
        return session.selectOne(namespace + "email_check", email);
    }
}