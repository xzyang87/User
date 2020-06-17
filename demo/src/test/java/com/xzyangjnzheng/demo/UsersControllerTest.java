package com.xzyangjnzheng.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xzyangjnzheng.demo.users.User;
import com.xzyangjnzheng.demo.users.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {
    @MockBean
    private UsersService usersService;

    @Autowired
    private MockMvc mvc;

    @Test
    void ShouldGetExpectedUsers() throws Exception {
        List<User> mockUsers = Arrays.asList(mockUser(), mockUser());

        when(usersService.getUsers()).thenReturn(mockUsers);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(result -> result
                        .getResponse()
                        .getContentAsByteArray()
                        .equals(convertUsersListToJsonString(mockUsers)));
    }

    private User mockUser() {
        User user = new User();
        user.setId(new Random().nextLong());
        user.setName(((Long)(new Random().nextLong())).toString());
        user.setAge(new Random().nextInt());
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setUpdatedAt(new Date(System.currentTimeMillis() + 10));

        return user;
    }

    private String convertUsersListToJsonString(List<User> users) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return users.stream().map(user -> {
            try {
                return mapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return "";
        }).toString();
    }
}
