package com.ws.ch4.dao;

import com.ws.ch4.domain.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class UserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void insertUserTest() throws Exception{
        userDao.deleteAll();
        for(int i = 0; i <= 20; i++){
            UserDto userDto = new UserDto("asdf"+i,"1234","kws","abc"+i+"@test.com");
            userDao.insertUser(userDto);
        }
    }

    @Test
    public void countTest() throws Exception{
        userDao.deleteAll();
        assertTrue(userDao.count() == 0);

        UserDto userDto = new UserDto("asdf", "1234", "kws", "abc@test.com");
        assertTrue(userDao.insertUser(userDto) == 1);
        assertTrue(userDao.count() == 1);

        userDto = new UserDto("asdf1", "1234", "kws", "abc@test.com");
        assertTrue(userDao.insertUser(userDto) == 1);
        assertTrue(userDao.count() == 2);
    }

    @Test
    public void deleteAllTest() throws Exception{
        userDao.deleteAll();
        assertTrue(userDao.count() == 0);

        UserDto userDto = new UserDto("asdf", "1234", "kws", "abc@test.com");
        assertTrue(userDao.insertUser(userDto) == 1);
        assertTrue(userDao.deleteAll() == 1);
        assertTrue(userDao.count() == 0);

        assertTrue(userDao.insertUser(userDto) == 1);
        userDto = new UserDto("asdf1", "1234", "kws", "abc@test.com");
        assertTrue(userDao.insertUser(userDto) == 1);
        assertTrue(userDao.count() == 2);
        assertTrue(userDao.deleteAll() == 2);
        assertTrue(userDao.count() == 0);
    }

    @Test
    public void deleteTest() throws Exception{
        userDao.deleteAll();
        assertTrue(userDao.count() == 0);

        UserDto userDto = new UserDto("asdf", "1234", "kws", "abc@test.com");
        assertTrue(userDao.insertUser(userDto) == 1);
        assertTrue(userDao.count() == 1);
        String testID = userDto.getId();
        assertTrue(userDao.deleteUser(testID) == 1);
        assertTrue(userDao.count() == 0);
    }

    @Test
    public void selDelTest() throws Exception{
        userDao.deleteAll();
        assertTrue(userDao.count() == 0);

        UserDto userDto = new UserDto("asdf", "1234", "kws", "abc@test.com");
        assertTrue(userDao.insertUser(userDto) == 1);

        String testID = userDto.getId();
        UserDto userDto2 = userDao.selectUser(testID);
        assertTrue(userDto.equals(userDto2));

        userDao.deleteUser(userDto2.getId());
        assertTrue(userDao.count() == 0);
    }

    @Test
    public void updateTest() throws Exception{
        userDao.deleteAll();
        assertTrue(userDao.count() == 0);

        UserDto userDto = new UserDto("asdf", "1234", "kws", "abc@test.com");
        assertTrue(userDao.insertUser(userDto) == 1);
        assertTrue(userDao.count() == 1);

        String testID = userDto.getId();
        System.out.println("ID = " + testID);

        userDto.setName("OhNo");
        userDto.setPwd("4321");
        assertTrue(userDao.updateUser(userDto) == 1);

        UserDto userDto2 = userDao.selectUser(testID);
        assertTrue(userDto.equals(userDto2));
    }

    @Test
    public void emailCheckTest() throws Exception{
        userDao.deleteAll();
        assertTrue(userDao.count() == 0);

        UserDto userDto = new UserDto("asdf", "1234", "kws", "abc@test.com");
        assertTrue(userDao.insertUser(userDto) == 1);
        assertTrue(userDao.count() == 1);

        String emailzz = userDto.getEmail();
        System.out.println("emailzz = " + emailzz);
        int eeee = userDao.email_check(emailzz);
        System.out.println("eeee = " + eeee);

        assertTrue(userDao.email_check(userDto.getEmail()) == 1);
        assertTrue(userDao.id_check(userDto.getId()) == 1);
    }

    @Test
    public void userIdEmailChk() throws Exception{

        String id = "user06";
        String id2 = "user07";
        String email = "abc@test.com";
        String email2 = "test@test.com";
        userDao.id_check(id);
        userDao.id_check(id2);
        userDao.id_check(id+id2);

        userDao.email_check(email);
        userDao.email_check(email2);
        userDao.email_check(email+email2);
    }
}