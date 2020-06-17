package com.xzyangjnzheng.demo.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    public List<User> getUsers() {
        return usersRepository.findAll();
    }

    public void createUser(User user) {
        usersRepository.save(user);
    }
}
