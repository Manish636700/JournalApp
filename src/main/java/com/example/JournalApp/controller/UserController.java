package com.example.JournalApp.controller;


import com.example.JournalApp.Repository.UserRepository;
import com.example.JournalApp.Service.UserService;
import com.example.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @DeleteMapping("/DeleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/GetById/{id}")
    public User getUserById(@PathVariable ObjectId id){
        return userService.findById(id).orElse(null);

    }

    @PutMapping("/Update")
    public ResponseEntity<?> updateUser( @RequestBody User user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
       User user1 = userService.findByUsername(userName);
           user1.setUserName(user.getUserName());
           user1.setPassword(user.getPassword());
           userService.save(user1);
       return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/DeleteUser")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userRepository.deleteByuserName(userName);
        return ResponseEntity.noContent().build();
    }
}
