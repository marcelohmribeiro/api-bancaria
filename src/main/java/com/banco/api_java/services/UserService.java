package com.banco.api_java.services;

import com.banco.api_java.models.UserModel;
import com.banco.api_java.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }
}
