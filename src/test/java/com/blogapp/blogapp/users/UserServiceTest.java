package com.blogapp.blogapp.users;

import com.blogapp.blogapp.users.dtos.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired UsersService usersService

    @Test
    void can_create_users() {

        var user = usersService.createUser(new CreateUserRequest(
                "Akhil",
                "ABC123",
                "kmakhil@gmail.com"
        ));

        Assertions.assertNotNull(user);
        Assertions.assertEquals("Akhil", user.getUsername());
    }
}
