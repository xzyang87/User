package com.xzyangjnzheng.demo.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {
    List<User> findByNameAndAge(String name, Integer age);
    List<User> findByCreatedAtBetween(Date after, Date before);
}
