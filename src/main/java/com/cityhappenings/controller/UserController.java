package com.cityhappenings.controller;

import com.cityhappenings.model.User;
import com.cityhappenings.service.UserService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        //1. Save user to DB
        User savedUser = userService.createUser(user);

        //2. Send Message to Kafka
        String topic = "users-topic"; // topic-name
        String message = "New user created: " + savedUser.getEmail();
        kafkaTemplate.send(topic, message);

        return savedUser;

    }

}
