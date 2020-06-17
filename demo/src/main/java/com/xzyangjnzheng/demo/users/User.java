package com.xzyangjnzheng.demo.users;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    @CreationTimestamp
    private Date createdAt;

    @CreationTimestamp
    private Date updatedAt;
}
