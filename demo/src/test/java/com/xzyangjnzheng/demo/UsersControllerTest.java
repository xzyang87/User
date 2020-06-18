package com.xzyangjnzheng.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xzyangjnzheng.demo.users.User;
import com.xzyangjnzheng.demo.users.UsersRepository;
import com.xzyangjnzheng.demo.users.UsersService;
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

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void ShouldGetExpectedUsers() throws Exception {
        List<User> mockUsers = Arrays.asList(
                mockUser(true,true),
                mockUser(true,true));

        when(usersService.getUsers()).thenReturn(mockUsers);
        String expected = convertUsersListToJsonString(mockUsers);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(result -> result
                        .getResponse()
                        .getContentAsByteArray()
                        .equals(convertUsersListToJsonString(mockUsers)));
    }

    @Test
    void ShouldCreatedExpectedUser() throws Exception {
        User newUser = mockUser(false, false);
        String postBody = String.format("{\"name\":\"%s\",\"age\":%s}", newUser.getName(), newUser.getAge());

        mvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(postBody))
                .andExpect(status().isOk()
                );

        verify(usersService, times(1)).createUser(newUser);
    }

    private User mockUser(boolean shouldSetRandomId, boolean shouldSetTimeStamp) {
        User user = new User();

        if (shouldSetRandomId) {
            user.setId((long) 1e8 + new Random().nextInt((int) 9e8));
        }

        user.setName(getRandomName());
        user.setAge(new Random().nextInt(100) + 1);

        if (shouldSetTimeStamp) {
            user.setCreatedAt(new Date(System.currentTimeMillis()));
            user.setUpdatedAt(new Date(System.currentTimeMillis() + 10));
        }

        return user;
    }

    private String getRandomName() {
        int nameLength = (new Random()).ints(3, 10).findFirst().getAsInt();
        return RandomString.make(nameLength);
    }

    private String convertUsersListToJsonString(List<User> users) {
        return users
                .stream()
                .map(this::convertUserToJsonString)
                .toString();
    }

    private String convertUserToJsonString(User user) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
