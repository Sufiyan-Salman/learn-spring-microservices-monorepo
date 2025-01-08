package com.learn.microservices.user_service.controllers;

import com.learn.microservices.user_service.Entities.User;
import com.learn.microservices.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private String port;

    /**
     * <pre>
     *     This is added to run multiple instances of user-service with different ports and to check if api-gateway correctly balances the load or not, and it does.
     *     To run multiple instances, create new run configuration and write this in program arguments
     *     --server.port= port which you would like to run on
     * </pre>
     *
     */
    @GetMapping("/instance")
    public String getInstance() {
        return "User Service is running on port: " + port;
    }
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}


