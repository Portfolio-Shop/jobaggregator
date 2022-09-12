package tech.portfolioshop.users.controllers;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.portfolioshop.users.services.implemetation.ResumeService;
import tech.portfolioshop.users.shared.ResumeDto;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user/resume")
public class ResumeController {

    private final ResumeService resumeService;
    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    @GetMapping
    public ResponseEntity<byte[]> getResume(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId;
        try{
            userId = Jwts.parser().setSigningKey("secret").parseClaimsJws(token.replace("Bearer ", "")).getBody().getSubject();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ResumeDto resumeDto = resumeService.getResume(userId);
        byte[] resume = resumeDto.getResume();
        return ResponseEntity.status(HttpStatus.OK).body(resume);
    }
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadResume(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam("resume") MultipartFile file) {
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId;
        try{
            userId = Jwts.parser().setSigningKey("secret").parseClaimsJws(token.replace("Bearer ", "")).getBody().getSubject();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(!Objects.equals(file.getContentType(), MediaType.APPLICATION_PDF_VALUE)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File must be a PDF");
        }
        byte[] resume;
        try {
            resume = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setResume(resume);
        resumeDto.setUserId(userId);
        resumeService.uploadResume(resumeDto);
        return ResponseEntity.status(HttpStatus.OK).body("Resume uploaded");
    }
}
