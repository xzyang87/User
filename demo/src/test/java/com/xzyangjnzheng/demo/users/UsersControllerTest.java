package com.xzyangjnzheng.demo.users;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {
    @MockBean
    private UsersService usersService;

    @Autowired
    private MockMvc mvc;
/*
    @Test
    void ShouldGetExpectedUsers() throws Exception {
        mvc.perform(get("/users/all"))
                .andExpect(status().isOk());

        verify(usersService, times(1)).getUsers();
    }

    @Test
    void ShouldCreatedExpectedUser() throws Exception {
        User newUser = mockUser();
        String postBody = String.format("{\"name\":\"%s\",\"age\":%s}", newUser.getName(), newUser.getAge());

        mvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(postBody))
                .andExpect(status().isOk()
                );

        verify(usersService, times(1)).createUser(newUser);
    }

    private User mockUser() {
        User user = new User();

        user.setName(getRandomName());
        user.setAge(new Random().nextInt(100) + 1);

        return user;
    }

    private String getRandomName() {
        int nameLength = (new Random()).ints(3, 10).findFirst().getAsInt();
        return RandomString.make(nameLength);
    }
 */
}
