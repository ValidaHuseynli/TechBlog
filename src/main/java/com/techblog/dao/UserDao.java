package com.techblog.dao;

import com.techblog.model.User;

public interface UserDao {
    void register(User user);
    User getUser(String username, String password);

}
