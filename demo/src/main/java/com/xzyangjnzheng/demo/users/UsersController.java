package com.xzyangjnzheng.demo.users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

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
