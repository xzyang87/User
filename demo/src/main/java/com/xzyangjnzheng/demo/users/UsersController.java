package com.xzyangjnzheng.demo.users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    public List<User> getUsers() {
        return usersService.getUsers();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        usersService.createUser(user);
    }
}
