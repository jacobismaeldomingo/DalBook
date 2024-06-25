package com.example.facebook_integration.service.Implementation;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.repository.UserRepository;
import com.example.facebook_integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository UserRepository;

    @Override
    public String createUser(User user) {
        UserRepository.save(user);
        return "Customer Successfully Created";
    }

//    @Override
//    public List<User> fetchAllUser() {
//        return null;
//    }
//
//    @Override
//    public User findByFirstName(String firstName) {
//        return null;
//    }
//
//    @Override
//    public String updateUser(User user) {
//        return null;
//    }
//
//    @Override
//    public User findUserById(int id) {
//        return null;
//    }
//
//    @Override
//    public String deleteUserById(int id) {
//        return null;
//    }
}