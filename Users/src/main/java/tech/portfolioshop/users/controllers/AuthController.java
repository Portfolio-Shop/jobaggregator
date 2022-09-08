package tech.portfolioshop.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.portfolioshop.users.models.UserResponse;

@RestController
@RequestMapping("/api/v1/user/auth")
public class AuthController {
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup() {
        return null;
    }
    @PostMapping("/signin")
    public ResponseEntity<UserResponse> signin() {
        return null;
    }
}
