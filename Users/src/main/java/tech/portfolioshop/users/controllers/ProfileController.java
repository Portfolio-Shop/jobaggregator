package tech.portfolioshop.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.portfolioshop.users.models.UserResponse;

@RestController
@RequestMapping("/api/v1/user/profile")
public class ProfileController {
    @GetMapping
    public ResponseEntity<UserResponse> getProfile() {
        return null;
    }
    @PutMapping
    public ResponseEntity<UserResponse> updateProfile() {
        return null;
    }
    @DeleteMapping
    public ResponseEntity<UserResponse> deleteProfile() {
        return null;
    }
}
