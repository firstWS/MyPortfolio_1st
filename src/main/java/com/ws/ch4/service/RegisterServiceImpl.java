package com.ws.ch4.service;

import com.ws.ch4.dao.UserDao;
import com.ws.ch4.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    UserDao userDao;

    @Override
    public int userInsert(UserDto userDto) throws Exception{
        return userDao.insertUser(userDto);
    }

    @Override
    public int userModify(UserDto userDto) throws Exception{
        return userDao.updateUser(userDto);
    }

    @Override
    public UserDto userSelect(String id) throws Exception{
        return userDao.selectUser(id);
    }

    @Override
    public int userRemove(String id) throws Exception{
        return userDao.deleteUser(id);
    }

    @Override
    public int all_User_Delete() throws Exception{
        return userDao.deleteAll();
    }

    @Override
    public int all_User_Count() throws Exception{
        return userDao.count();
    }
    @Override
    public int id_Check(String id) throws Exception{
        return userDao.id_check(id);
    }

    @Override
    public int email_Check(String email) throws Exception{
        return userDao.email_check(email);
    }
}
