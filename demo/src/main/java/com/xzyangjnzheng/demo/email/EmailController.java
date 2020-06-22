package com.xzyangjnzheng.demo.email;

import com.xzyangjnzheng.demo.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final UsersService usersService;

    @GetMapping
    public String getUserEmail(@RequestParam(defaultValue = "") Long userId) {
        return usersService.getUserById(userId) != null
                ? String.format("%s@rest.local", userId)
                : "";
    }
}
