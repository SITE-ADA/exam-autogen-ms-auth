package az.edu.ada.msauth.service.impl;

import az.edu.ada.msauth.model.entities.User;
import az.edu.ada.msauth.repository.UserRepository;
import az.edu.ada.msauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
