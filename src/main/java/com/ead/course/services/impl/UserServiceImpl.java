package com.ead.course.services.impl;

import com.ead.course.repositories.UserRepository;
import com.ead.course.services.UserService;

public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
