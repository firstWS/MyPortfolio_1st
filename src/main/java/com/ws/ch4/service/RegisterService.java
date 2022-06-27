package com.ws.ch4.service;

import com.ws.ch4.domain.UserDto;

public interface RegisterService {
    int userInsert(UserDto userDto) throws Exception;
    int userModify(UserDto userDto) throws Exception;
    UserDto userSelect(String id) throws Exception;
    int userRemove(String id) throws Exception;
    int all_User_Delete() throws Exception;
    int all_User_Count() throws Exception;

    int id_Check(String id) throws Exception;
    int email_Check(String email) throws Exception;
}
