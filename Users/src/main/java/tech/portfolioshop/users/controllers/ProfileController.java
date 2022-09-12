package tech.portfolioshop.users.controllers;

import io.jsonwebtoken.Jwts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.portfolioshop.users.models.http.response.UserResponse;
import tech.portfolioshop.users.models.http.request.UserUpdateRequest;
import tech.portfolioshop.users.models.kafka.UserDeleted;
import tech.portfolioshop.users.models.kafka.UserUpdated;
import tech.portfolioshop.users.services.KafkaProducerService;
import tech.portfolioshop.users.services.ProfileService;
import tech.portfolioshop.users.shared.UserDto;

@RestController
@RequestMapping("/api/v1/user/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final ModelMapper modelMapper;

    private final KafkaProducerService<UserUpdated> kafkaUserUpdated;
    private final KafkaProducerService<UserDeleted> kafkaUserDeleted;

    @Autowired
    public ProfileController(ProfileService profileService, ModelMapper modelMapper, KafkaProducerService<UserUpdated> kafkaUserUpdated, KafkaProducerService<UserDeleted> kafkaUserDeleted) {
        this.profileService = profileService;
        this.modelMapper = modelMapper;
        this.kafkaUserUpdated = kafkaUserUpdated;
        this.kafkaUserDeleted = kafkaUserDeleted;
    }

    @GetMapping
    public ResponseEntity<UserResponse> getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if(token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId;
        try{
            userId = Jwts.parser().setSigningKey("secret").parseClaimsJws(token.replace("Bearer ", "")).getBody().getSubject();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDto userDto = profileService.getUserByUserId(userId);
        UserResponse userResponse = modelMapper.map(userDto, UserResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
    @PutMapping
    public ResponseEntity<UserResponse> updateProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody UserUpdateRequest userDetails) {
        if(token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId;
        try{
            userId = Jwts.parser().setSigningKey("secret").parseClaimsJws(token.replace("Bearer ", "")).getBody().getSubject();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        userDto.setUserId(userId);
        profileService.updateUser(userDto);
        UserUpdated user = new UserUpdated(
                userDto.getName(), userDto.getUserId(), userDto.getPhone()
        );
        kafkaUserUpdated.send(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping
    public ResponseEntity<String> deleteProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if(token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId;
        try{
            userId = Jwts.parser().setSigningKey("secret").parseClaimsJws(token.replace("Bearer ", "")).getBody().getSubject();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        profileService.deleteUser(userId);
        UserDeleted user = new UserDeleted(userId);
        kafkaUserDeleted.send(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
