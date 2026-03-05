package com.example.demo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserRepository userRepo;

    @GetMapping("/")
    public String home() {
        return "TTproject API is running. Try /demo, /users, or POST /register";
    }

    @GetMapping("/demo")
    public String demo(){

        return "i am good how are you";
    }
    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return this.userRepo.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is required");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
        }
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is required");
        }

        Users u = this.userRepo.findByEmail(user.getEmail().trim());
        if (u != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        try {
            user.setEmail(user.getEmail().trim());
            user.setUsername(user.getUsername().trim());
            this.userRepo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }
}

