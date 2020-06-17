package com.xzyangjnzheng.demo;

import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xzyangjnzheng.demo.users.User;
import com.xzyangjnzheng.demo.users.UsersController;
import com.xzyangjnzheng.demo.users.UsersService;
import org.aspectj.lang.annotation.Before;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
        Date user1CreatedAt = new Date(System.currentTimeMillis());
        Date user1UpdatedAt = new Date(System.currentTimeMillis() + 10);
        Long user1Id = (long) 1;
        int user1Age = 5;
        String user1Name = "mike";

        User user1 = new User();
        user1.setId(user1Id);
        user1.setName(user1Name);
        user1.setAge(user1Age);
        user1.setCreatedAt(user1CreatedAt);
        user1.setUpdatedAt(user1UpdatedAt);

        List<User> usersList = Arrays.asList(user1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        when(usersService.getUsers()).thenReturn(usersList);

        MvcResult result = mvc.perform(get("/users")).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String contentString = response.getContentAsString();
        List<String> mappedArray = usersList.stream().map(user -> {
            try {
                return mapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return "";
        }).collect(Collectors.toList());
        String expected = mappedArray.toString();
        Assert.isTrue(contentString.equals(expected));
//                .andExpect(content().string((new JSONArray(usersList)).toString()));
    }


//    @Test
//    public void getUsers() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(equalTo("123")));
//    }
}
