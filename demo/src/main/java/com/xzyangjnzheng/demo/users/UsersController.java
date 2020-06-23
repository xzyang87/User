package com.xzyangjnzheng.demo.users;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@RibbonClient(name = "email", configuration = RibbonConfiguration.class)
public class UsersController {

    @Autowired
    EmailInfoServiceClient emailInfoServiceClient;

    private final RestTemplate restTemplate;

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

//    @GetMapping("test-gateway")
//    public String getEmailByGateway(@RequestParam(defaultValue = "") String userId) {
//        String emailUrl = String.format("http://gateway-server/emails/%s", userId);
//        String result = restTemplate.getForObject(emailUrl, String.class);
//        return "result: " + result;
//    }

    @GetMapping("test-gateway")
    public String getEmailByGateway() {
        return "result ok";
    }

    @GetMapping("user-info")
    public String getUserInfo(@RequestParam(defaultValue = "") String userId) {
        String email = emailInfoServiceClient.getEmailInfo(userId);
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
