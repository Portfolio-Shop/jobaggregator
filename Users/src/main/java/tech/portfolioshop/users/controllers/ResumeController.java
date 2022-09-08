package tech.portfolioshop.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/resume")
public class ResumeController {
    @GetMapping
    public ResponseEntity<Byte[]> getResume() {
        return null;
    }
    @PostMapping
    public void uploadResume() {
    }
}
