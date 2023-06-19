package com.mycomp.message.hub.web.controller;

import com.mycomp.message.hub.service.api.UserService;
import com.mycomp.message.hub.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("user")
public class UserController {
    @Autowired private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        User tempuser = userService.saveUser(user);
        if (tempuser != null) {
            return new ResponseEntity<>(tempuser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(tempuser, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        User tempuser = userService.updateUser(user);
        if (tempuser != null) {
            return new ResponseEntity<>(tempuser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(tempuser, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllProduct() {
        List<User> users = userService.getAllUsers();
        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/byid")
    public ResponseEntity<User> getUser(@RequestParam(name = "id") String id) {
        User user = userService.getUser(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam(name = "id") String id) {
        userService.deleteUser(id);
    }
}
