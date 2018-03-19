package com.vincent.dao;

import com.vincent.entity.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao dao;

    @Test
    @Rollback
    public void add() {
        TUser user = new TUser();
        user.setUserName("vicent.long");
        user.setPassword("123456");
        user.setPhone("130202020");
        dao.add(user);
    }

    @Test
    public void getById() {
        TUser user1 = dao.getById(1000);
        System.out.println("user1" + user1);
        TUser user2 = dao.getById(1000);
        System.out.println("user2" + user2);
    }
}