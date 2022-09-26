package tech.portfolioshop.users.controllers;

import io.jsonwebtoken.Jwts;
import org.jobaggregator.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.portfolioshop.users.services.ResumeService;
import tech.portfolioshop.users.shared.ResumeDto;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final Environment environment;
    @Autowired
    public ResumeController(ResumeService resumeService, Environment environment) {
        this.resumeService = resumeService;
        this.environment = environment;
    }
    @GetMapping
    public ResponseEntity<byte[]> getResume(@NotNull @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws NotFoundException {
        String userId;
        try{
            userId = Jwts.parser().setSigningKey(environment.getProperty("jwt.secret")).parseClaimsJws(token.replace("Bearer ", "")).getBody().getSubject();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ResumeDto resumeDto = resumeService.getResume(userId);
        byte[] resume = resumeDto.getResume();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF).body(resume);
    }
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadResume(@NotNull @RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam("resume") MultipartFile file) throws NotFoundException {
        String userId;
        try{
            userId = Jwts.parser().setSigningKey(environment.getProperty("jwt.secret")).parseClaimsJws(token.replace("Bearer ", "")).getBody().getSubject();
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
        return ResponseEntity.status(HttpStatus.CREATED).body("Resume uploaded");
    }
}
