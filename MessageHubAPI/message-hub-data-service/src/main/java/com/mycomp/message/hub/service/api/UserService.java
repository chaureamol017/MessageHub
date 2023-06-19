package com.mycomp.message.hub.service.api;

import com.mycomp.message.hub.persistence.entity.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public User updateUser(User user);

    public List<User> getAllUsers();

    public User getUser(String id);
    void deleteUser(String id);
}
