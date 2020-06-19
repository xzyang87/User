package com.xzyangjnzheng.demo.users;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    public List<User> getUsers() {
        return usersRepository.findAll();
    }

    public List<User> getUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> pagedResult = usersRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        }

        return new ArrayList<User>();
    }

    public List<User> getUsersByNameAndAge(String name, Integer age) {
        return usersRepository.findByNameAndAge(name, age);
    }

    public List<User> getUsersBetween(Date after, Date before) {
        return usersRepository.findByCreatedAtBetween(after, before);
    }

    public void createUser(User user) {
        usersRepository.save(user);
    }
}
