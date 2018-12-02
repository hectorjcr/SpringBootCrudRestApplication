package com.hc.springbootcrudrest.controller;

import com.hc.springbootcrudrest.exception.ResourceNotFoundException;
import com.hc.springbootcrudrest.model.User;
import com.hc.springbootcrudrest.repository.UserRepository;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on :: \"+ userId"));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping(value = "/users")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                           @Valid @RequestBody User userDetails) throws ResourceNotFoundException{

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No encontrado"));
        user.setEmailId(userDetails.getEmailId());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUpdatedAt(new Date());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(value = "/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException{
        User user = userRepository.findById(userId).orElseThrow( () -> new ResourceNotFoundException("No encontrado"));
        userRepository.delete(user);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return response;
    }

}
