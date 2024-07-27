package com.example.facebook_integration.IntegrationTests;

import com.example.facebook_integration.model.User;
import com.example.facebook_integration.repository.UserRepository;
import jakarta.transaction.Transactional;
<<<<<<< HEAD
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
=======
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> d82eabc03def686a7fc69a7ace7eedd784b2d39f
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
<<<<<<< HEAD
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
=======
>>>>>>> d82eabc03def686a7fc69a7ace7eedd784b2d39f
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

<<<<<<< HEAD
    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

=======
>>>>>>> d82eabc03def686a7fc69a7ace7eedd784b2d39f
    @Test
    public void testFindById() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@dal.ca");
        user.setSecurityAnswer("Paris");
        user.setDateOfBirth("01-01-2001");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("John", foundUser.get().getFirstName());
    }
<<<<<<< HEAD

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setFirstName("Jamie");
        user.setLastName("Doe");
        user.setEmail("jamie.doe@dal.ca");
        user.setSecurityAnswer("France");
        user.setDateOfBirth("02-02-2002");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findUserByEmail(user.getEmail());
        assertTrue(foundUser.isPresent());
        assertEquals("Jamie", foundUser.get().getFirstName());
    }
=======
>>>>>>> d82eabc03def686a7fc69a7ace7eedd784b2d39f
}
