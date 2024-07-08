package com.example.JournalApp.controller;


import com.example.JournalApp.Service.UserService;
import com.example.JournalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/CreateUser")
    public void CreateUser(@RequestBody User user) {
        userService.save(user);
    }

}
