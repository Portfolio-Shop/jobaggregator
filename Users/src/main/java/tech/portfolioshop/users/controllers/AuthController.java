package tech.portfolioshop.users.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.portfolioshop.users.models.http.request.SignInRequest;
import tech.portfolioshop.users.models.http.request.SignUpRequest;
import tech.portfolioshop.users.models.http.response.UserResponse;
import tech.portfolioshop.users.models.kafka.UserCreated;
import tech.portfolioshop.users.services.AuthService;
import tech.portfolioshop.users.services.KafkaProducerService;
import tech.portfolioshop.users.shared.UserDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user/auth")
public class AuthController {
    private final Environment environment;
    private final AuthService authService;
    private final KafkaProducerService<UserCreated> kafkaUserCreated;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(AuthService authService, KafkaProducerService<UserCreated> kafkaUserCreated, ModelMapper modelMapper, Environment environment) {
        this.authService = authService;
        this.kafkaUserCreated = kafkaUserCreated;
        this.modelMapper = modelMapper;
        this.environment = environment;
    }
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignUpRequest userDetails) {
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = authService.signup(userDto);
        UserCreated user = new UserCreated(
                createdUser.getName(),
                createdUser.getEmail(),
                createdUser.getUserId(),
                createdUser.getPhone()
        );
        kafkaUserCreated.send(user);
        UserResponse userResponse = modelMapper.map(createdUser, UserResponse.class);
        String token = "Bearer " + Jwts.builder()
                .setSubject(createdUser.getUserId())
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.AUTHORIZATION, token).body(userResponse);
    }
    @PostMapping("/signin")
    public ResponseEntity<UserResponse> signin(@Valid @RequestBody SignInRequest userDetails) {
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto user = authService.signin(userDto);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        String token = "Bearer " + Jwts.builder()
                .setSubject(user.getUserId())
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("jwt.secret"))
                .compact();
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.AUTHORIZATION, token).body(userResponse);
    }
}
