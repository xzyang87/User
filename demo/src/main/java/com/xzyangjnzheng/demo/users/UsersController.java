package com.xzyangjnzheng.demo.users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return usersService.getUsers();
    }

    @GetMapping
    public List<User> getAllUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "2") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        return usersService.getUsers(pageNo, pageSize, sortBy);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        usersService.createUser(user);
    }
}
