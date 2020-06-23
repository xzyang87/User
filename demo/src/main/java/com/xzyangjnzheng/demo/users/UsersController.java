package com.xzyangjnzheng.demo.users;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@RibbonClient(name = "email", configuration = EmailConfiguration.class)
public class UsersController {

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    private final UsersService usersService;

    @Value("${circuit.retryTime: default value}")
    private String circuitRetryTimes;

    @GetMapping("getConfig")
    public String test() {
        // just a demo endpoint, we can delete it later.
        // Try it by visit: http://localhost:8080/users/getConfig,
        // and you will get the value configed here: https://github.com/xzyang87/configManager/blob/master/user.yml
        // We can use it in the circuit breaker implementation later.
        return "This is the config from config server about circuit RetryTimes: " + circuitRetryTimes;
    }

    @GetMapping("user-info")
    public String getUserInfo(@RequestParam(defaultValue = "") String userId) {
        String url = String.format("http://email/emails/%s", userId);
        String email = restTemplate.getForObject(url, String.class);
        return "userinfo: " + email;
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return usersService.getUsers();
    }

    @GetMapping("/page")
    public List<User> getUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "2") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        return usersService.getUsers(pageNo, pageSize, sortBy);
    }

    @GetMapping("/name-age")
    public List<User> getUsers(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer age) {
        return usersService.getUsersByNameAndAge(name, age);
    }

    @GetMapping("/between")
    public List<User> getUsers(
            @RequestParam(defaultValue = "01/01/1900") String after,
            @RequestParam(defaultValue = "30/12/2020") String before) throws ParseException {
        Date createdAfter = new SimpleDateFormat("dd/MM/yyyy").parse(after);
        Date createdBefore = new SimpleDateFormat("dd/MM/yyyy").parse(before);
        return usersService.getUsersBetween(createdAfter, createdBefore);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        usersService.createUser(user);
    }
}
