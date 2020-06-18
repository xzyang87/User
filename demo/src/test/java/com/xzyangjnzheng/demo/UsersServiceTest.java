package com.xzyangjnzheng.demo;

import com.xzyangjnzheng.demo.users.User;
import com.xzyangjnzheng.demo.users.UsersRepository;
import com.xzyangjnzheng.demo.users.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersServiceTest {
    @MockBean
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;

    @Test
    public void ShouldGetExpectedUsers() {
        usersService.getUsers();
        verify(usersRepository, times(1)).findAll();
    }

    @Test
    public void ShouldCreateUserAsExpected() {
        User user = new User();
        usersService.createUser(user);

        verify(usersRepository, times(1)).save(user);
    }
}
